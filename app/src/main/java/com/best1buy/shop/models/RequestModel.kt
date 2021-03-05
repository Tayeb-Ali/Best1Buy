package com.best1buy.shop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class RequestModel {
    /**
     * Request for login
     */
    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    var accessToken: String? = null
    var loginType: String? = null
    var photoURL: String? = null

    @SerializedName("new_password")
    @Expose
    var new_password: String? = null
    /**
     * Request for post product review
     */
    @SerializedName("product_id")
    @Expose
    var product_id: Int? = null

    @SerializedName("review")
    @Expose
    var review: String? = null

    @SerializedName("reviewer")
    @Expose
    var reviewer: String? = null

    @SerializedName("reviewer_email")
    @Expose
    var reviewer_email: String? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null

    /**
     * Request for update order
     */
    @SerializedName("status")
    @Expose
    var status: String? = null

    /**
     * Request for delete product review
     */
    @SerializedName("force")
    @Expose
    var force: Boolean? = null

    /**
     * Request for generate client token for stripe payment
     */
    @SerializedName("apiKey")
    @Expose
    var apiKey: String? = null

    @SerializedName("amount")
    @Expose
    var amount: Int? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null


    /**
     * Request for update review
     */
    @SerializedName("id")
    @Expose
    var id: String? = null


    /**
     * Request for add item to cart
     */
    @SerializedName("pro_id")
    @Expose
    var pro_id: Int? = null

    @SerializedName("cart_id")
    @Expose
    var cartid: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    /**
     * Request for Create /update cart item
     */
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("email")
    @Expose
    var userEmail: String? = null

    @SerializedName("profile_image")
    @Expose
    var image: String? = null


    /**
     * Request for search item
     */
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("search")
    @Expose
    var search: String? = null


    // Profile
    @SerializedName("billing")
    @Expose
    var billing: Billing? = null

    @SerializedName("shipping")
    @Expose
    var shipping: Shipping? = null
    var base64_img: String? = null

    // Shipping
    @SerializedName("country_code")
    @Expose
    var country_code: String? = null

    @SerializedName("state_code")
    @Expose
    var state_code: String? = null

    @SerializedName("postcode")
    @Expose
    var postcode: String? = null

}
 class SearchRequest {
     var attribute: ArrayList<Map<String, Any?>>?=null;
     var category: List<Int> ?=null;
     var price: List<Int> ?=null;
     var text: String ?=null
     var Optional_selling: String?=null
     var on_sale: String ?=null
     var newest: String ?=null
     var featured: String ?=null
     var page: Int = 0
     var product_per_page: Int = 0
     var special_product:String?=null
}

class CategoryRequest {
    var page: Int = 0
    var parent: Int = 0
}
