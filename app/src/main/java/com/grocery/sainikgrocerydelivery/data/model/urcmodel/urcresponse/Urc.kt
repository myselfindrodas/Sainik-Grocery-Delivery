package com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse


import com.google.gson.annotations.SerializedName

data class Urc(
    @SerializedName("distance")
    val distance: Double, // 9.493529796600341796875e-5
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,

    var isSelected: Boolean = false
)