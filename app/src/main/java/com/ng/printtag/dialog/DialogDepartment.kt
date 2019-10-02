package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.databinding.DialogDepartmentBinding
import ng.pdp.base.BaseDialog

class DialogDepartment : BaseDialog<DialogDepartmentBinding>() {
    lateinit var binding: DialogDepartmentBinding
    val adapter = DepartmentDialogAdapter()

    override fun initDialog() {
        binding = getDialogDataBinding()
        binding.tvTitle.text = title

        val stringList: ArrayList<String> = ArrayList()
        stringList.add("Fresh Tag")
        stringList.add("Inventory Tag")
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