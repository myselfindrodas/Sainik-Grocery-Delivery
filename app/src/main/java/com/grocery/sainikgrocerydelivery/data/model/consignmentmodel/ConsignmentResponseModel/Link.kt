package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("active")
    val active: Boolean, // false
    @SerializedName("label")
    val label: String, // &laquo; Previous
    @SerializedName("url")
    val url: String? // https://developer.shyamfuture.in/sainik-grocery/admin/api/delivery/urc-order-list?page=1
)