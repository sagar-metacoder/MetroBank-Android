package com.ng.printtag.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import com.ng.printtag.R
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentDashboardBinding
import com.ng.printtag.setting.ActivitySetting


class FragmentDashboard : BaseFragment<FragmentDashboardBinding>() {

    private lateinit var binding: FragmentDashboardBinding

    /**
     * @see BaseFragment#initFragment()
     */
    override fun initFragment() {
        binding = getFragmentDataBinding()
        handleClick()


    }

    private fun handleClick() {
        binding.ivSetting.setOnClickListener {
            startActivity(Intent(activity!!, ActivitySetting::class.java))

        }
    }

    @SuppressLint("SetTextI18n")
    fun init() {

        binding.txtCompanyName.text = BaseSharedPreference.getInstance(activity!!)
            .getPrefValue(resources.getString(R.string.company_name), "FRUIT FEEDERS")
        binding.txtBalance.text = "\u00a3" + BaseSharedPreference.getInstance(activity!!)
            .getPrefValue(
                resources.getString(R.string.pref_balance),
                "22,544.88"
            ) + " available (Inc £0.00 overdraft)"
        binding.txtCompanyAmount.text = "\u00a3" + BaseSharedPreference.getInstance(activity!!)
            .getPrefValue(resources.getString(R.string.pref_balance), "22,544.88")
        binding.txtCompanyAddress.text = BaseSharedPreference.getInstance(activity!!)
            .getPrefValue(resources.getString(R.string.account_number), "32231152") + " | " +
                BaseSharedPreference.getInstance(activity!!)
                    .getPrefValue(resources.getString(R.string.sort_code), "Business Community")

    }

    override fun onResume() {
        super.onResume()
        init()
    }

    /**
     * @see BaseFragment#getLayoutId()
     */
    override fun getLayoutId(): Int = R.layout.fragment_dashboard

}