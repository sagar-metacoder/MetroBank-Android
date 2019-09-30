package com.ng.printtag.api

import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityAppSessionTimeoutBinding
import com.ng.printtag.login.ActivityLogin


class ActivityAppSessionTimeout : BaseActivity<ActivityAppSessionTimeoutBinding>() {

    private lateinit var binding: ActivityAppSessionTimeoutBinding

    /**
     * Init components
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        binding.tvLogBackIn.setOnClickListener {
            AppUtils.navigateToOtherScreen(this, ActivityLogin::class.java, true)
        }
    }


    /**
     * Init back pressed
     */
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_app_session_timeout
}