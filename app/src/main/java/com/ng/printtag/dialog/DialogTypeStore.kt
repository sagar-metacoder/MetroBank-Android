package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.apputils.Constant
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogTypeStoreBinding

class DialogTypeStore : BaseDialog<DialogTypeStoreBinding>() {
    lateinit var binding: DialogTypeStoreBinding
    var stringList: ArrayList<String> = ArrayList()
    val adapter = TypeStoreDialogAdapter()
    override fun initDialog() {
        binding = getDialogDataBinding()
        if(fromWhere == Constant.TAG_TYPE) {
            binding.tvTitle.text = getString(R.string.a_title_select_tag_type)
        }else if(fromWhere == Constant.TAG_STORE)
        {
            binding.tvTitle.text = getString(R.string.a_title_select_store_no)

        }

        adapter.setData(activity!!, stringList)
        binding.rvList.adapter = adapter
        handleClick()

    }

    private fun handleClick() {
        binding.btnDone.setOnClickListener {
            dismiss()
            if(fromWhere == Constant.TAG_TYPE) {
                callBackListener!!.onCallBack(adapter.selPosValue,  Constant.TAG_TYPE)
            }
            else
            {
                callBackListener!!.onCallBack(adapter.selPosValue, Constant.TAG_STORE)

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_type_store
}