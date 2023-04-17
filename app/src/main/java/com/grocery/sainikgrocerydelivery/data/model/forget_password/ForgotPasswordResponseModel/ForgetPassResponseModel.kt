package com.grocery.sainikgrocerydelivery.data.model.forget_password.ForgotPasswordResponseModel


import com.google.gson.annotations.SerializedName

data class ForgetPassResponseModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // OTP send successfully !!
    @SerializedName("status")
    val status: Boolean // true
)