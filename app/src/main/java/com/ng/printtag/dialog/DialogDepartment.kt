package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogDepartmentBinding

class DialogDepartment : BaseDialog<DialogDepartmentBinding>() {
    lateinit var binding: DialogDepartmentBinding
    var stringList: ArrayList<String> = ArrayList()
    val adapter = DepartmentDialogAdapter()

    override fun initDialog() {
        binding = getDialogDataBinding()
        binding.tvTitle.text = title

        adapter.setData(activity!!, stringList)
        binding.rvList.adapter = adapter
        handleClick()


    }

    private fun handleClick() {
        binding.btnDone.setOnClickListener {
            dismiss()


            callBackListener!!.onCallBack(adapter.selectedDeptList, "")
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_department
}