package com.ng.printtag.printrequest

import com.ng.printtag.R
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityUpcBinding

class ActivityUpc : BaseActivity<ActivityUpcBinding>() {
    lateinit var binding: ActivityUpcBinding
    override fun initMethod() {
        binding = getViewDataBinding()
    }

    override fun getLayoutId(): Int = R.layout.activity_upc
}