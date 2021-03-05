package com.best1buy.shop.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.best1buy.shop.AppBaseActivity
import com.best1buy.shop.R
import com.best1buy.shop.fragments.BaseFragment
import com.best1buy.shop.models.RequestModel
import com.best1buy.shop.utils.extensions.*
import kotlinx.android.synthetic.main.activity_change_pwd.*
import kotlinx.android.synthetic.main.toolbar.*

class ChangePwdActivity : AppBaseActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwd)
        setToolbar(toolbar)
        title = getString(R.string.lbl_change_pwd)
        mAppBarColor()
        changeColor()
        edtOldPwd.transformationMethod = BaseFragment.BiggerDotTransformation
        edtNewPwd.transformationMethod = BaseFragment.BiggerDotTransformation
        edtConfirmPwd.transformationMethod = BaseFragment.BiggerDotTransformation
        btnChangePassword.onClick {
            when {
                edtOldPwd.checkIsEmpty() -> {
                    edtOldPwd.showError(getString(R.string.error_field_required))
                }
                edtNewPwd.checkIsEmpty() -> {
                    edtNewPwd.showError(getString(R.string.error_field_required))
                }
                edtNewPwd.validPassword() -> {
                    edtNewPwd.showError(getString(R.string.error_pwd_digit_required))
                }
                edtConfirmPwd.checkIsEmpty() -> {
                    edtConfirmPwd.showError(getString(R.string.error_field_required))
                }
                edtConfirmPwd.validPassword() -> {
                    edtConfirmPwd.showError(getString(R.string.error_pwd_digit_required))
                }
                !edtConfirmPwd.text.toString().equals(
                        edtNewPwd.text.toString(),
                        false
                ) -> {
                    edtConfirmPwd.showError(getString(R.string.error_password_not_matches))
                }
                else -> {
                    val requestModel = RequestModel()
                    requestModel.password = edtOldPwd.text.toString()
                    requestModel.new_password = edtNewPwd.text.toString()
                    requestModel.username = getUserName()
                    showProgress(true)
                    if (isNetworkAvailable()) {
                        showProgress(true)
                        getRestApiImpl().changePwd(requestModel, onApiSuccess = {
                            showProgress(false)
                            snackBar(it.message!!)
                        }, onApiError = {
                            showProgress(false)
                            snackBar(it)
                        })
                    } else {
                        showProgress(false)
                        noInternetSnackBar()
                    }

                }
            }
        }
    }

    private fun changeColor() {
        btnChangePassword.changeBackgroundTint(getButtonColor())
        edtOldPwd.changeTextPrimaryColor()
        edtNewPwd.changeTextPrimaryColor()
        edtConfirmPwd.changeTextPrimaryColor()
        llMain.changeBackgroundColor()
    }
}
