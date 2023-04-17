package com.grocery.sainikgrocerydelivery.data


import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentRequestModel
import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentResponseModel.ConsignmentResponseModel
import com.grocery.sainikgrocerydelivery.data.model.consignmentorderdetailsmodel.ConsignmentOrderDetailsResponseModel.ConsignmentOrderDetailsResponseModel
import com.grocery.sainikgrocerydelivery.data.model.forget_password.ForgotPasswordResponseModel.ForgetPassResponseModel
import com.grocery.sainikgrocerydelivery.data.model.login.LoginEmailRequestModel
import com.grocery.sainikgrocerydelivery.data.model.login.LoginResponseModel
import com.grocery.sainikgrocerydelivery.data.model.logout.LogoutResponseModel
import com.grocery.sainikgrocerydelivery.data.model.orderprocessotp.OtpProcessOrderModel.OtpProcessOrderModel
import com.grocery.sainikgrocerydelivery.data.model.pickupimagemodel.PickupImageModelResponse
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordModelResponse.ResetPasswordModelResponse
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse.UrcModel
import com.shyamfuture.tantujayarnbank.data.model.forget_password.ForgetPassRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {


    @POST("delivery/login")
    suspend fun loginemail(
        @Body requestBody: LoginEmailRequestModel
    ): LoginResponseModel


    @POST("delivery/urc-list")
    suspend fun urclist(
        @Body requestBody: UrcRequestModel
    ): UrcModel


    @GET("delivery/logout")
    suspend fun logout(
    ): LogoutResponseModel


    @POST("delivery/forgot-password")
    suspend fun forgotpassword(
        @Body requestBody: ForgetPassRequestModel
    ): ForgetPassResponseModel


    @POST("delivery/reset-password")
    suspend fun resetpassword(
        @Body requestBody: ResetPasswordRequestModel
    ): ResetPasswordModelResponse



    @POST("delivery/urc-order-list")
    suspend fun consignmentlist(
        @Body requestBody: ConsignmentRequestModel,
        @Query("page") page: String
    ): ConsignmentResponseModel


    @GET("delivery/order-details/{id}")
    suspend fun consignmentdetails(
        @Path("id") id: String
    ): ConsignmentOrderDetailsResponseModel



    @GET("delivery/order-process-otp/{id}")
    suspend fun orderprocessotp(
        @Path("id") id: String
    ): OtpProcessOrderModel


    @Multipart
    @POST("delivery/order-picked")
    suspend fun pickupimageupdate(
        @Part("id") id: RequestBody,
        @Part file: MultipartBody.Part,
    ): PickupImageModelResponse




//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister1(@Body registerRequest: RegisterRequest): Single<CommonResponseModel>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_USER_REGISTER)
//    fun postRegister(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("language") language: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @Multipart
//    @POST(NetworkConstants.ENDPOINT_PROFILE_UPDATE)
//    fun postProfileUpdate(
//        @Part("name") title: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("society_id") societyId: RequestBody,
//        @Part aadharDocument: MultipartBody.Part,
//        @Part idFile: MultipartBody.Part,
//        @Part proileFile: MultipartBody.Part,
//        @Part("phone_number") phone: RequestBody,
//        @Part("user_id") userId: RequestBody,
//    ): Call<RegisterResponse?>
//
//    @GET(NetworkConstants.ENDPOINT_USER_MOBILE_OTP)
//    fun otp(@Query("phone_no") phone_no: Long): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_FORGET_PASS)
//    fun forgotPassword(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_LOGIN)
//    fun login(@Body loginRequestModel: LoginRequestModel): Single<LoginResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CHANGE_LANGUAGE)
//    fun changeLanguage(@Body changeLanguageRequestModel: ChangeLanguageRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_PROFILE_DETAILS)
//    fun profileDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<ProfileResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_HUB_LIST)
//    fun hubListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<HubListResponse>
//
//    @POST(NetworkConstants.ENDPOINT_DELETE_CART_ITEM)
//    fun deleteCartItem(@Body deleteCartItemRequestModel: DeleteCartItemRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_GET_CART)
//    fun getCartDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<GetCartResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CART_CHECKOUT)
//    fun getCartCheckout(@Body checkoutRequestModel: CheckoutRequestModel): Single<CheckoutResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CATEGORY_LIST)
//    fun categoryListDetails(@Body getProfileRequestModel: GetProfileRequestModel): Single<CategoryListResponse>
//    @POST(NetworkConstants.ENDPOINT_PAYMENT_STATUS)
//    fun postPaymentStatus(@Body paymentStatusRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//    @POST(NetworkConstants.ENDPOINT_ITEM_PARTICULAR_LIST)
//    fun itemParticularListDetails(@Body getItemParticulateRequestModel: GetItemParticulateRequestModel): Single<ItemParticualarResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAST_ORDER)
//    fun pastOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_UPCOMING_ORDER)
//    fun upcomingtOrderListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<MyOrderResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_NOTIFICATION)
//    fun notificationListDetails(@Body profileRequestModel: GetProfileRequestModel): Single<NotificationResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_CHANGEPASSWORD)
//    fun changePassword(@Body changePasswordRequestModel: ChangePasswordRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_SUPPLY_MILL_LIST)
//    fun millListDetails(@Body millRequestModel: SupplyMillRequestModel): Single<SupplyMillResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_ADD_TO_CART)
//    fun addToCart(@Body addToCartRequesModel: AddToCartRequesModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_PAY_NOW)
//    fun postPayNow(@Body payNowRequestModel: PayNowRequestModel): Single<PayNowResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_CREATE_PASSWORD)
//    fun createPassword(@Body loginRequestModel: LoginRequestModel): Single<CommonResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_USER_RESEND_OTP)
//    fun resendOTP(@Body forgetPassRequestModel: ForgetPassRequestModel): Single<ForgetPasswordResponseModel>
//
//    @GET(NetworkConstants.ENDPOINT_SOCIETY_LIST)
//    fun societyList(): Single<SocietyListResponseModel>
//
//    @POST(NetworkConstants.ENDPOINT_LOGOUT)
//    fun logout(@Body getProfileRequestModel: GetProfileRequestModel): Single<CommonResponseModel>

}