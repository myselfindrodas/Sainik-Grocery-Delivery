package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class Urc(
    @SerializedName("address")
    val address: Any?, // null
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("name")
    val name: String // urc
)