package com.best1buy.shop.models

data class CancelOrderRequest(
    var status: String = "",
    var customer_note: String = ""
)

