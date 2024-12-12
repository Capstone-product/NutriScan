package com.dicoding.nutriscan.ui.camera

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.capstone.nutriscan.databinding.FragmentCameraBinding
import com.dicoding.nutriscan.data.remote.response.FruitScanResponse
import com.dicoding.nutriscan.data.remote.retrofit.ApiConfig
import com.dicoding.nutriscan.utils.getImageUri
import com.dicoding.nutriscan.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }


        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonCamera.setOnClickListener { startCamera() }
        binding.buttonScan.setOnClickListener { uploadImage()}

    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCamera() {
        val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                currentImageUri?.let { uri ->
                    showImage()
                }
            }
        }
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imagePreview.setImageURI(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun uploadImage() {
        currentImageUri?. let {uri ->
            val imageFile = uriToFile(uri, requireContext())
            Log.d("Image Classification File", "showImage: ${imageFile.path}")

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val response = apiService.uploadImage(multipartBody)
                    with(binding) {
                        resultFruitText.text = response.predictedClass
                        kaloriResult.text = response.nutritionalInfo[0].kalori.toString()
                        karbohidratResult.text = response.nutritionalInfo[0].karbohidrat.toString()
                        lemakResult.text = response.nutritionalInfo[0].lemak.toString()
                        proteinResult.text = response.nutritionalInfo[0].protein.toString()
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, FruitScanResponse::class.java)
                    Toast.makeText(requireContext(), errorBody, Toast.LENGTH_SHORT).show()
                }
            }

        } ?: Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ScanFragment"
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }
}