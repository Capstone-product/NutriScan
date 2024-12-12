package com.dicoding.nutriscan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutriscan.R
import com.capstone.nutriscan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val list = ArrayList<Fruit>()

    private lateinit var fruitAdapter: FruitAdapter
    private lateinit var rvFruit: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFruit = binding.rvEvents
        rvFruit.setHasFixedSize(true)

        list.addAll(getListFruit())
        showRecyclerList()

        binding.cameraButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_camera)
        }

        binding.bookButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_dictionary)
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = list.filter { fruit ->
                    fruit.name.contains(newText.orEmpty(), ignoreCase = true)
                }
                fruitAdapter.setData(filteredList as ArrayList<Fruit>)
                return true
            }
        })

    }

    private fun getListFruit(): ArrayList<Fruit> {
        val fruitNames = resources.getStringArray(R.array.fruit_article_name)
        val fruitDescriptions = resources.getStringArray(R.array.fruit_article)
        val fruitImages = resources.obtainTypedArray(R.array.article_image)
        val listFruit = ArrayList<Fruit>()
        for (i in fruitNames.indices) {
            val fruit = Fruit(fruitNames[i], fruitDescriptions[i], fruitImages.getResourceId(i, -1))
            listFruit.add(fruit)
        }
        return listFruit
    }

    private fun showRecyclerList() {
        rvFruit.layoutManager = LinearLayoutManager(requireContext())
        fruitAdapter = FruitAdapter(list)
        rvFruit.adapter = fruitAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



