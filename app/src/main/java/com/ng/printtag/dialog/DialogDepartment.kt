package com.ng.printtag.dialog

import android.util.Log
import com.ng.printtag.R
import com.ng.printtag.apputils.ErrorActions
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogDepartmentBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.StoreDepartmentListModel

class DialogDepartment : BaseDialog<DialogDepartmentBinding>(), OnItemClickListener {


    lateinit var binding: DialogDepartmentBinding
    var stringList: ArrayList<StoreDepartmentListModel> = ArrayList()
    lateinit var deptPosition: ArrayList<Int>

    val adapter = DepartmentDialogAdapter()

    override fun initDialog() {
        binding = getDialogDataBinding()
        binding.tvTitle.text = title
        Log.e("position", deptPosition.toString())

        adapter.setData(activity!!, stringList, deptPosition, this)
        binding.rvList.adapter = adapter
        handleClick()
        if (deptPosition.isNullOrEmpty()) {
            ErrorActions.validateButton_dialog(binding.btnDone, false)
        } else {
            ErrorActions.validateButton_dialog(binding.btnDone, true)

        }


    }

    private fun handleClick() {
        binding.btnDone.setOnClickListener {
            dismiss()

            callBackListener!!.onCallBack(adapter.selectedDeptList, "")
        }
    }

    override fun onItemClick(item: Any, position: Int) {
        if (callBackListener != null) {
            if (!(item as ArrayList<Int>).isNullOrEmpty()) {
                ErrorActions.validateButton_dialog(binding.btnDone, true)

            } else {
                ErrorActions.validateButton_dialog(binding.btnDone, false)

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_department
}