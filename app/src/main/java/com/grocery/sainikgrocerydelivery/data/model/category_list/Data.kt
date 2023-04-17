package com.shyamfuture.tantujayarnbank.data.model.category_list


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("bundle_weight")
    val bundle_weight: String,
    @SerializedName("max_order_quantity")
    val max_order_quantity: Int
)