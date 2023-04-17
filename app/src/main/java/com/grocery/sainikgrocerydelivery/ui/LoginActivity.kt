package com.grocery.sainikgrocerydelivery.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.base.BaseActivity
import com.grocery.sainikgrocerydelivery.data.ApiClient
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.model.login.LoginEmailRequestModel
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.ActivityLoginBinding
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    var isPasswordVisible = false
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLoginBinding
    }

    override fun setFunction() {

        with(binding) {

            Shared_Preferences.setUserToken("")
            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm

            fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this@LoginActivity)

            btnLogin.setOnClickListener {


                if (etUserName.text.toString().length == 0) {
                    Toast.makeText(applicationContext, "Enter User Name", Toast.LENGTH_LONG).show()
                } else if (etPassword.text.toString().isEmpty()) {
                    Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_LONG).show()

                } else {

                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (binding.etUserName.text.toString().trim().matches(Regex(emailPattern))) {
                        loginemailapi(
                            binding.etUserName.text.toString(),
                            binding.etPassword.text.toString())
                    } else {

                        loginphoneapi(
                            binding.etUserName.text.toString(),
                            binding.etPassword.text.toString())
                    }

//                    if (ContextCompat.checkSelfPermission(
//                            this@LoginActivity,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                        != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        requestPermissionLauncher.launch(
//                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//                        )
//                    } else {
//                        displayLocationSettingsRequest(this@LoginActivity)
//                    }

                }

            }



            tvForgetPass.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
            }


            pwdHideBtn.setOnClickListener {
                if (!isPasswordVisible) {
                    etPassword.transformationMethod = null
                    pwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                    isPasswordVisible = true

                } else {
                    etPassword.transformationMethod = PasswordTransformationMethod()
                    pwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                    isPasswordVisible = false
                }
                etPassword.setSelection(etPassword.length())
            }
        }
    }


    private fun displayLocationSettingsRequest(context: Context) {
        showProgressDialog()
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 10000
        locationRequest!!.fastestInterval = 10000 / 2
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val resultPending: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        resultPending.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
            @SuppressLint("MissingPermission")
            override fun onResult(result: LocationSettingsResult) {
                val status: com.google.android.gms.common.api.Status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                        Log.i(
                            "TAG",
                            "All location settings are satisfied."
                        )

                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            })
                            .addOnSuccessListener { location: Location? ->
                                if (location == null)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude

                                    hideProgressDialog()

//                                    startActivity(Intent(this@LoginActivity, URCListActivity::class.java))
//                                    intent.putExtra("lat", lat)
//                                    intent.putExtra("lon", lon)
//                                    startActivity(intent)

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Lat: $lat  Long: $lon",
                                        Toast.LENGTH_SHORT
                                    ).show()



//                                    startActivity(Intent(this@LoginActivity,URCListActivity::class.java))


//                                    reverseGeocoding(
//                                        location.latitude.toString(),
//                                        location.longitude.toString()
//                                    )

                                    //fusedLocationClient.removeLocationUpdates(location)
                                    Log.i(
                                        "TAG",
                                        "Lat: $lat  Long: $lon"
                                    )
                                }

                            }
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(
                            "TAG",
                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            val intentSenderRequest = IntentSenderRequest
                                .Builder(status.resolution!!).build()
                            resolutionForResult.launch(intentSenderRequest)
                            /*status.startResolutionForResult(
                                this@LocationActivity,
                                11
                            )*/
                        } catch (e: IntentSender.SendIntentException) {
                            Log.i("TAG", "PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        "TAG",
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        })
    }


    private val resolutionForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK)
            displayLocationSettingsRequest(this@LoginActivity)
    }


    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            displayLocationSettingsRequest(this)

        } else {
            // PERMISSION NOT GRANTED
        }
    }


    private fun loginemailapi(username: String, password: String) {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.loginemail(LoginEmailRequestModel(email = username, password = password, phone = ""))
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                if (resource.data?.status == true) {
                                    val builder = AlertDialog.Builder(this@LoginActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        Shared_Preferences.setUserToken(resource.data.data.token)
                                        Shared_Preferences.setLoginStatus(true)
                                        val intent = Intent(this@LoginActivity, URCListActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    val alert = builder.create()
                                    alert.setOnShowListener { arg0 ->
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setTextColor(resources.getColor(R.color.orange))
                                    }
                                    alert.show()
                                } else {

                                    val builder = AlertDialog.Builder(this@LoginActivity)
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
                                if (it.message!!.contains("401", true)) {
                                    val builder = AlertDialog.Builder(this@LoginActivity)
                                    builder.setMessage("Invalid Employee Id / Password")
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.cancel()

                                    }
                                    val alert = builder.create()
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                    alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                    alert.show()
                                } else
                                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    private fun loginphoneapi(username: String, password: String) {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.loginemail(LoginEmailRequestModel(phone = username, password = password, email = ""))
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                if (resource.data?.status == true) {
                                    val builder = AlertDialog.Builder(this@LoginActivity)
                                    builder.setMessage(resource.data.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        Shared_Preferences.setUserToken(resource.data.data.token)
                                        Shared_Preferences.setLoginStatus(true)
                                        val intent = Intent(this@LoginActivity, URCListActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    val alert = builder.create()
                                    alert.setOnShowListener { arg0 ->
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                            .setTextColor(resources.getColor(R.color.orange))
                                    }
                                    alert.show()
                                } else {

                                    val builder = AlertDialog.Builder(this@LoginActivity)
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
                                if (it.message!!.contains("401", true)) {
                                    val builder = AlertDialog.Builder(this@LoginActivity)
                                    builder.setMessage("Invalid Employee Id / Password")
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.cancel()

                                    }
                                    val alert = builder.create()
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                    alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                        .setTextColor(resources.getColor(R.color.orange))
                                    alert.show()
                                } else
                                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }


}