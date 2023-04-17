package com.grocery.sainikgrocerydelivery.data.model.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String, // 29|XCnB0f3E37Qj7v90YGrWtLaerTRGKxPLwulUHbcu
    @SerializedName("user")
    val user: User
)