package com.ng.printtag.splash

import android.os.Handler
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.Utils
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivitySplashBinding

class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    private lateinit var binding: ActivitySplashBinding

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initMethod() {

        AppUtils.noStatusBar(window)
        binding = getViewDataBinding()


        Handler().postDelayed({
            Utils.gotoLoginScreen(this@ActivitySplash)

        }, 1500)
    }


}