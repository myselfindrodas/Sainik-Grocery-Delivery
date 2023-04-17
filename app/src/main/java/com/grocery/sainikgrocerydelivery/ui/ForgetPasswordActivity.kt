package com.grocery.sainikgrocerydelivery.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.base.BaseActivity
import com.grocery.sainikgrocerydelivery.data.ApiClient
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.model.resetpassword.ResetPasswordRequestModel
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.ActivityForgetPasswordBinding
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel
import com.shyamfuture.tantujayarnbank.data.model.forget_password.ForgetPassRequestModel

class ForgetPasswordActivity : BaseActivity() {


    lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel: CommonViewModel

    override fun resourceLayout(): Int {
        return R.layout.activity_forget_password
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityForgetPasswordBinding
    }

    override fun setFunction() {
        with(binding) {

            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm

            svForgetLayout.visibility = View.VISIBLE
            svGotoLogin.visibility = View.GONE
            svResetLayout.visibility = View.GONE
            btnSubmit.setOnClickListener {

                if (etPhone.text.toString().length < 10) {
                    Toast.makeText(
                        this@ForgetPasswordActivity,
                        "Enter valid phone number",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    forgotpassword(etPhone.text.toString())
                }

            }

            btnReset.setOnClickListener {
                if (etPass.text.toString().length==0){
                    Toast.makeText(this@ForgetPasswordActivity, "Enter Password", Toast.LENGTH_SHORT).show()
                }else if (etConfPassword.text.toString().length==0){
                    Toast.makeText(this@ForgetPasswordActivity, "Enter Confirm Password", Toast.LENGTH_SHORT).show()
                }else if (!etPass.text.toString().equals(etConfPassword.text.toString())){
                    Toast.makeText(this@ForgetPasswordActivity, "Password Not matched", Toast.LENGTH_SHORT).show()
                }else {

                    resetpassword(etPhone.text.toString(),etConfPassword.text.toString())
                }

            }
            btnGo.setOnClickListener {
                startActivity(Intent(this@ForgetPasswordActivity, LoginActivity::class.java))
                finish()
            }
        }

    }


    private fun forgotpassword(phone: String) {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.forgotpassword(ForgetPassRequestModel(phone = phone)).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {
                                Utilities.alertOTPDialogUtil(
                                    this@ForgetPasswordActivity,
                                    binding.etPhone.text.toString(),
                                    false,
                                    object : Utilities.OnItemClickListener {
                                        override fun onItemClickAction(
                                            type: Int,
                                            dialogInterface: DialogInterface,
                                            otp: String
                                        ) {

                                            if (resource.data.data.details.otp.equals(otp)) {

                                                when (type) {
                                                    1 -> {

                                                        if (binding.svForgetLayout.isVisible) {
                                                            binding.svForgetLayout.visibility =
                                                                View.GONE
                                                            binding.svGotoLogin.visibility =
                                                                View.GONE
                                                            binding.svResetLayout.visibility =
                                                                View.VISIBLE
                                                        }
                                                    }
                                                }
                                            } else {

                                                Toast.makeText(this@ForgetPasswordActivity,
                                                    "OTP not matched!", Toast.LENGTH_SHORT).show()

                                            }


                                        }

                                    })
                            } else {

                                val builder = AlertDialog.Builder(this@ForgetPasswordActivity)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                }
                                alert.show()

                            }


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }


    private fun resetpassword(phone: String, password:String){
        if (Utilities.isNetworkAvailable(this)) {
            viewModel.resetpassword(ResetPasswordRequestModel(phone = phone, password= password)).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {


                                if (binding.svResetLayout.isVisible) {
                                    binding.svForgetLayout.visibility = View.GONE
                                    binding.svResetLayout.visibility = View.GONE
                                    binding.svGotoLogin.visibility = View.VISIBLE
                                }

                            } else {

                                val builder = AlertDialog.Builder(this@ForgetPasswordActivity)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                }
                                alert.show()

                            }


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.svGotoLogin.isVisible) {
            binding.svForgetLayout.visibility = View.GONE
            binding.svResetLayout.visibility = View.VISIBLE
            binding.svGotoLogin.visibility = View.GONE

        } else if (binding.svResetLayout.isVisible) {
            binding.svForgetLayout.visibility = View.VISIBLE
            binding.svGotoLogin.visibility = View.GONE
            binding.svResetLayout.visibility = View.GONE

        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}