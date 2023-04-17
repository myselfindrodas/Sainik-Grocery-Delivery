package com.grocery.sainikgrocerydelivery.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.data.model.itemmodel.ItemModel
import com.grocery.sainikgrocerydelivery.databinding.FragmentDeliveryDetailsBinding
import com.grocery.sainikgrocerydelivery.databinding.FragmentOrderdetailsBinding
import com.grocery.sainikgrocerydelivery.databinding.FragmentReturnOrderBinding
import com.grocery.sainikgrocerydelivery.ui.MainActivity
import com.grocery.sainikgrocerydelivery.ui.adapter.ItemAdapter
import com.grocery.sainikgrocerydelivery.ui.adapter.ReturnItemAdapter
import java.text.SimpleDateFormat
import java.util.*


class ReturnOrderFragment : Fragment(), ReturnItemAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentReturnOrderBinding: FragmentReturnOrderBinding

    private var itemAdapter: ReturnItemAdapter?=null
    val itemModelArrayList: ArrayList<ItemModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReturnOrderBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_return_order, container, false)
        val root = fragmentReturnOrderBinding.root
        mainActivity = activity as MainActivity




        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..5) {

            itemModelArrayList.add(
                ItemModel(
                    "Daawat biryani rice",
                    "15KG"
                )
            )

        }



        itemAdapter =
            ReturnItemAdapter(mainActivity, this@ReturnOrderFragment)
        fragmentReturnOrderBinding.rvOrderitems.adapter = itemAdapter
        fragmentReturnOrderBinding.rvOrderitems.layoutManager = GridLayoutManager(mainActivity, 1)

        itemAdapter?.updateData(itemModelArrayList)


        fragmentReturnOrderBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }





    }


    override fun onClick(position: Int, view: View) {

    }

}