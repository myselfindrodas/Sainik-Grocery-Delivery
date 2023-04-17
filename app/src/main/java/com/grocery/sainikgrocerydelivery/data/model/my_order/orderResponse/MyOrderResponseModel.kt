package com.shyamfuture.tantujayarnbank.data.model.my_order.orderResponse


import com.google.gson.annotations.SerializedName

data class MyOrderResponseModel(
    @SerializedName("data")
    val `data`: List<MyOrderListData>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: Int
)