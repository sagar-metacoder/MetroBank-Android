package com.ng.printtag.splash

import android.content.Intent
import android.os.Handler
import android.view.View
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivitySplashBinding
import com.ng.printtag.login.ActivityLogin

class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    private lateinit var binding: ActivitySplashBinding

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initMethod() {
        AppUtils.setStatusBar(window, this)
        AppUtils.noStatusBar(window)
        binding = getViewDataBinding()

        Handler().postDelayed({
            Intent(this@ActivitySplash, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }


}