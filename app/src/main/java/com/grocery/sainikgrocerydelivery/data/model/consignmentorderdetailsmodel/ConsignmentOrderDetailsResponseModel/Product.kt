package com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("image")
    val image: String, // sugar1.png
    @SerializedName("name")
    val name: String // shop
)