package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.databinding.DialogTypeStoreBinding
import ng.pdp.base.BaseDialog

class DialogTypeStore : BaseDialog<DialogTypeStoreBinding>() {
    lateinit var binding: DialogTypeStoreBinding
    val adapter = TypeStoreDialogAdapter()
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
            callBackListener!!.onCallBack(adapter.selPosValue, "")
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_type_store
}