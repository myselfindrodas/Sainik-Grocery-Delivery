package com.grocery.sainikgrocerydelivery.data.repository


import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentRequestModel
import com.grocery.sainikgrocerydelivery.data.model.login.LoginEmailRequestModel
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcRequestModel
import com.shyamfuture.tantujayarnbank.data.model.forget_password.ForgetPassRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun loginemail(requestBody: LoginEmailRequestModel) = apiHelper.loginemail(requestBody)
    suspend fun urclist(requestBody: UrcRequestModel) = apiHelper.urclist(requestBody)
    suspend fun logout() = apiHelper.logout()
    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiHelper.forgotpassword(requestBody)
    suspend fun resetpassword(requestBody: ResetPasswordRequestModel) = apiHelper.resetpassword(requestBody)
    suspend fun consignmentlist(requestBody: ConsignmentRequestModel, page:String) = apiHelper.consignmentlist(requestBody,page)
    suspend fun consignmentdetails(id:String) = apiHelper.consignmentdetails(id)
    suspend fun orderprocessotp(id:String) = apiHelper.orderprocessotp(id)
    suspend fun pickupimageupdate(id: RequestBody, part: MultipartBody.Part) = apiHelper.pickupimageupdate(id,part)


}