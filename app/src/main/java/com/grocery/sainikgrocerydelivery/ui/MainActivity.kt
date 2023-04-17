package com.grocery.sainikgrocerydelivery.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.base.BaseActivity
import com.grocery.sainikgrocerydelivery.databinding.ActivityMainBinding

class MainActivity : BaseActivity(),
    NavController.OnDestinationChangedListener{

    lateinit var activityMainBinding: ActivityMainBinding
    var mNavController: NavController? = null

    override fun resourceLayout(): Int {
        return R.layout.activity_main
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.activityMainBinding = binding as ActivityMainBinding

    }

    override fun setFunction() {


        mNavController = findNavController(R.id.flFragment)

        mNavController?.addOnDestinationChangedListener(this)
        mNavController?.navigate(R.id.nav_home)

    }

    fun showLoading(){
        activityMainBinding.rlLoading.visibility=View.VISIBLE
    }
    fun hideLoading(){
        activityMainBinding.rlLoading.visibility=View.GONE
    }


    override fun onDestroy() {
        findNavController(R.id.flFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        hideKeyboard()

    }
}