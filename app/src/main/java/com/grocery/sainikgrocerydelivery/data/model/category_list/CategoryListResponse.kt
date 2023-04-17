package com.shyamfuture.tantujayarnbank.data.model.category_list


import com.google.gson.annotations.SerializedName

data class CategoryListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)