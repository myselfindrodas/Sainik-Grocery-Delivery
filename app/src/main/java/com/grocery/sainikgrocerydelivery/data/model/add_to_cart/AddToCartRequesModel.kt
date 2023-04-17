package com.shyamfuture.tantujayarnbank.data.model.add_to_cart


import com.google.gson.annotations.SerializedName

data class AddToCartRequesModel(
    @SerializedName("bundle")
    val bundle: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("hub_id")
    val hubId: String,
    @SerializedName("mill_id")
    val millId: String,
    @SerializedName("selling_cost")
    val sellingCost: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("yarn_id")
    val yarnId: String,
    @SerializedName("max_quantity")
    val max_quantity: String
)