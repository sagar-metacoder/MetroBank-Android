package com.ng.printtag.dashboard

import com.ng.printtag.R
import com.ng.printtag.databinding.FragmentDashboardBinding
import ng.pdp.base.BaseFragment


class FragmentDashboard : BaseFragment<FragmentDashboardBinding>() {

    private lateinit var binding: FragmentDashboardBinding

    /**
     * @see BaseFragment#initFragment()
     */
    override fun initFragment() {
        binding = getFragmentDataBinding()
        handleClick()
    }

    /**
     * Upon click on tvSearchClick FragmentLookUpPhoneLandLine will be open
     * Upon click on ivWellCome ActivityCheckWaiver will be open
     */
    private fun handleClick() {

        binding.consAllRequest.setOnClickListener({


        })
        /* binding.tvSearchClick.setOnClickListener { view ->
             super.onClick(view)
             ALREADY_EXIST_USER = 0
             CHECK_ALREADY_EXIST_USER = false
             (activity as ActivityDashboard).addressModels = null
             (activity as ActivityDashboard).countryList = null
             Utils.navigateTo(binding.tvSearchClick, R.id.actionLookUpPhoneLandLine, null)
         }
         binding.ivWellCome.setOnClickListener {
             //            PdpUtils.navigateToOtherScreen(activity!!, ActivityUnderWritingRules::class.java, false)
         }*/
    }


    /**
     * @see BaseFragment#getLayoutId()
     */
    override fun getLayoutId(): Int = R.layout.fragment_dashboard
}