package com.grocery.sainikgrocerydelivery.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginEmailRequestModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)