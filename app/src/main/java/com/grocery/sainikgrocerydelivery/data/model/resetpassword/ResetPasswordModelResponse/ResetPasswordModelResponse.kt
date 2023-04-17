package com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordModelResponse


import com.google.gson.annotations.SerializedName

data class ResetPasswordModelResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // You are successfully reset your password.
    @SerializedName("status")
    val status: Boolean // true
)