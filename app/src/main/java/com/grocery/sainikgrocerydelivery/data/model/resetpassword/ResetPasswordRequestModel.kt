package com.grocery.sainikgrocerydelivery.data.model.resetpassword

import com.google.gson.annotations.SerializedName

class ResetPasswordRequestModel(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)