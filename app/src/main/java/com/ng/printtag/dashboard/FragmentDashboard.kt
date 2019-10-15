package com.ng.printtag.dashboard

import android.content.Intent
import com.ng.printtag.R
import com.ng.printtag.allrequest.ActivityAllRequests
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentDashboardBinding
import com.ng.printtag.printrequest.ActivityNewPrintRequest


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
        binding.consNewRequest.setOnClickListener {
            AppUtils.navigateToOtherScreen(activity!!, ActivityNewPrintRequest::class.java, false)

        }

        binding.consAllRequest.setOnClickListener {
            val intent = Intent(activity!!, ActivityAllRequests::class.java)
            intent.putExtra(getString(R.string.action_from), getString(R.string.action_from_all))
            AppUtils.navigateToOtherScreen(activity!!, intent, false)
        }

        binding.consPendingRequest.setOnClickListener {
            val intent = Intent(activity!!, ActivityAllRequests::class.java)
            intent.putExtra(getString(R.string.action_from), getString(R.string.action_from_pending))
            AppUtils.navigateToOtherScreen(activity!!, intent, false)
        }
    }


    /**
     * @see BaseFragment#getLayoutId()
     */
    override fun getLayoutId(): Int = R.layout.fragment_dashboard
}