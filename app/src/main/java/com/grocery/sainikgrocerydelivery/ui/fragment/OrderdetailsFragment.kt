package com.grocery.sainikgrocerydelivery.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.ApiClient
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.FragmentOrderdetailsBinding
import com.grocery.sainikgrocerydelivery.ui.MainActivity
import com.grocery.sainikgrocerydelivery.ui.adapter.ItemAdapter
import com.grocery.sainikgrocerydelivery.utils.GetRealPathFromUri
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class OrderdetailsFragment : Fragment(), ItemAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentOrderdetailsBinding: FragmentOrderdetailsBinding
    private var itemAdapter:ItemAdapter?=null
//    val itemModelArrayList: ArrayList<ItemModel> = arrayListOf()
    private var consignmentid:Int = 0
    private lateinit var viewModel: CommonViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentOrderdetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_orderdetails, container, false)
        val root = fragmentOrderdetailsBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        val intent = arguments
        if (intent != null && intent.containsKey("consignmentid")) {
            consignmentid = intent.getInt("consignmentid", 0)
        }


        itemAdapter = ItemAdapter(mainActivity, this@OrderdetailsFragment)
        fragmentOrderdetailsBinding.rvOrderitems.adapter = itemAdapter
        fragmentOrderdetailsBinding.rvOrderitems.layoutManager = GridLayoutManager(mainActivity, 1)
        consignmentlist(consignmentid.toString())


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentOrderdetailsBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentOrderdetailsBinding.btnOrderprocess.setOnClickListener {

            orderprocessotp(consignmentid.toString())

        }


        fragmentOrderdetailsBinding.ivOrderdetailsimage.setOnClickListener {



                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    )
                } else {


                    ImagePicker.Companion.with(this@OrderdetailsFragment)
                        .cameraOnly()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start()


                    mainActivity.showLoading()
                }
            }


        fragmentOrderdetailsBinding.btnNext.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_deliverydetails)
        }

    }



    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            ImagePicker.Companion.with(this@OrderdetailsFragment)
                .cameraOnly()
                .compress(1024)
                .cropSquare()
                .maxResultSize(1080, 1080)
                .start()

            mainActivity.showLoading()
        } else {
            // PERMISSION NOT GRANTED
        }


    }



    @SuppressLint("SetTextI18n")
    private fun consignmentlist(id:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.consignmentdetails(id = id).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideLoading()
                            resource.data?.let { itProfileInfo ->
                                if (itProfileInfo.status) {
                                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    val output = SimpleDateFormat("dd-MM-yyyy")
                                    var d: Date? = null
                                    try {
                                        d = input.parse(itProfileInfo.data.order.createdAt)
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                    val formatted: String = output.format(d)

                                    fragmentOrderdetailsBinding.tvorderDate.text = "Order date : "+formatted
                                    fragmentOrderdetailsBinding.tvordId.text = "Order ID : "+itProfileInfo.data.order.orderReferenceId
                                    fragmentOrderdetailsBinding.tvAmount.text = "Order Amount : ₹ "+itProfileInfo.data.order.amount
                                    fragmentOrderdetailsBinding.tvAddress.text =
                                        itProfileInfo.data.order.address.houseNo+","+
                                        itProfileInfo.data.order.address.apartmentName+","+
                                                itProfileInfo.data.order.address.streetDetails+","+
                                            itProfileInfo.data.order.address.city+","+
                                            itProfileInfo.data.order.address.type
                                    itemAdapter?.updateData(itProfileInfo.data.order.orderHistory, itProfileInfo.orderImageUrl)
                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
                            }

                        }
                        Status.ERROR -> {
                            mainActivity.hideLoading()
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
                            mainActivity.showLoading()
                        }

                    }

                }
            }

        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }

    }


    private fun orderprocessotp(id: String){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.orderprocessotp(id = id).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideLoading()
                            if (resource.data?.status == true) {
                                Utilities.alertOTPDialogUtil(
                                    mainActivity,
                                    resource.data.data.deliveryBoyPhone,
                                    false,
                                    object : Utilities.OnItemClickListener {
                                        override fun onItemClickAction(
                                            type: Int,
                                            dialogInterface: DialogInterface,
                                            otp: String
                                        ) {

                                            if (resource.data.data.order.deliveryOtp.toString().equals(otp)) {

                                                when (type) {
                                                    1 -> {

                                                        fragmentOrderdetailsBinding.btnOrderprocess.visibility = View.GONE
                                                        fragmentOrderdetailsBinding.tvOTPtext.visibility = View.GONE
                                                        fragmentOrderdetailsBinding.llimageupload.visibility = View.VISIBLE
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(mainActivity, "OTP not matched!", Toast.LENGTH_SHORT).show()
                                            }


                                        }

                                    })

                            } else {

                                val builder = AlertDialog.Builder(mainActivity)
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
                            mainActivity.hideLoading()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

                        }

                        Status.LOADING -> {
                            mainActivity.showLoading()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
            val fileUri = data!!.data

            fragmentOrderdetailsBinding.ivOrderdetailsimage.setImageURI(fileUri)
            fragmentOrderdetailsBinding.ivOrderdetailsimage.rotation = 90F
//            fragmentOrderdetailsBinding.btnNext.visibility = View.VISIBLE
//            mainActivity.hideLoading()
            try {
                picuploadToServer(GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
            mainActivity.hideLoading()
        } else {
            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            mainActivity.hideLoading()
        }
    }


    override fun onClick(position: Int, view: View) {

    }



    private fun picuploadToServer(fileuri:String){


        if (Utilities.isNetworkAvailable(mainActivity)) {

            val file = File(fileuri)
            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("pickup_image", file.name, fileReqBody)

            val id: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), consignmentid.toString())



            viewModel.pickupimageupdate(id = id, part = part).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideLoading()
                            resource.data?.let {itResponse->

                                if (itResponse.status) {

                                    Picasso.get()
                                        .load(itResponse.orderImageUrl+"/"+
                                                itResponse.data.urc.pickupImage)
                                        .error(R.drawable.orderitem)
                                        .placeholder(R.drawable.orderitem)
                                        .into(fragmentOrderdetailsBinding.ivOrderdetailsimage)
                                    fragmentOrderdetailsBinding.ivOrderdetailsimage.rotation = 90F
                                    fragmentOrderdetailsBinding.btnNext.visibility = View.VISIBLE

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
                            }


                        }
                        Status.ERROR -> {
                            mainActivity.hideLoading()
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                            }
                            alert.show()
                        }

                        Status.LOADING -> {
                            mainActivity.showLoading()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }




}