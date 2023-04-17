package com.grocery.sainikgrocerydelivery.data.model.orderprocessotp.OtpProcessOrderModel


import com.google.gson.annotations.SerializedName

data class OtpProcessOrderModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // OTP has been send successfully !!
    @SerializedName("orderImageUrl")
    val orderImageUrl: String, // https://developer.shyamfuture.in/sainik-grocery/admin/uploads/orderImages
    @SerializedName("status")
    val status: Boolean // true
)