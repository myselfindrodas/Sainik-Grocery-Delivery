package com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("apartment_name")
    val apartmentName: String, // b bfhjc
    @SerializedName("area")
    val area: String, // dhhdndj
    @SerializedName("city")
    val city: String, // ujcjdj
    @SerializedName("created_at")
    val createdAt: String, // 2023-04-06T19:59:30.000000Z
    @SerializedName("deleted_at")
    val deletedAt: Any?, // null
    @SerializedName("house_no")
    val houseNo: String, // jfjnbf
    @SerializedName("id")
    val id: Int, // 7
    @SerializedName("is_primary")
    val isPrimary: Int, // 1
    @SerializedName("pincode")
    val pincode: String, // 774883838
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("street_details")
    val streetDetails: String, // vhdhhdhd
    @SerializedName("type")
    val type: String, // Office
    @SerializedName("updated_at")
    val updatedAt: String, // 2023-04-09T20:43:17.000000Z
    @SerializedName("user_id")
    val userId: Int // 2
)