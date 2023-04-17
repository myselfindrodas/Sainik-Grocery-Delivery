package com.shyamfuture.tantujayarnbank.data.model.my_order.orderResponse


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyOrderListData(
    @SerializedName("category_name")
    val categoryName: String?="",
    @SerializedName("cgst")
    val cgst: String?="",
    @SerializedName("code")
    val code: String?="",
    @SerializedName("created_at")
    val createdAt: String?="",
    @SerializedName("gst")
    val gst: String?="",
    @SerializedName("hubs_name")
    val hubsName: String?="",
    @SerializedName("id")
    val id: String?="",
    @SerializedName("mills_name")
    val millsName: String?="",
    @SerializedName("name")
    val name: String?="",
    @SerializedName("qty")
    val qty: String?="",
    @SerializedName("order_details_id")
    val orderDetailsId: String?="",
    @SerializedName("order_no")
    val orderNo: String?="",
    @SerializedName("payment_mode")
    val paymentMode: String?="",
    @SerializedName("payment_status")
    val paymentStatus: String?="",
    @SerializedName("pickup_date")
    val pickupDate: String?="",
    @SerializedName("sgst")
    val sgst: String?="",
    @SerializedName("society_name")
    val societyName: String?="",
    @SerializedName("status")
    val status: String?="",
    @SerializedName("sub_total")
    val subTotal: String?="",
    @SerializedName("total")
    val total: String?="",
    @SerializedName("transaction_id")
    var transactionId: String?="",
    @SerializedName("updated_at")
    val updatedAt: String?="",
    @SerializedName("user_id")
    val userId: String?="",
    @SerializedName("yarns_name")
    val yarnsName: String?=""
):Serializable{

}