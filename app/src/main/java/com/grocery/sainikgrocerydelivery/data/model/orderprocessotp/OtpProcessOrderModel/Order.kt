package com.grocery.sainikgrocerydelivery.data.model.orderprocessotp.OtpProcessOrderModel


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-12T16:21:42.000000Z
    @SerializedName("delivery_image")
    val deliveryImage: Any?, // null
    @SerializedName("delivery_otp")
    val deliveryOtp: Int, // 5984
    @SerializedName("expt_delivery")
    val exptDelivery: String, // 2023-04-12 21:51:42
    @SerializedName("id")
    val id: Int, // 28
    @SerializedName("order_reference_id")
    val orderReferenceId: String, // SAINIK757460
    @SerializedName("pickup_image")
    val pickupImage: String // 1681476862.png
)