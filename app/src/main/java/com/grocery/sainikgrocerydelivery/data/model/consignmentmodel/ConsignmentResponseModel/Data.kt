package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("order")
    val order: Order,
    @SerializedName("urc")
    val urc: Urc
)