package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class ConsignmentResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Data get successfully
    @SerializedName("status")
    val status: Boolean // true
)