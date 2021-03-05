package com.best1buy.shop.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.best1buy.shop.AppBaseActivity
import com.best1buy.shop.R
import com.best1buy.shop.ProShopApp
import com.best1buy.shop.activity.*
import com.best1buy.shop.adapter.BaseAdapter
import com.best1buy.shop.adapter.HomeSliderAdapter
import com.best1buy.shop.models.*
import com.best1buy.shop.utils.Constants
import com.best1buy.shop.utils.Constants.KeyIntent.EXTERNAL_URL
import com.best1buy.shop.utils.Constants.KeyIntent.IS_BANNER
import com.best1buy.shop.utils.Constants.KeyIntent.TITLE
import com.best1buy.shop.utils.Constants.SharedPref.CONTACT
import com.best1buy.shop.utils.Constants.SharedPref.COPYRIGHT_TEXT
import com.best1buy.shop.utils.Constants.SharedPref.DEFAULT_CURRENCY
import com.best1buy.shop.utils.Constants.SharedPref.DEFAULT_CURRENCY_FORMATE
import com.best1buy.shop.utils.Constants.SharedPref.ENABLE_COUPONS
import com.best1buy.shop.utils.Constants.SharedPref.FACEBOOK
import com.best1buy.shop.utils.Constants.SharedPref.INSTAGRAM
import com.best1buy.shop.utils.Constants.SharedPref.LANGUAGE
import com.best1buy.shop.utils.Constants.SharedPref.PAYMENT_METHOD
import com.best1buy.shop.utils.Constants.SharedPref.PRIVACY_POLICY
import com.best1buy.shop.utils.Constants.SharedPref.TERM_CONDITION
import com.best1buy.shop.utils.Constants.SharedPref.TWITTER
import com.best1buy.shop.utils.Constants.SharedPref.WHATSAPP
import com.best1buy.shop.utils.Constants.viewName.VIEW_BANNER
import com.best1buy.shop.utils.Constants.viewName.VIEW_BEST_SELLING
import com.best1buy.shop.utils.Constants.viewName.VIEW_DEAL_OF_THE_DAY
import com.best1buy.shop.utils.Constants.viewName.VIEW_FEATURED
import com.best1buy.shop.utils.Constants.viewName.VIEW_NEWEST
import com.best1buy.shop.utils.Constants.viewName.VIEW_OFFER
import com.best1buy.shop.utils.Constants.viewName.VIEW_SALE
import com.best1buy.shop.utils.Constants.viewName.VIEW_SUGGESTED_FOR_YOU
import com.best1buy.shop.utils.Constants.viewName.VIEW_YOU_MAY_LIKE
import com.best1buy.shop.utils.dotsindicator.DotsIndicator
import com.best1buy.shop.utils.extensions.*
import kotlinx.android.synthetic.main.dashboard_dealofferview.view.*
import kotlinx.android.synthetic.main.fragment_home1.*
import kotlinx.android.synthetic.main.item_home_dashboard1.view.*
import kotlinx.android.synthetic.main.menu_cart.view.*

class HomeFragment1 : BaseFragment() {
    private var mMenuCart: View? = null
    private var image: String = ""
    private var isAddedToCart: Boolean = false
    private lateinit var lan: String
    private var mViewNewest: View? = null
    private var mViewFeatured: View? = null
    private var mViewDealOfTheDay: View? = null
    private var mViewOffer: View? = null
    private var mViewBestSelling: View? = null
    private var mViewSale: View? = null
    private var mViewYouMayLike: View? = null
    private var mViewSuggested: View? = null
    private var mSliderView: View? = null
    private var mLLDynamic: LinearLayout? = null
    private lateinit var mDashboardJson: BuilderDashboard

    private fun setProductItem(
            view: View,
            model: StoreProductModel
    ) {
        if (model.images!![0].src!!.isNotEmpty()) {
            view.ivProduct.loadImageFromUrl(model.images!![0].src!!)
            image = model.images!![0].src!!
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.ivProduct.outlineProvider = object : ViewOutlineProvider() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?) {
                    outline!!.setRoundRect(0, 0, view!!.width, (view.height + 20F).toInt(), 20F)
                }
            }
            view.ivProduct.clipToOutline = true
        }

        val mName = model.name!!.split(",")
        view.tvProductName.text = mName[0]
        view.tvProductName.changeTextPrimaryColor()

        if (model.onSale) {
            view.tvSaleLabel.show()
        } else {
            view.tvSaleLabel.hide()
        }
        if (model.type!!.contains("grouped")) {
            view.tvDiscountPrice.hide()
            view.tvOriginalPrice.hide()
            view.tvAdd.hide()
        } else {
            if (!model.onSale) {
                view.tvDiscountPrice.text = model.regularPrice!!.currencyFormat()
                view.tvOriginalPrice.show()
                if (model.regularPrice!!.isEmpty()) {
                    view.tvOriginalPrice.text = ""
                    view.tvDiscountPrice.text = model.price!!.currencyFormat()
                } else {
                    view.tvOriginalPrice.text = ""
                    view.tvDiscountPrice.text = model.regularPrice!!.currencyFormat()
                }
            } else {
                if (model.salePrice!!.isNotEmpty()) {
                    view.tvDiscountPrice.text = model.salePrice!!.currencyFormat()
                } else {
                    view.tvOriginalPrice.show()
                    view.tvDiscountPrice.text = model.price!!.currencyFormat()
                }
                view.tvOriginalPrice.applyStrike()
                view.tvOriginalPrice.text = model.regularPrice!!.currencyFormat()
                view.tvOriginalPrice.show()
            }
            view.tvOriginalPrice.changeTextSecondaryColor()
            view.tvDiscountPrice.changeTextPrimaryColor()

            view.tvAdd.background.setTint(Color.parseColor(getAccentColor()))
            if (model.attributes!!.isNotEmpty()) {
                if(model.attributes!![0].options!!.isNotEmpty()){
                    view.tvProductWeight.text = model.attributes!![0].options!![0]
                    view.tvProductWeight.changeTextSecondaryColor()
                }
            }
            if (model.in_stock) {
                view.tvAdd.show()
            } else {
                view.tvAdd.hide()
            }
            if (!model.purchasable) {
                view.tvAdd.hide()
            } else {
                view.tvAdd.show()
            }
        }


        view.onClick {
            if (getProductDetailConstant() == 0) {
                activity?.launchActivity<ProductDetailActivity1> {
                    putExtra(Constants.KeyIntent.PRODUCT_ID, model.id)
                    putExtra(Constants.KeyIntent.DATA, model)
                }
            } else {
                activity?.launchActivity<ProductDetailActivity2> {
                    putExtra(Constants.KeyIntent.PRODUCT_ID, model.id)
                    putExtra(Constants.KeyIntent.DATA, model)
                }
            }
        }
        view.tvAdd.onClick {
            mAddCart(model)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home1, container, false)


    private fun mAddCart(model: StoreProductModel) {
        if (isLoggedIn()) {
            val requestModel = RequestModel()
            if (model.type == "variable") {
                requestModel.pro_id = model.variations!![0]
            } else {
                requestModel.pro_id = model.id
            }
            requestModel.quantity = 1
            (activity as AppBaseActivity).addItemToCart(requestModel, onApiSuccess = {
                isAddedToCart = true
                activity!!.fetchAndStoreCartData()
            })
        } else (activity as AppBaseActivity).launchActivity<SignInUpActivity> { }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (isLoggedIn()) {
            loadApis()
        }
        listAllProducts()
        refreshLayout.setOnRefreshListener {
            dashboardMainView!!.removeAllViews()
            listAllProducts()
            refreshLayout.isRefreshing = false
        }
        refreshLayout.viewTreeObserver.addOnScrollChangedListener {
            refreshLayout.isEnabled = scrollView.scrollY == 0
        }
        mLLDynamic = view.findViewById(R.id.dashboardMainView)
        scrollView.changeBackgroundColor()

        mDashboardJson = getBuilderDashboard()
        mSliderUI()
        mProductUI()
    }

    @SuppressLint("InflateParams")
    private fun mProductUI() {
        val inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mViewNewest = inflater.inflate(R.layout.dashboard_productlist, null)
        mViewFeatured = inflater.inflate(R.layout.dashboard_productlist, null)
        mViewDealOfTheDay = inflater.inflate(R.layout.dashboard_dealofferview, null)
        mViewOffer = inflater.inflate(R.layout.dashboard_dealofferview, null)
        mViewBestSelling = inflater.inflate(R.layout.dashboard_productlist, null)
        mViewSale = inflater.inflate(R.layout.dashboard_productlist, null)
        mViewYouMayLike = inflater.inflate(R.layout.dashboard_productlist, null)
        mViewSuggested = inflater.inflate(R.layout.dashboard_productlist, null)
    }

    @SuppressLint("InflateParams")
    private fun mSliderUI() {
        val inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mSliderView = inflater.inflate(R.layout.dashboard_sliderview, null)
    }

    private fun onAddView(
            mView: View?,
            isGridView: Boolean = false,
            title: String,
            mViewAll: String,
            code: Int,
            specialKey: String = "",
            productList: List<StoreProductModel>,
            modelSize: Int = 5
    ) {
        val recyclerView = mView!!.findViewById(R.id.rvNewProduct) as RecyclerView
        val viewAllProduct = mView.findViewById(R.id.viewAllItem) as TextView
        val titleProduct = mView.findViewById(R.id.tvTitleBar) as TextView

        if (mView == mViewDealOfTheDay || mView == mViewOffer) {
            mView.llDeal.changeTint(getPrimaryColor())
            mView.viewAllItem.changeBackgroundTint(getAccentColor())
            titleProduct.changeTitleColor()
        } else {
            mView.viewAllItem.changeTextSecondaryColor()
            titleProduct.changeAccentColor()
        }
        mView.viewAllItem.text = mViewAll
        titleProduct.text = title

        if (isGridView) {
            val productAdapter = BaseAdapter<StoreProductModel>(
                    R.layout.item_viewproductgrid,
                    onBind = { view, model, _ ->
                        setProductItem(view, model)
                    })
            productAdapter.addItems(productList)
            recyclerView.adapter = productAdapter
            productAdapter.setModelSize(modelSize)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(activity, 2)
            }
        } else {
            val productAdapter = BaseAdapter<StoreProductModel>(
                    R.layout.item_home_dashboard1,
                    onBind = { view, model, _ ->
                        setProductItem(view, model)
                    })
            productAdapter.addItems(productList)
            productAdapter.setModelSize(modelSize)

            recyclerView.setHorizontalLayout()
            recyclerView.adapter = productAdapter
            productAdapter.setModelSize(4)
        }
        viewAllProduct.onClick {
            activity?.launchActivity<ViewAllProductActivity> {
                putExtra(TITLE, title)
                putExtra(Constants.KeyIntent.VIEWALLID, code)
                putExtra(Constants.KeyIntent.SPECIAL_PRODUCT_KEY, specialKey)
            }
        }
        if (mView.parent != null) {
            (mView.parent as ViewGroup).removeView(mView) // <- fix
        }
    }

    private fun addSlider(
            productList: List<DashboardBanner>
    ) {
        val slideViewPager = mSliderView!!.findViewById(R.id.slideViewPager) as ViewPager
        val dots = mSliderView!!.findViewById(R.id.dots) as DotsIndicator

        val adapter1 = HomeSliderAdapter(productList)
        slideViewPager.adapter = adapter1
        adapter1.setListener(object : HomeSliderAdapter.OnClickListener {
            override fun onClick(position: Int, mImg: List<DashboardBanner>) {
                launchActivity<WebViewExternalProductActivity> {
                    putExtra(EXTERNAL_URL, mImg[position].url)
                    putExtra(IS_BANNER, true)
                }
            }
        })
        dots.attachViewPager(slideViewPager)
        dots.setDotDrawable(R.drawable.bg_circle_primary, R.drawable.black_dot)
//        slideViewPager.pageMargin = resources.getDimensionPixelOffset(R.dimen._6sdp)
//        slideViewPager.setPageTransformer(
//            false,
//            object : CarouselEffectTransformer(activity) {})

        mLLDynamic!!.addView(mSliderView!!)

    }


    private fun showLoader() {
        (activity as AppBaseActivity).showProgress(true)
    }

    private fun loadApis() {
        if (isNetworkAvailable()) {
            activity!!.fetchAndStoreCartData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_dashboard, menu)
        val menuCartItem: MenuItem = menu.findItem(R.id.action_cart)
        menuCartItem.isVisible = true
        mMenuCart = menuCartItem.actionView
        mMenuCart?.onClick {
            if (isLoggedIn()) {
                launchActivity<MyCartActivity>()
            } else {
                launchActivity<SignInUpActivity>()
            }
        }
        val item = menu.findItem(R.id.action_search)
        val icon = resources.getDrawable(R.drawable.ic_search)
        icon.setColorFilter(Color.parseColor(getTextTitleColor()), PorterDuff.Mode.SRC_IN)
        item.icon = icon
        setCartCount()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                activity?.launchActivity<SearchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setCartCount() {
        val count = getCartCount()
        mMenuCart?.ivCart?.changeBackgroundImageTint(getTextTitleColor())
        mMenuCart?.tvNotificationCount?.changeTint(getTextTitleColor())
        mMenuCart?.tvNotificationCount?.text = count
        mMenuCart?.tvNotificationCount?.changeAccentColor()
        if (count.checkIsEmpty() || count == "0") {
            mMenuCart?.tvNotificationCount?.hide()
        } else {
            mMenuCart?.tvNotificationCount?.show()
        }
    }

    private fun listAllProducts() {
        if (isNetworkAvailable()) {
            showLoader()
            getRestApiImpl().getDashboardData(onApiSuccess = {
                if (activity == null) return@getDashboardData
                (activity as AppBaseActivity).showProgress(false)
                getSharedPrefInstance().apply {
                    removeKey(WHATSAPP)
                    removeKey(FACEBOOK)
                    removeKey(TWITTER)
                    removeKey(INSTAGRAM)
                    removeKey(CONTACT)
                    removeKey(PRIVACY_POLICY)
                    removeKey(TERM_CONDITION)
                    removeKey(COPYRIGHT_TEXT)
                    removeKey(LANGUAGE)
                    setValue(LANGUAGE, it.app_lang)
                    setValue(DEFAULT_CURRENCY, it.currency_symbol.currency_symbol)
                    setValue(DEFAULT_CURRENCY_FORMATE, it.currency_symbol.currency)
                    setValue(WHATSAPP, it.social_link.whatsapp)
                    setValue(FACEBOOK, it.social_link.facebook)
                    setValue(TWITTER, it.social_link.twitter)
                    setValue(INSTAGRAM, it.social_link.instagram)
                    setValue(CONTACT, it.social_link.contact)
                    setValue(PRIVACY_POLICY, it.social_link.privacy_policy)
                    setValue(TERM_CONDITION, it.social_link.term_condition)
                    setValue(COPYRIGHT_TEXT, it.social_link.copyright_text)
                    setValue(ENABLE_COUPONS, it.enable_coupons)
                    setValue(PAYMENT_METHOD, it.payment_method)
                }
                setNewLocale(it.app_lang)

                for (view in mDashboardJson.sorting!!) {
                    when (view) {
                        VIEW_BANNER -> {
                            if (it.banner.isNotEmpty()) {
                                if (mDashboardJson.sliderView!!.enable == true) {
                                    addSlider(
                                            productList = it.banner
                                    )
                                }
                            }
                        }
                        VIEW_NEWEST -> {
                            if (it.newest.isNotEmpty()) {
                                if (mDashboardJson.newProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewNewest,
                                            title = mDashboardJson.newProduct!!.title!!,
                                            mViewAll = mDashboardJson.newProduct!!.viewAll!!,
                                            code = Constants.viewAllCode.NEWEST,
                                            productList = it.newest,
                                            modelSize = 5
                                    )
                                    mLLDynamic!!.addView(mViewNewest!!)
                                }
                            }
                        }
                        VIEW_FEATURED -> {
                            if (it.featured.isNotEmpty()) {
                                if (mDashboardJson.feature!!.enable == true) {
                                    onAddView(
                                            mView = mViewFeatured,
                                            title = mDashboardJson.feature!!.title!!,
                                            mViewAll = mDashboardJson.feature!!.viewAll!!,
                                            code = Constants.viewAllCode.FEATURED,
                                            productList = it.featured,
                                            modelSize = 5
                                    )
                                    mLLDynamic!!.addView(mViewFeatured!!)
                                }
                            }
                        }
                        VIEW_DEAL_OF_THE_DAY -> {
                            if (it.deal_of_the_day.isNotEmpty()) {
                                if (mDashboardJson.dealOfTheDay!!.enable == true) {
                                    onAddView(
                                            mView = mViewDealOfTheDay,
                                            title = mDashboardJson.dealOfTheDay!!.title!!,
                                            mViewAll = mDashboardJson.dealOfTheDay!!.viewAll!!,
                                            code = Constants.viewAllCode.SPECIAL_PRODUCT,
                                            productList = it.deal_of_the_day,
                                            isGridView = true,
                                            modelSize = 2,
                                            specialKey = "deal_of_the_day"
                                    )
                                    mLLDynamic!!.addView(mViewDealOfTheDay!!)
                                }
                            }
                        }
                        VIEW_BEST_SELLING -> {
                            if (it.best_selling_product.isNotEmpty()) {
                                if (mDashboardJson.bestSaleProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewBestSelling,
                                            title = mDashboardJson.bestSaleProduct!!.title!!,
                                            mViewAll = mDashboardJson.bestSaleProduct!!.viewAll!!,
                                            code = Constants.viewAllCode.BESTSELLING,
                                            productList = it.best_selling_product,
                                            modelSize = 5
                                    )
                                    mLLDynamic!!.addView(mViewBestSelling!!)
                                }
                            }
                        }
                        VIEW_SALE -> {
                            if (it.sale_product.isNotEmpty()) {
                                if (mDashboardJson.saleProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewSale,
                                            title = mDashboardJson.saleProduct!!.title!!,
                                            mViewAll = mDashboardJson.saleProduct!!.viewAll!!,
                                            code = Constants.viewAllCode.SALE,
                                            productList = it.sale_product,
                                            modelSize = 5
                                    )
                                    mLLDynamic!!.addView(mViewSale!!)
                                }
                            }
                        }
                        VIEW_OFFER -> {
                            if (it.offer.isNotEmpty()) {
                                if (mDashboardJson.offerProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewOffer,
                                            title = mDashboardJson.offerProduct!!.title!!,
                                            mViewAll = mDashboardJson.offerProduct!!.viewAll!!,
                                            isGridView = true,
                                            code = Constants.viewAllCode.SPECIAL_PRODUCT,
                                            productList = it.offer,
                                            modelSize = 2,
                                            specialKey = "offer"
                                    )
                                    mLLDynamic!!.addView(mViewOffer!!)
                                }
                            }
                        }
                        VIEW_SUGGESTED_FOR_YOU -> {
                            if (it.suggested_for_you.isNotEmpty()) {
                                if (mDashboardJson.suggestionProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewSuggested,
                                            title = mDashboardJson.suggestionProduct!!.title!!,
                                            mViewAll = mDashboardJson.suggestionProduct!!.viewAll!!,
                                            code = Constants.viewAllCode.SPECIAL_PRODUCT,
                                            productList = it.suggested_for_you,
                                            modelSize = 5,
                                            specialKey = "suggested_for_you"
                                    )
                                    mLLDynamic!!.addView(mViewSuggested!!)
                                }
                            }
                        }
                        VIEW_YOU_MAY_LIKE -> {
                            if (it.you_may_like.isNotEmpty()) {
                                if (mDashboardJson.youMayLikeProduct!!.enable == true) {
                                    onAddView(
                                            mView = mViewYouMayLike,
                                            title = mDashboardJson.youMayLikeProduct!!.title!!,
                                            mViewAll = mDashboardJson.youMayLikeProduct!!.viewAll!!,
                                            code = Constants.viewAllCode.SPECIAL_PRODUCT,
                                            productList = it.you_may_like,
                                            modelSize = 5,
                                            specialKey = "you_may_like"
                                    )
                                    mLLDynamic!!.addView(mViewYouMayLike!!)
                                }

                            }
                        }
                    }
                }

            }, onApiError = {
                (activity as AppBaseActivity).showProgress(false)
                snackBar(it)
            })
        }
    }

    private fun setNewLocale(language: String) {
        ProShopApp.changeLanguage(language)
        lan = ProShopApp.language
        if (lan != language) {
            (activity as AppBaseActivity).recreate()
        }
    }


}