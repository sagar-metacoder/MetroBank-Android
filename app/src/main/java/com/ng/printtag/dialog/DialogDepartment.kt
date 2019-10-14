package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.apputils.ErrorActions
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogDepartmentBinding
import com.ng.printtag.interfaces.OnItemClickListener

class DialogDepartment : BaseDialog<DialogDepartmentBinding>(), OnItemClickListener {


    lateinit var binding: DialogDepartmentBinding
    var stringList: ArrayList<String> = ArrayList()
    val adapter = DepartmentDialogAdapter()

    override fun initDialog() {
        binding = getDialogDataBinding()
        binding.tvTitle.text = title

        adapter.setData(activity!!, stringList, this)
        binding.rvList.adapter = adapter
        handleClick()
        ErrorActions.validateButton_dialog(binding.btnDone, false)


    }

    private fun handleClick() {
        binding.btnDone.setOnClickListener {
            dismiss()

            callBackListener!!.onCallBack(adapter.selectedDeptList, "")
        }
    }

    override fun onItemClick(item: Any, position: Int) {
        if (callBackListener != null) {
            if (!(item as String).isEmpty()) {
                ErrorActions.validateButton_dialog(binding.btnDone, true)

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_department
}