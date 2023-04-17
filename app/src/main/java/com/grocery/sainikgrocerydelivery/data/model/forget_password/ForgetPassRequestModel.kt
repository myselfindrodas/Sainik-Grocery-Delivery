package com.shyamfuture.tantujayarnbank.data.model.forget_password


import com.google.gson.annotations.SerializedName

data class ForgetPassRequestModel(
    @SerializedName("phone")
    val phone: String
)