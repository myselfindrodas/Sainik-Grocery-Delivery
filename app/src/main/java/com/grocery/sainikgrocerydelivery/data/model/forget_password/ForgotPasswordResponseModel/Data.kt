package com.grocery.sainikgrocerydelivery.data.model.forget_password.ForgotPasswordResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("details")
    val details: Details
)