package com.grocery.sainikgrocerydelivery.data.model.pickupimagemodel


import com.google.gson.annotations.SerializedName

data class Urc(
    @SerializedName("address")
    val address: Address,
    @SerializedName("address_id")
    val addressId: Int, // 3
    @SerializedName("amount")
    val amount: Int, // 44
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-13T11:57:08.000000Z
    @SerializedName("delivery_image")
    val deliveryImage: Any?, // null
    @SerializedName("delivery_otp")
    val deliveryOtp: Int, // 123456
    @SerializedName("expt_delivery")
    val exptDelivery: String, // 2023-04-13 17:27:08
    @SerializedName("id")
    val id: Int, // 29
    @SerializedName("order_history")
    val orderHistory: List<OrderHistory>,
    @SerializedName("order_reference_id")
    val orderReferenceId: String, // SAINIK728745
    @SerializedName("pickup_image")
    val pickupImage: String // 1681394727.jpg
)