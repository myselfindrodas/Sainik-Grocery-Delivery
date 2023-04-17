package com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel


import com.google.gson.annotations.SerializedName

data class OrderHistory(
    @SerializedName("id")
    val id: Int, // 8
    @SerializedName("order_id")
    val orderId: Int, // 29
    @SerializedName("order_reference_id")
    val orderReferenceId: String, // SAINIK728745
    @SerializedName("product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int, // 2
    @SerializedName("product_pack_size")
    val productPackSize: ProductPackSize,
    @SerializedName("product_pack_size_id")
    val productPackSizeId: Int // 2
)