package com.ng.printtag.printrequest

import com.ng.printtag.R
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityNewPrintRequestBinding

class ActivityNewPrintRequest : BaseActivity<ActivityNewPrintRequestBinding>() {
    private lateinit var binding: ActivityNewPrintRequestBinding
    override fun initMethod() {
        binding = getViewDataBinding()
    }

    override fun getLayoutId(): Int = R.layout.activity_new_print_request
}