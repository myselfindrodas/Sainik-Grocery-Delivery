package com.grocery.sainikgrocerydelivery.data

import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentRequestModel
import com.grocery.sainikgrocerydelivery.data.model.login.LoginEmailRequestModel
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcRequestModel
import com.shyamfuture.tantujayarnbank.data.model.forget_password.ForgetPassRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun loginemail(requestBody: LoginEmailRequestModel) = apiInterface.loginemail(requestBody)
    suspend fun urclist(requestBody: UrcRequestModel) = apiInterface.urclist(requestBody)
    suspend fun logout() = apiInterface.logout()
    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiInterface.forgotpassword(requestBody)
    suspend fun resetpassword(requestBody: ResetPasswordRequestModel) = apiInterface.resetpassword(requestBody)
    suspend fun consignmentlist(requestBody: ConsignmentRequestModel, page:String) = apiInterface.consignmentlist(requestBody,page)
    suspend fun consignmentdetails(id:String) = apiInterface.consignmentdetails(id)
    suspend fun orderprocessotp(id:String) = apiInterface.orderprocessotp(id)
    suspend fun pickupimageupdate(id: RequestBody, part: MultipartBody.Part) = apiInterface.pickupimageupdate(id,part)



}