package com.grocery.sainikgrocerydelivery.ui.fragment

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.ApiClient
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.model.consignmentmodel.ConsignmentRequestModel
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.FragmentURClistBinding
import com.grocery.sainikgrocerydelivery.ui.LoginActivity
import com.grocery.sainikgrocerydelivery.ui.MainActivity
import com.grocery.sainikgrocerydelivery.ui.adapter.UrcAdapter
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel

class URClistFragment : Fragment() , UrcAdapter.OnItemClickListener{

    lateinit var mainActivity: MainActivity
    lateinit var fragmentURClistBinding: FragmentURClistBinding
    private var urcAdapter:UrcAdapter?=null
    private lateinit var viewModel: CommonViewModel

    var mIsLoading = false;
    var mIsLastPage = false;
    var mCurrentPage = 0;
    var pageSize = 10;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentURClistBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_u_r_clist, container, false)
        val root = fragmentURClistBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentURClistBinding.topnav.tvUrcaddress.text = Shared_Preferences.getaddress()


        urcAdapter = UrcAdapter(mainActivity, this@URClistFragment)
        fragmentURClistBinding.rvURClist.adapter = urcAdapter
        fragmentURClistBinding.rvURClist.layoutManager = GridLayoutManager(mainActivity, 1)
        fragmentURClistBinding.rvURClist.addOnScrollListener(recyclerOnScroll)


        consignmentList(true, Shared_Preferences.getUrcid().toString())


        fragmentURClistBinding.topnav.btnLogout.setOnClickListener {

            val builder = AlertDialog.Builder(mainActivity)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                logout()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.orange,resources.newTheme()))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.orange,resources.newTheme()))
            }
            alert.show()
        }


    }





    override fun onClick(position: Int, view: View, id: Int) {

        val bundle = Bundle()
        bundle.putInt("consignmentid", id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_orderdetails, bundle)

    }


    val recyclerOnScroll = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // number of visible items
            val visibleItemCount = recyclerView.layoutManager?.childCount;
            // number of items in layout
            val totalItemCount = recyclerView.layoutManager?.itemCount;
            // the position of first visible item
            val firstVisibleItemPosition =
                (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

            val isNotLoadingAndNotLastPage = !mIsLoading && !mIsLastPage;
            // flag if number of visible items is at the last
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount!! >= totalItemCount!!;
            // validate non negative values
            val isValidFirstItem = firstVisibleItemPosition >= 0;
            // validate total items are more than possible visible items
            val totalIsMoreThanVisible = totalItemCount >= pageSize;
            // flag to know whether to load more
            val shouldLoadMore =
                isValidFirstItem && isAtLastItem && totalIsMoreThanVisible && isNotLoadingAndNotLastPage

            if (shouldLoadMore) consignmentList(false, Shared_Preferences.getUrcid().toString());
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsLoading = false
        mIsLastPage = false
        mCurrentPage = 0
    }


    private fun consignmentList(isFirstPage: Boolean, urcid: String){
        if (Utilities.isNetworkAvailable(mainActivity)) {

            mIsLoading = true
            if (isFirstPage)
                mCurrentPage = 1
            else
                mCurrentPage += 1

            viewModel.consignmentlist(
                ConsignmentRequestModel(urc_id = urcid), mCurrentPage.toString()
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideLoading()
                            resource.data?.let { itProfileInfo ->
                                if (isFirstPage)
                                    urcAdapter?.updateData(itProfileInfo.data.order.data)
                                else
                                    urcAdapter?.addData(
                                    itProfileInfo.data.order.data
                                )
                                mIsLoading = false
                                mIsLastPage =
                                    mCurrentPage == itProfileInfo.data.order.lastPage
                                pageSize = itProfileInfo.data.order.perPage
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


    private fun logout() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.logout()
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideLoading()
                                if (resource.data?.status == true) {

                                    Shared_Preferences.setLoginStatus(false)
                                    Shared_Preferences.clearPref()
                                    val intent = Intent(mainActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    mainActivity.finish()

                                } else {

                                    Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()

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
                                alert.show()
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


}