package com.best1buy.shop.activity

import android.os.Bundle
import com.best1buy.shop.AppBaseActivity
import com.best1buy.shop.R
import android.widget.FrameLayout
import com.best1buy.shop.fragments.SignInFragment
import com.best1buy.shop.fragments.SignUpFragment
import com.best1buy.shop.utils.extensions.*


class SignInUpActivity : AppBaseActivity() {

    private val mSignInFragment: SignInFragment = SignInFragment()
    private val mSignUpFragment: SignUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)

        /**
         * Load Default Fragment
         */
        loadSignInFragment()
    }

    fun loadSignUpFragment() {
        if (mSignUpFragment.isAdded) {
            replaceFragment(mSignUpFragment, R.id.fragmentContainer)
            findViewById<FrameLayout>(R.id.fragmentContainer).fadeIn(500)
        } else {
            addFragment(mSignUpFragment, R.id.fragmentContainer)
        }
    }

    fun loadSignInFragment() {
        if (mSignInFragment.isAdded) {
            replaceFragment(mSignInFragment, R.id.fragmentContainer)
            findViewById<FrameLayout>(R.id.fragmentContainer).fadeIn(500)
        } else {
            addFragment(mSignInFragment, R.id.fragmentContainer)
        }
    }

    override fun onBackPressed() {
        when {
            mSignUpFragment.isVisible -> {
                removeFragment(mSignUpFragment)
            }
            else -> super.onBackPressed()

        }
    }
}