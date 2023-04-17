package com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordModelResponse


import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("address")
    val address: Any?, // null
    @SerializedName("birthday")
    val birthday: Any?, // null
    @SerializedName("created_at")
    val createdAt: Any?, // null
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("device_type")
    val deviceType: Any?, // null
    @SerializedName("email")
    val email: String, // delivery@gmail.com
    @SerializedName("email_validate")
    val emailValidate: Int, // 0
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?, // null
    @SerializedName("gender")
    val gender: Int, // 1
    @SerializedName("id")
    val id: Int, // 4
    @SerializedName("image")
    val image: String,
    @SerializedName("last_name")
    val lastName: Any?, // null
    @SerializedName("latitude")
    val latitude: String, // 28.418717
    @SerializedName("longitude")
    val longitude: String, // 77.0478999
    @SerializedName("name")
    val name: String, // delivery
    @SerializedName("otp")
    val otp: Int, // 123456
    @SerializedName("phone")
    val phone: String, // 7031552025
    @SerializedName("phone_validate")
    val phoneValidate: Int, // 0
    @SerializedName("quota")
    val quota: Any?, // null
    @SerializedName("rank")
    val rank: Any?, // null
    @SerializedName("remember_token")
    val rememberToken: Any?, // null
    @SerializedName("role_id")
    val roleId: Int, // 3
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-12T13:19:47.000000Z
    @SerializedName("zip_codes")
    val zipCodes: Any? // null
)