package com.ng.printtag.printrequest

import android.util.Log
import com.ng.printtag.R
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import ng.pdp.base.BaseFragment

class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    private lateinit var binding: FragmentNewPrintRequestBinding
    override fun initFragment() {
        binding = getFragmentDataBinding()
        handleClick()
    }


    private fun callTagTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.title = getString(R.string.a_title_select_tag_type)

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun callDepartmentDialog() {
        val dialog = DialogDepartment()
        dialog.title = getString(R.string.a_title_select_department)

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun handleClick() {
        binding.edtTagType.setOnClickListener {
            //            callTagTypeDialog()

            callDepartmentDialog()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}