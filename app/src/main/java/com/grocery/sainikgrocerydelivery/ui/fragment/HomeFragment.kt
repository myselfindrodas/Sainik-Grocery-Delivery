package com.grocery.sainikgrocerydelivery.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.ApiClient
import com.grocery.sainikgrocerydelivery.data.ApiHelper
import com.grocery.sainikgrocerydelivery.data.modelfactory.CommonModelFactory
import com.grocery.sainikgrocerydelivery.databinding.FragmentHomeBinding
import com.grocery.sainikgrocerydelivery.ui.LoginActivity
import com.grocery.sainikgrocerydelivery.ui.MainActivity
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences
import com.grocery.sainikgrocerydelivery.utils.Status
import com.grocery.sainikgrocerydelivery.utils.Utilities
import com.grocery.sainikgrocerydelivery.viewmodel.CommonViewModel

class HomeFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root = fragmentHomeBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHomeBinding.topnav.tvUrcaddress.text = Shared_Preferences.getaddress()

        fragmentHomeBinding.btnProcceed.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_urc)
        }


        fragmentHomeBinding.topnav.btnLogout.setOnClickListener {

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