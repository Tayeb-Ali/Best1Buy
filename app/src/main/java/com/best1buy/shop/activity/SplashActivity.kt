package com.best1buy.shop.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.best1buy.shop.AppBaseActivity
import com.best1buy.shop.R
import android.view.WindowManager
import com.best1buy.shop.utils.Constants.SharedPref.MODE
import com.best1buy.shop.utils.extensions.*
import kotlinx.android.synthetic.main.activity_splash_new.*

class SplashActivity : AppBaseActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_new)

        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        runDelayed(1500) {
            launchActivity<WalkThroughActivity>()
            finish()
        }
        rlMain.changeBackgroundColor()
        getSharedPrefInstance().removeKey(MODE)
        getSharedPrefInstance().setValue(MODE,2)
    }
}