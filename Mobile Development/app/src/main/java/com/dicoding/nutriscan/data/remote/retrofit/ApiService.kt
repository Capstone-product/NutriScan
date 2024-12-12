package com.dicoding.nutriscan.data.remote.retrofit

import com.dicoding.nutriscan.data.remote.response.FruitScanResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("pred/buah")
    suspend fun uploadImage(
        @Part imageMultipart: MultipartBody.Part
    ): FruitScanResponse
}