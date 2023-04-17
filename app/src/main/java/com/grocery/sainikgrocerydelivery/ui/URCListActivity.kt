package com.grocery.sainikgrocerydelivery.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.UrcRequestModel
import com.grocery.sainikgrocerydelivery.data.model.urcmodel.urcresponse.Urc
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.ActivityUrclistBinding
import com.grocery.sainikgrocerydelivery.ui.adapter.UrcListAdapter
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel

class URCListActivity : BaseActivity(), UrcListAdapter.OnItemClickListener {

    lateinit var binding: ActivityUrclistBinding
    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    private lateinit var viewModel: CommonViewModel
    lateinit var urcListAdapter: UrcListAdapter
    val urcModelArrayList: ArrayList<Urc> = arrayListOf()

    override fun resourceLayout(): Int {
        return R.layout.activity_urclist

    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityUrclistBinding
    }

    override fun setFunction() {


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@URCListActivity)
        if (ContextCompat.checkSelfPermission(
                this@URCListActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            )
        } else {
            displayLocationSettingsRequest(this@URCListActivity)
        }
        binding.btnContinue.setOnClickListener {

            var isSelected = false
            urcListAdapter.getURCList().forEach { item ->
                if (item.isSelected) {
                    isSelected = true
                    return@forEach
                }

            }
            val intent = Intent(this@URCListActivity, MainActivity::class.java)
            if (isSelected)
                startActivity(intent)
            else
                Toast.makeText(
                    this@URCListActivity,
                    "Please select an URC from the list above.",
                    Toast.LENGTH_SHORT
                ).show()
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
                                        this@URCListActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude

                                    hideProgressDialog()
                                    setupRecyclewrView()
                                    UrcList(lat.toString(), lon.toString())

//                                    startActivity(Intent(this@LoginActivity, URCListActivity::class.java))
//                                    intent.putExtra("lat", lat)
//                                    intent.putExtra("lon", lon)
//                                    startActivity(intent)

                                    Toast.makeText(
                                        this@URCListActivity,
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


    private fun setupRecyclewrView() {
        urcListAdapter = UrcListAdapter(this, urcModelArrayList, this@URCListActivity)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.rvUrc.layoutManager = mLayoutManager
        binding.rvUrc.itemAnimator = DefaultItemAnimator()
        binding.rvUrc.adapter = urcListAdapter
    }


    private fun UrcList(lat:String, lon:String) {
        if (Utilities.isNetworkAvailable(this)) {

            viewModel.urclist(
                UrcRequestModel(
                    latitude = lat,
                    longitude = lon
                )
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            resource.data?.data?.let { itProfileInfo ->


                                urcListAdapter.updateData(itProfileInfo.urc)
                                /* with(fragmentManageDriverBinding) {

                                     Glide.with(mainActivity)
                                         .load(itProfileInfo.profile.profileImage)
                                         .timeout(6000)
                                         .error(com.app.conectar.R.drawable.logo)
                                         .placeholder(com.app.conectar.R.drawable.logo)
                                         .into(PrfImg)


                                 }*/


                            }

                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

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


    private val resolutionForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK)
            displayLocationSettingsRequest(this@URCListActivity)
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


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

    override fun onClick(position: Int, view: View, mUrcModelArrayList: ArrayList<Urc>) {
        mUrcModelArrayList.forEach { item ->
            item.isSelected = false
        }
        mUrcModelArrayList[position].isSelected = true
        urcListAdapter.updateData(mUrcModelArrayList)
    }

}