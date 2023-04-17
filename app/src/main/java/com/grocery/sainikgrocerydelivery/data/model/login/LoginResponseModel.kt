package com.grocery.sainikgrocerydelivery.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // Login successfully!
    @SerializedName("status")
    val status: Boolean // true
)