package com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("order")
    val order: Order
)