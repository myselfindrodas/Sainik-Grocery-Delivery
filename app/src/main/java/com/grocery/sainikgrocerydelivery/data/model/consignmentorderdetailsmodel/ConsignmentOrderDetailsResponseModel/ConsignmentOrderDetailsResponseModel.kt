package com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class ConsignmentOrderDetailsResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Data get successfully
    @SerializedName("orderImageUrl")
    val orderImageUrl: String, // https://developer.shyamfuture.in/sainik-grocery/admin/uploads/orderImages
    @SerializedName("status")
    val status: Boolean // true
)