package com.best1buy.shop.activity


import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.best1buy.shop.AppBaseActivity
import com.best1buy.shop.R
import com.best1buy.shop.fragments.ViewAllProductFragment
import com.best1buy.shop.utils.BroadcastReceiverExt
import com.best1buy.shop.utils.Constants
import com.best1buy.shop.utils.Constants.AppBroadcasts.CART_COUNT_CHANGE
import com.best1buy.shop.utils.extensions.addFragment
import com.best1buy.shop.utils.extensions.getHtmlString
import kotlinx.android.synthetic.main.toolbar.*

class ViewAllProductActivity : AppBaseActivity() {

    private var mFragment: ViewAllProductFragment? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        setToolbar(toolbar)

        BroadcastReceiverExt(this) { onAction(CART_COUNT_CHANGE) { mFragment?.setCartCount() } }

        title = intent.getStringExtra(Constants.KeyIntent.TITLE)?.getHtmlString()
        mAppBarColor()
        val mViewAllId = intent.getIntExtra(Constants.KeyIntent.VIEWALLID, 0)
        val mCategoryId = intent.getIntExtra(Constants.KeyIntent.KEYID, -1)
        val specialProduct = intent.getStringExtra(Constants.KeyIntent.SPECIAL_PRODUCT_KEY)
        mFragment = if (specialProduct != null) {
            ViewAllProductFragment.getNewInstance(
                mViewAllId,
                mCategoryId,
                specialProduct = specialProduct!!
            )
        } else {
            ViewAllProductFragment.getNewInstance(mViewAllId, mCategoryId)
        }

        addFragment(mFragment!!, R.id.fragmentContainer)
    }
}