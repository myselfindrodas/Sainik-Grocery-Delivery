package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class DataList(
    @SerializedName("address")
    val address: Address,
    @SerializedName("address_id")
    val addressId: Int, // 7
    @SerializedName("amount")
    val amount: Int, // 121
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-12T16:21:42.000000Z
    @SerializedName("expt_delivery")
    val exptDelivery: String, // 2023-04-12 21:51:42
    @SerializedName("id")
    val id: Int, // 28
    @SerializedName("order_reference_id")
    val orderReferenceId: String // SAINIK757460
)