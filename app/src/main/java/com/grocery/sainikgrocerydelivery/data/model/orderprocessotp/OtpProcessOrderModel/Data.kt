package com.grocery.sainikgrocerydelivery.data.model.orderprocessotp.OtpProcessOrderModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("deliveryBoyPhone")
    val deliveryBoyPhone: String, // 7031552025
    @SerializedName("order")
    val order: Order
)