@file:Suppress("DEPRECATION")

package com.ng.printtag.layout

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ng.printtag.R
import com.ng.printtag.apputils.*
import com.ng.printtag.dashboard.ActivityDashboard
import com.ng.printtag.databinding.ManageSlideMenuBinding
import com.ng.printtag.interfaces.CallBackInterfaces


/**
 * LayoutSlideMenu is common layout class to display slide menu[left drawer]
 */
class LayoutSlideMenu : LinearLayout {

    lateinit var binding: ManageSlideMenuBinding
    var langCode: String = ""
    private lateinit var callBackInterfaces: CallBackInterfaces

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
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
        if (AppUtils.getUserModel(context).data != null) {
            if (!AppUtils.getUserModel(context).data!!.username.isNullOrEmpty())
                binding.tvUsername.text =
                    AppUtils.getUserModel(context).data!!.username
            if (!AppUtils.getUserModel(context).data!!.firstName.isNullOrEmpty())
                binding.tvStorename.text =
                    AppUtils.getUserModel(context).data!!.getFullProfileName()

        }

        if (BaseSharedPreference.getInstance(context).getLanguage(
                context.getString(R.string.pref_language)
            ).equals("es")
        ) {

            binding.tvSpanish.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.tvSpanish.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_menu_language_select, 0);
        } else {
            binding.tvLanguage.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.tvLanguage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_menu_language_select, 0);


        }
        /* binding.tvAssociateName.text =
             BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.pref_user_name))!!

        *//* binding.tvAssociateStore.text = Utils.getDynamicLabel(
            context.getString(R.string.a_menu_store_associate),
            BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.pref_store))!!
        )*/
        setLang()
    }

    /**
     * This method is used for language set for the app
     */
    private fun setLang() {
        langCode = BaseSharedPreference.getInstance(context).getLanguage(context.getString(R.string.pref_language))
        /* if (langCode == context.getString(R.string.language_en)) {
             binding.tvLanguage.text = context.getString(R.string.a_app_menu_spanish)
         } else {
             binding.tvLanguage.text = context.getString(R.string.a_app_menu_english)
         }*/
    }

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
        } else if (tag == context.getString(R.string.action_menu_lan_spanish)) {
            Utils.setLocalForTheApp(context as Activity, "es")


            BaseSharedPreference.getInstance(context).putValue(
                context.getString(R.string.pref_language), "es"
            )
            callAppTextApi()

        } else if (tag == context.getString(R.string.action_menu_lan_english)) {
            Utils.setLocalForTheApp(context as Activity, "en")

            BaseSharedPreference.getInstance(context).putValue(
                context.getString(R.string.pref_language), "en"
            )
            callAppTextApi()

            /* langCode = if (langCode == context.getString(R.string.language_es)) {
                 context.getString(R.string.language_en)
             } else {
                 context.getString(R.string.language_es)
             }*/
        }
        if (::callBackInterfaces.isInitialized)
            callBackInterfaces.onCallBack(title, tag)

    }

    /**
     * Call language text api
     */
    private fun callAppTextApi() {

        AppUtils.navigateToOtherScreen(context as Activity, ActivityDashboard::class.java, true)


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