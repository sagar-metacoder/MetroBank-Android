package com.ng.printtag.base

import android.view.View
import com.ng.printtag.R
import com.ng.printtag.api.ActivityAppSessionTimeout
import com.ng.printtag.dashboard.FragmentDashboard
import com.ng.printtag.login.FragmentLogin
import com.ng.printtag.setting.ActivitySetting
import com.ng.printtag.splash.ActivitySplash


class HeaderModel {

    var title = ""
    var backIcon = R.mipmap.ic_back
    private var hamburgerIcon = R.mipmap.ic_hamburger_nav_white
    var backVisibility = false
    var settingsVisibility = false
    var barcodeVisibility = false
    var exportVisibility = false
    var printVisibility = false
    var filterVisibility = false
    var generateNewPinVisibility = false
    var logovisibility = false
    var printReceiptVisibility = false
    var closeVisibility = false
    var transactionHistoryVisibility = false
    var baseFragment: BaseFragment<*>? = null
    var baseActivity: BaseActivity<*>? = null

    fun setHeaderValues() {
        setDefaultHeader()
        when (baseFragment) {

            is FragmentLogin -> {
                baseActivity!!.setNoStatusBar()
                baseActivity!!.setHeaderVisibility(View.GONE)
                /*setLeftIcon(R.mipmap.ic_back)
                setLeftIconText(baseFragment!!.getString(R.string.a_title_sign_in))
                backVisibility = false*/
            }



            is FragmentDashboard -> {
                baseActivity!!.setHeaderVisibility(View.GONE)

            }

        }
        when (baseActivity) {
            is ActivitySplash -> {
                //  baseActivity!!.setNoStatusBar()
                baseActivity!!.setHeaderVisibility(View.GONE)
            }
            is ActivityAppSessionTimeout -> {
                backVisibility = false

            }
            is ActivitySetting -> {
                //  baseActivity!!.setNoStatusBar()
                backVisibility = true
                title = "Setting"
            }
/*
            is ActivityDashboard -> {
                // baseActivity!!.setNoStatusBar()
                baseActivity!!.setHeaderVisibility(View.GONE)
            }
*/


        }
        baseActivity!!.actBaseBinding.headerToolBar.binding.headerModel = this
        baseActivity!!.actBaseBinding.headerToolBar.binding.executePendingBindings()
    }

    private fun setDefaultHeader() {
        baseActivity!!.setHeaderVisibility(View.VISIBLE)
        baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.text = ""
        backVisibility = false
        title = ""
        settingsVisibility = true
        barcodeVisibility = false
        exportVisibility = false
        printVisibility = false
        filterVisibility = false
        generateNewPinVisibility = false
        printReceiptVisibility = false
        closeVisibility = false
        transactionHistoryVisibility = false
    }

    private fun setLeftIconText(label: String) {
        baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.text = label
    }

    private fun setLeftIcon(resId: Int) {
        baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.setCompoundDrawablesWithIntrinsicBounds(
            resId,
            0,
            0,
            0
        )
    }
}