package com.ng.printtag.printrequest

import com.ng.printtag.R
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import ng.pdp.base.BaseFragment

class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    private lateinit var binding: FragmentNewPrintRequestBinding
    override fun initFragment() {
        binding = getFragmentDataBinding()

    }

    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}