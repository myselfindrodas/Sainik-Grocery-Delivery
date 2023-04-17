package com.grocery.sainikgrocerydelivery.ui.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.databinding.FragmentDeliveryDetailsBinding
import com.grocery.sainikgrocerydelivery.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*


class DeliveryDetailsFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentDeliveryDetailsBinding: FragmentDeliveryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentDeliveryDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_details, container, false)
        val root = fragmentDeliveryDetailsBinding.root
        mainActivity = activity as MainActivity

        return root
    }

    private val myCalendar: Calendar? = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentDeliveryDetailsBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }
        with(fragmentDeliveryDetailsBinding) {

            llNavigate.setOnClickListener {
                openGoogleMapsNavigationToB(mainActivity,22.606357589755948,88.42309460204574)
            }

            llReturn.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_returnorder)
            }


            llRescheduleOn.visibility = View.GONE
            val date =
                OnDateSetListener { view, year, month, day ->
                    myCalendar?.set(Calendar.YEAR, year)
                    myCalendar?.set(Calendar.MONTH, month)
                    myCalendar?.set(Calendar.DAY_OF_MONTH, day)
                    updateLabel(tvRescheduleDate)
                }

            llRescheduleBtn.setOnClickListener {
                val dateDialog = DatePickerDialog(
                    mainActivity,
                    date,
                    myCalendar!![Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dateDialog.setOnShowListener {

                    dateDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
                    dateDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
                }
                dateDialog.show()

            }
        }

    }
    fun openGoogleMapsNavigationToB(context: Context,
                                    latitude : Double,
                                    longitude : Double)
    {
        val googleMapsUrl = "google.navigation:q=$latitude,$longitude&dirflg=d"
        val uri = Uri.parse(googleMapsUrl)

        val googleMapsPackage = "com.google.android.apps.maps"
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage(googleMapsPackage)
        }

        context.startActivity(intent)
    }
    private fun updateLabel(textView: TextView) {
        val myFormat = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        textView.text = dateFormat.format(myCalendar!!.time)

        fragmentDeliveryDetailsBinding.llRescheduleOn.visibility = View.VISIBLE
    }

}