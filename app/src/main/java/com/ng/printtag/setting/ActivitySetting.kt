@file:Suppress("DEPRECATION")

package com.ng.printtag.setting

import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivitySettingBinding
import java.text.NumberFormat
import java.util.*


class ActivitySetting : BaseActivity<ActivitySettingBinding>() {

    private lateinit var binding: ActivitySettingBinding

    /**
     * Init components
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        binding.edtPin.requestFocus()


        handleClick()
    }

    private fun handleClick() {
        binding.btnSave.setOnClickListener {

            val locale: Locale = Locale.ENGLISH
            val nf: NumberFormat = NumberFormat.getNumberInstance(locale)

            nf.minimumFractionDigits = 2
            nf.maximumFractionDigits = 2

            if (!binding.edtCompanyname.text.isNullOrEmpty())
                BaseSharedPreference.getInstance(this@ActivitySetting).putValue(
                    resources.getString(R.string.company_name)
                    , binding.edtCompanyname.text.toString()
                )
            if (!binding.edtAccountNumber.text.isNullOrEmpty())
                BaseSharedPreference.getInstance(this@ActivitySetting).putValue(
                    resources.getString(R.string.account_number)
                    , binding.edtAccountNumber.text.toString()
                )
            if (!binding.edtPin.text.isNullOrEmpty())
                BaseSharedPreference.getInstance(this@ActivitySetting).putValue(
                    resources.getString(R.string.pref_pin)
                    , binding.edtPin.text.toString()
                )
            if (!binding.edtSortCode.text.isNullOrEmpty())
                BaseSharedPreference.getInstance(this@ActivitySetting).putValue(
                    resources.getString(R.string.sort_code)
                    , binding.edtSortCode.text.toString()
                )
            if (!binding.edtBalance.text.isNullOrEmpty())
                BaseSharedPreference.getInstance(this@ActivitySetting).putValue(
                    resources.getString(R.string.pref_balance)
                    , (nf.format(binding.edtBalance.text.toString().toDouble()).toString())
                )

            finish()

        }
    }

    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun onStop() {
        super.onStop()
        AppUtils.hideKeyBoard(this@ActivitySetting)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppUtils.hideKeyBoard(this@ActivitySetting)

    }
}