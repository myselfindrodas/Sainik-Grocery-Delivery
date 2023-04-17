package com.grocery.sainikgrocerydelivery.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.databinding.DeliveredOtpDialogBinding
import com.grocery.sainikgrocerydelivery.databinding.OtpDialogBinding
import java.security.AccessController.getContext
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


object Utilities {

    fun makeShortToast(context: Context?, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun makeLongToast(context: Context?, text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    /* fun isNetworkConnected(context: Context?):Boolean{
         val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val nw      = connectivityManager.activeNetwork ?: return false
         val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
         return when {
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
             //for other device how are able to connect with Ethernet
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
             //for check internet over Bluetooth
             actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
             else -> false
         }
     }*/


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    true
                } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } else {
                false
            }
        } else {
            val info = connectivityManager.activeNetworkInfo
            info != null && info.isConnected
        }
    }

    fun getProfileNameLogo(text: String): String {
        return if (text.trim().substringAfterLast(" ").isNotEmpty()) {
            (text.trim().substring(0, 1) + text.trim().substringAfterLast(" ")
                .substring(0, 1)).uppercase()
        } else {
            (text.trim().substring(0, 1) + text.trim().substring(1, 2)).uppercase()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkConnected(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        //1
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        val version = Build.VERSION.SDK_INT
        return if (version >= 21) {
            ContextCompat.getDrawable(context, id)
        } else {
            context.resources.getDrawable(id)
        }
    }

    fun isEmail(text: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val p: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val m: Matcher = p.matcher(text)
        return m.matches()
    }

    fun isPhone(text: String?): Boolean {
        return if (text != null) {
            if (!TextUtils.isEmpty(text)) {
                TextUtils.isDigitsOnly(text) && text.trim().length == 10
            } else {
                false
            }
        } else {
            false
        }
    }

    fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                enableDisableView(group.getChildAt(idx), enabled)
            }
        }
    }

    @SuppressLint("Range")
    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        val fileName: String?
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        fileName = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        cursor?.close()
        return fileName
    }

    @SuppressLint("Range")
    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(column_index)
        cursor.close()
        return path
    }

    private fun getfileExtension(context: Context, uri: Uri): String {
        val extension: String
        val contentResolver: ContentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))!!
        return extension
    }

    fun alertOTPDialogUtil(
        context: Context?,
        mobileNo: String,
        isCancelable: Boolean = false,
        onItemClickListener: OnItemClickListener
    ) {
        val binding: OtpDialogBinding = OtpDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context!!,R.style.CustomAlertDialog).create()
      /*  builder.setTitle(title)
        builder.setMessage(message)*/
        builder.setCancelable(isCancelable)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        otpTimer(binding,context)
        with(binding){
            val otptext = ArrayList<EditText>()
            otptext.add(otp1)
            otptext.add(otp2)
            otptext.add(otp3)
            otptext.add(otp4)
            setOtpEditTextHandler(otptext)
            btnVerify.setOnClickListener {
                val otp = otp1.text.toString() +
                        otp2.text.toString() +
                        otp3.text.toString() +
                        otp4.text.toString()

                if (otp.length > 3) {
                    onItemClickListener.onItemClickAction(1, builder, otp)
                    builder.dismiss()
                } else {
                    Toast.makeText(context, "Enter valid OTP", Toast.LENGTH_SHORT).show()
                }
            }

            tvTitle.text = "We are send a OTP to your mobile number $mobileNo"
            tvResentOTP.setOnClickListener {
                otpTimer(binding,context)
            }
        }
        builder.setView(binding.root)
        /*
        if (isPositive) {
            builder.setPositiveButton(positiveTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(1, dialog)

                *//*Toast.makeText(
                    context,
                    positiveTxt, Toast.LENGTH_SHORT
                ).show()*//*
            }
        }

        if (isNegetive) {
            builder.setNegativeButton(negetiveTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(2, dialog)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.cancelled), Toast.LENGTH_SHORT
                ).show()
            }

        }
        if (isNeutral) {
            builder.setNeutralButton(neutralTxt) { dialog, which ->
                onItemClickListener.onItemClickAction(3, dialog)
                Toast.makeText(
                    context,
                    neutralTxt, Toast.LENGTH_SHORT
                ).show()
            }
        }*/
        builder.setOnDismissListener {
            if (countDownTimer!=null){
                countDownTimer?.cancel()
                countDownTimer=null
            }
        }
        builder.show()
    }


    fun alertDeliveryOTPDialogUtil(
        context: Context?,
        userName: String,
        isCancelable: Boolean = false,
        onItemClickListener: OnItemClickListener
    ) {
        val binding: DeliveredOtpDialogBinding = DeliveredOtpDialogBinding
            .inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context!!,R.style.CustomAlertDialog).create()

        builder.setCancelable(isCancelable)


        with(binding){
            val otptext = ArrayList<EditText>()
            otptext.add(otp1)
            otptext.add(otp2)
            otptext.add(otp3)
            otptext.add(otp4)
            setOtpEditTextHandler(otptext)
            clDeliveryOtp.visibility=View.VISIBLE
            clDeliverySuccess.visibility=View.GONE
            btnVerify.setOnClickListener {
                val otp = otp1.text.toString() +
                        otp2.text.toString() +
                        otp3.text.toString() +
                        otp4.text.toString()

                if (otp.length > 3) {

                    clDeliveryOtp.visibility=View.GONE
                    clDeliverySuccess.visibility=View.VISIBLE
                    builder.setCancelable(true)
                    onItemClickListener.onItemClickAction(1, builder, otp)
                    //builder.dismiss()
                } else {
                    Toast.makeText(context, "Enter valid OTP", Toast.LENGTH_SHORT).show()
                }
            }

            tvTitle.text = "Please enter OTP from $userName"

        }
        builder.setView(binding.root)

        builder.setOnDismissListener {
            if (countDownTimer!=null){
                countDownTimer?.cancel()
                countDownTimer=null
            }
        }
        builder.show()
    }

    var countDownTimer: CountDownTimer? = null
    private fun otpTimer(binding: OtpDialogBinding, context: Context) {
        if (countDownTimer!=null){
            countDownTimer?.cancel()
        }
       countDownTimer = object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.tvExpire.text = "00:" + String.format(
                    Locale.ENGLISH, "%02d", millisUntilFinished / 1000
                )
                binding.tvResentOTP.isEnabled = false
                binding.tvResentOTP.isClickable = false
                binding.tvResentOTP.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.grey_light,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.grey_light
                        )
                    }
                )
            }

            override fun onFinish() {
                binding.tvExpire.text = "00:00"
                binding.tvResentOTP.isEnabled = true
                binding.tvResentOTP.isClickable = true

                binding.tvResentOTP.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.orange,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.orange
                        )
                    }
                )

            }
        }.start()
    }


    private fun setOtpEditTextHandler(otpEt: ArrayList<EditText>) { //This is the function to be called
        for (i in 0..3) { //Its designed for 6 digit OTP
            otpEt.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (i == 3 && otpEt[i].text.toString().isNotEmpty()) {

                    } else if (otpEt[i].text.toString().isNotEmpty()) {
                        otpEt[i + 1]
                            .requestFocus()
                    }
                }
            })
            otpEt[i].setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    otpEt[i].text.toString().isEmpty() && i != 0
                ) {
                    otpEt[i - 1].setText("")
                    otpEt[i - 1].requestFocus()
                }
                false
            })
        }
    }


    interface OnItemClickListener {
        fun onItemClickAction(type: Int, dialogInterface: DialogInterface, otp:String)
    }

}