package com.dicoding.nutriscan.data.remote.response

import com.google.gson.annotations.SerializedName

data class FruitScanResponse(

	@field:SerializedName("nutritional_info")
	val nutritionalInfo: List<NutritionalInfoItem>,

	@field:SerializedName("predicted_class")
	val predictedClass: String
)

data class NutritionalInfoItem(

	@field:SerializedName("Lemak(g)")
	val lemak: Float,

	@field:SerializedName("Kalori")
	val kalori: Float,

	@field:SerializedName("Ukuran")
	val ukuran: String,

	@field:SerializedName("Karbohidrat(g)")
	val karbohidrat: Float,

	@field:SerializedName("Protein(g)")
	val protein: Float
)
