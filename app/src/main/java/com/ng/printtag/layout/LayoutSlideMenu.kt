@file:Suppress("DEPRECATION")

package com.ng.printtag.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.BindingMethods
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.databinding.ManageSlideMenuBinding
import com.ng.printtag.interfaces.CallBackInterfaces


/**
 * LayoutSlideMenu is common layout class to display slide menu[left drawer]
 */
class LayoutSlideMenu : LinearLayout {

    lateinit var binding: ManageSlideMenuBinding
    var langCode: String = ""
    private lateinit var callBackInterfaces: CallBackInterfaces
    val companyListId = ArrayList<String>()
    val companyList = ArrayList<String>()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    /**
     * Initialization layout
     */
    private fun init(context: Context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, true)
        binding.instance = this
        ContextCompat.getDrawable(context, R.mipmap.ic_user)?.let {
            BindingMethods.setCircleImage(
                binding.imageView, "",
                it
            )
        }
        if (AppUtils.getUserModel(context).result != null) {
            if (!AppUtils.getUserModel(context).result!!.name.isNullOrEmpty())
                binding.tvUsername.text = AppUtils.getUserModel(context).result!!.name

            //if(AppUtils.getUserModel(context).result!!.userCompanies!! !is Boolean) {

            if (!AppUtils.getUserModel(context).result!!.userCompanies!!.allowedCompanies.isNullOrEmpty()) {
                for (i in 0 until (AppUtils.getUserModel(context).result!!.userCompanies!!.allowedCompanies!!.size)) {
                    companyListId.add(AppUtils.getUserModel(context).result!!.userCompanies!!.allowedCompanies!![i][0].toString())
                    companyList.add(AppUtils.getUserModel(context).result!!.userCompanies!!.allowedCompanies!![i][1].toString())

                }

                for (j in 0 until companyList.size) {
                    if (BaseSharedPreference.getInstance(context).getPrefValue(
                            context.getString(R.string.pref_company),
                            ""
                        ) == companyListId[j]
                    )

                        binding.tvStorename.text = companyList[j]

                }
            }
            // }


        }
    }

    inline fun <reified T> cast(anything: Any): T? {
        return anything as? T
    }

/*
    inline  fun <reified T: Any> Any.cast(): T{
        return this as T
    }
*/


    /**
     * This wait for a menu item press and check which ID was pressed
     *
     * @param title String : title of item
     * @param tag String : tag of item
     */
    fun onMenuItemClicks(title: String, tag: String) {
        if (tag == context.getString(R.string.action_menu_logout)) {
            callBackInterfaces.onCallBack(title, tag)


            CallDialog.errorDialog(
                context,
                context.getString(R.string.a_menu_logout),
                context.getString(R.string.a_lbl_sign_out_message),
                context.getString(R.string.a_btn_yes),
                context.getString(R.string.a_btn_no),
                context.getString(R.string.action_menu_logout),
                null
            )
        }
        if (::callBackInterfaces.isInitialized)
            callBackInterfaces.onCallBack(title, tag)

    }

    /**
     * Sets handler call back.
     *
     * @param callBackInterfaces the handler call back
     */
    fun setCallBackInterfaces(callBackInterfaces: CallBackInterfaces) {
        this.callBackInterfaces = callBackInterfaces
    }

    /**
     * @return layout genericModel id
     */
    @LayoutRes
    fun getLayoutId(): Int = R.layout.manage_slide_menu
}