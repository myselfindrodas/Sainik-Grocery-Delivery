package com.grocery.sainikgrocerydelivery.data.model.logout


import com.google.gson.annotations.SerializedName
import com.grocery.sainikgrocerydelivery.data.model.logout.Data

data class LogoutResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // User logout successfully
    @SerializedName("status")
    val status: Boolean // true
)