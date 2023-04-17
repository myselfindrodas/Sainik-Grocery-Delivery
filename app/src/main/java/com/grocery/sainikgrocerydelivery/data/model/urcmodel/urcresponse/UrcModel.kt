package com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse


import com.google.gson.annotations.SerializedName
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse.Data

data class UrcModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Data get successfully
    @SerializedName("status")
    val status: Boolean // true
)