package com.best1buy.shop.network

import com.best1buy.shop.models.*
import com.best1buy.shop.utils.extensions.getApiToken
import com.best1buy.shop.utils.extensions.getUserId
import retrofit2.Call
import retrofit2.http.*

interface RestApis {

    @GET("iqonic-api/api/v1/woocommerce/get-dashboard")
    fun getDashboardData(): Call<Dashboard>

    @GET("wc/v3/products")
    fun listAllProducts(): Call<ArrayList<StoreProductModel>>

    @GET("iqonic-api/api/v1/woocommerce/get-product-details")
    fun listSingleProducts(
        @Query("product_id") product_id: Int,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ArrayList<StoreProductModel>>

    @GET("wc/v3/products/categories")
    fun listAllCategory(@QueryMap options:Map<String, Int>): Call<ArrayList<Category>>

    @GET("wc/v3/products")
    fun listSingleCategory(@Query("category") categories: Int): Call<ArrayList<StoreProductModel>>

    @GET("wc/v3/products")
    fun listAllCategoryProduct(@QueryMap options:Map<String, Int>): Call<ArrayList<StoreProductModel>>

    @GET("wc/v1/products/{id}/reviews")
    fun listReview(@Path("id") id: Int): Call<ArrayList<ProductReviewData>>

    @POST("wc/v3/products/reviews")
    fun createProductReview(@Body request: RequestModel): Call<ProductReviewData>

    @HTTP(method = "DELETE", path = "wc/v3/products/reviews/{id}", hasBody = true)
    fun deleteProductReview(@Path("id") id: Int): Call<ProductReviewData>

    @HTTP(method = "PUT", path = "wc/v3/products/reviews/{id}", hasBody = true)
    fun updateProductReview(
        @Path("id") id: Int,
        @Body request: RequestModel
    ): Call<ProductReviewData>

    @GET("wc/v3/Coupons")
    fun GetCoupon(): Call<ArrayList<Coupons>>

    @GET("wc/v3/Coupons")
    fun FindCoupon(@Query("code") code: String): Call<ArrayList<Coupons>>

    @POST("iqonic-api/api/v1/woocommerce/get-product")
    fun Search(@Body options:SearchRequest): Call<StoreProductModelNew>

    @POST("iqonic-api/api/v1/woocommerce/get-product")
    fun getSaleProducts(@Body options:SearchRequest): Call<StoreProductModelNew>

    @GET("iqonic-api/api/v1/woocommerce/get-product-attributes")
    fun getProductAttribute(): Call<StoreProductAttribute>

    @POST("iqonic-api/api/v1/woocommerce/customer/registration")
    fun createCustomer(@Body request: RequestModel): Call<RegisterResponse>

    @POST("jwt-auth/v1/token")
    fun login(@Body request: RequestModel): Call<loginResponse>

    @GET("wc/v3/customers/{id}")
    fun retrieveCustomer(@Path("id") id: String = getUserId()): Call<CustomerData>

    @POST("wc/v3/customers/{id}")
    fun updateCustomer(@Path("id") id: String, @Body request: RequestModel): Call<CustomerData>

    @POST("iqonic-api/api/v1/cart/add-cart/")
    fun addItemToCart(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<AddCartResponse>

    @GET("iqonic-api/api/v1/cart/get-cart/")
    fun getCart(
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ArrayList<CartResponse>>

    @POST("iqonic-api/api/v1/woocommerce/get-stripe-client-secret")
    fun getStripeClientSecret(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<GetStripeClientSecret>


    @POST("wc/v3/orders/{order_id}/notes")
    fun createOrderNotes(
        @Path("order_id") ids: Int,
        @Body request: CreateOrderNotes
    ): Call<BaseResponse>

    @POST("iqonic-api/api/v1/woocommerce/get-checkout-url")
    fun getCheckoutUrl(
        @Body request: CheckoutUrlRequest,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<CheckoutResponse>

    @POST("wc/v3/orders")
    fun createOrder(
        @Body request: OrderRequest,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<CreateOrderResponse>

    @POST("iqonic-api/api/v1/cart/delete-cart/")
    fun removeCartItem(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<BaseResponse>

    @POST("iqonic-api/api/v1/cart/delete-cart/")
    fun removeMultipleCartItem(
        @Body request: CartRequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<BaseResponse>

    @POST("iqonic-api/api/v1/cart/update-cart/")
    fun updateItemInCart(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<UpdateCartResponse>

    @POST("iqonic-api/api/v1/wishlist/add-wishlist/")
    fun addWishList(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<BaseResponse>

    @GET("iqonic-api/api/v1/wishlist/get-wishlist/")
    fun getWishList(
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ArrayList<WishList>>

    @POST("wc/v3/orders/{order_id}")
    fun cancelOrder(
        @Path("order_id") ids: Int,
        @Body request: CancelOrderRequest
    ): Call<BaseResponse>

    @POST("iqonic-api/api/v1/wishlist/delete-wishlist/")
    fun removeWishList(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<BaseResponse>

    @GET("iqonic-api/api/v1/woocommerce/get-customer-orders")
    fun getOrderData(
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ArrayList<Order>>

    @GET("wc/v3/orders/{order_id}/notes")
    fun getOrderTracking(@Path("order_id") id: Int): Call<ArrayList<OrderNotes>>

    @POST("iqonic-api/api/v1/woocommerce/change-password")
    fun changePwd(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<BaseResponse>

    @POST("iqonic-api/api/v1/customer/save-profile-image")
    fun saveProfileImage(
        @Body request: RequestModel,
        @Header("Content-Type") type: String = "application/json",
        @Header(
            "token"
        ) token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ProfileImage>

    @POST("iqonic-api/api/v1/customer/forget-password")
    fun forgetPassword(@Body request: RequestModel): Call<BaseResponse>

    @POST("iqonic-api/api/v1/woocommerce/get-shipping-methods")
    fun getShippingMethod(
        @Body request: RequestModel,
        @Header("token") token: String = getApiToken(),
        @Header("id") id: String = getUserId()
    ): Call<ShippingModel>


    @GET("wc/v3/data/countries")
    fun listCountry(): Call<ArrayList<CountryModel>>

    @HTTP(method = "DELETE", path = "wc/v3/orders/{id}", hasBody = true)
    fun deleteOrder(@Path("id") id: Int): Call<BaseResponse>

    @POST("woobox-api/api/v1/customer/social_login")
    fun socialLogin(@Body request: RequestModel): Call<loginResponse>
}