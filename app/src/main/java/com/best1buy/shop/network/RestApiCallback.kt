package com.best1buy.shop.network

import androidx.annotation.NonNull


interface RestApiCallback<T, K> {

    fun onSuccess(aApiCode: Int, aSuccessResponse: T)

    fun onApiError(aApiCode: Int, aFailureResponse: K)

}
