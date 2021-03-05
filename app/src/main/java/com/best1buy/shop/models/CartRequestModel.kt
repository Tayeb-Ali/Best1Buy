package com.best1buy.shop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CartRequestModel {

    @SerializedName("pro_id")
    @Expose
    var multpleId: String? = null

}
