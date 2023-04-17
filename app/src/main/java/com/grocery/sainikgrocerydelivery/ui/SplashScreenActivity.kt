package com.grocery.sainikgrocerydelivery.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.grocery.sainikgrocerydelivery.R
import com.grocery.sainikgrocerydelivery.base.BaseActivity
import com.grocery.sainikgrocerydelivery.databinding.ActivitySplashBinding
import com.grocery.sainikgrocerydelivery.utils.AnimUtils
import com.grocery.sainikgrocerydelivery.utils.Shared_Preferences

class SplashScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun resourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashBinding
    }

    override fun setFunction() {

        callNextScreen()
    }

    private fun callNextScreen() {
        Handler(Looper.myLooper()!!).postDelayed({


            if (Shared_Preferences.getLoginStatus()==true){

                val intent = Intent(this, URCListActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()

            }else{

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()
            }


        }, 3000)
    }
}