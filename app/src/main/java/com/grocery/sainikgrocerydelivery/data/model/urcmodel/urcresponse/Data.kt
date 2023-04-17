package com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("urc")
    val urc: List<Urc>
)