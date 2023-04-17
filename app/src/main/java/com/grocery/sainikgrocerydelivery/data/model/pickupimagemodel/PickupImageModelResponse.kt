package com.grocery.sainikgrocerydelivery.data.model.pickupimagemodel


import com.google.gson.annotations.SerializedName

data class PickupImageModelResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Data updated successfully
    @SerializedName("orderImageUrl")
    val orderImageUrl: String, // https://developer.shyamfuture.in/sainik-grocery/admin/uploads/orderImages
    @SerializedName("status")
    val status: Boolean // true
)