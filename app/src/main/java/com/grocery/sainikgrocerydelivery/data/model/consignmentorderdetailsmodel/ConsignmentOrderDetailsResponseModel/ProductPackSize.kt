package com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class ProductPackSize(
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("selling_price")
    val sellingPrice: Int, // 44
    @SerializedName("size")
    val size: String // 1kg (Approx. 11-12 Pcs)
)