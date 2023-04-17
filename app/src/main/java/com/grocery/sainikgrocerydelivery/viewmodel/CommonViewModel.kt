package com.grocery.sainikgrocerydelivery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.grocery.sainikgrocerydelivery.data.Resource
import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentRequestModel
import com.grocery.sainikgrocerydelivery.data.model.login.LoginEmailRequestModel
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcRequestModel
import com.grocery.sainikgrocerydelivery.data.repository.MainRepository
import com.shyamfuture.tantujayarnbank.data.model.forget_password.ForgetPassRequestModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CommonViewModel(private val mainRepository: MainRepository) : ViewModel() {


    fun loginemail(requestBody: LoginEmailRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.loginemail(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun urclist(requestBody: UrcRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.urclist(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun logout() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.logout()
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun forgotpassword(requestBody: ForgetPassRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.forgotpassword(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun resetpassword(requestBody: ResetPasswordRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.resetpassword(requestBody)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun consignmentlist(requestBody: ConsignmentRequestModel,page:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.consignmentlist(requestBody, page)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun consignmentdetails(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.consignmentdetails(id)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }



    fun orderprocessotp(id:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.orderprocessotp(id)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }




    fun pickupimageupdate(id: RequestBody, part: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = mainRepository.pickupimageupdate(id, part)
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}