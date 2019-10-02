package com.ng.printtag.base

import android.view.View
import android.widget.RelativeLayout
import com.ng.printtag.R
import com.ng.printtag.dashboard.FragmentDashboard
import com.ng.printtag.login.FragmentPassword
import com.ng.printtag.login.FragmentUsername
import com.ng.printtag.splash.ActivitySplash
import ng.pdp.base.BaseFragment
import kotlin.contracts.contract


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
    var checkVisibility = false
    var generateNewPinVisibility = false
    var printReceiptVisibility = false
    var closeVisibility = false
    var transactionHistoryVisibility = false
    var helpVisibility = false
    var baseFragment: BaseFragment<*>? = null
    var baseActivity: BaseActivity<*>? = null

    fun setHeaderValues() {
        setDefaultHeader()
        when (baseFragment) {

            is FragmentUsername, is FragmentPassword -> {
                setLeftIcon(R.mipmap.ic_back)
                setLeftIconText(baseFragment!!.getString(R.string.a_title_sign_in))
                backVisibility = true
            }
            is FragmentDashboard -> {
                title =
                   baseFragment!!.getString(R.string.a_title_dashboard)

                setLeftIcon(hamburgerIcon)
                val param = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                param.addRule(
                    RelativeLayout.RIGHT_OF,
                    baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.id
                )
                param.addRule(RelativeLayout.CENTER_VERTICAL)
                param.setMargins(baseActivity!!.resources.getDimension(R.dimen.margin_5).toInt(), 0, 0, 0)
                baseActivity!!.actBaseBinding.headerToolBar.binding.tvHeaderTitle.layoutParams = param
            }
           /* is FragmentUsername, is FragmentPassword, is FragmentStoreNumber, is FragmentLookUpByName, is FragmentLookUpResult, is FragmentLookUpByProspera -> {
                setLeftIcon(backIcon)
                baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.text = ""
            }*/
/*
            is FragmentIdTypeSelection, is FragmentIdPreview, is FragmentPhotoTutorial -> {
                baseActivity!!.setStatusBar()
            }
*/

/*
            is FragmentExeVerifyCheck -> {
                setLeftIcon(R.mipmap.ic_back)
                setLeftIconText(Utils.getLabel(baseActivity!!.getString(R.string.a_title_check_verfication)))
                backVisibility = true
                closeVisibility = true
                helpVisibility = true
                settingsVisibility = false
            }
*/


        }
        when (baseActivity) {
            is ActivitySplash -> {
                baseActivity!!.setNoStatusBar()
                baseActivity!!.setHeaderVisibility(View.GONE)
            }
           /* is ActivityCheckTutorial -> {
                setLeftIconText(Utils.getLabel(baseActivity!!.getString(R.string.a_lbl_check_cashing)))
                backVisibility = true
                closeVisibility = true
                settingsVisibility = false
            }
            is ActivityUploadedPhotoPreview -> {
                backVisibility = true
                closeVisibility = true
                settingsVisibility = false
                helpVisibility = true
                setLeftIconText(Utils.getLabel(baseActivity!!.getString(R.string.a_lbl_edit_customer_photo)))
                baseActivity!!.setStatusBar()
            }*/


        }
        baseActivity!!.actBaseBinding.headerToolBar.binding.headerModel = this
        baseActivity!!.actBaseBinding.headerToolBar.binding.executePendingBindings()
    }

    private fun setDefaultHeader() {
        baseActivity!!.setHeaderVisibility(View.VISIBLE)
        baseActivity!!.actBaseBinding.headerToolBar.binding.ivBack.text = ""
        backVisibility = true
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