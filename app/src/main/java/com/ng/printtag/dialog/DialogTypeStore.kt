package com.ng.printtag.dialog

import com.ng.printtag.R
import com.ng.printtag.apputils.Constant
import com.ng.printtag.apputils.ErrorActions
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogTypeStoreBinding
import com.ng.printtag.interfaces.OnItemClickListener

class DialogTypeStore : BaseDialog<DialogTypeStoreBinding>(), OnItemClickListener {

    lateinit var binding: DialogTypeStoreBinding
    lateinit var stringList: ArrayList<String>
    var selectedTag: Int = -1
    val adapter = TypeStoreDialogAdapter()
    override fun initDialog() {
        binding = getDialogDataBinding()
        if(fromWhere == Constant.TAG_TYPE) {
            binding.tvTitle.text = getString(R.string.a_title_select_tag_type)
            adapter.setData(activity!!, stringList, Constant.TAG_TYPE, selectedTag, this)

        } else if (fromWhere == Constant.TAG_STORE) {
            binding.tvTitle.text = getString(R.string.a_title_select_store_no)
            adapter.setData(activity!!, stringList, Constant.TAG_STORE, selectedTag, this)


        }

        if (selectedTag == -1) {
            ErrorActions.validateButton_dialog(binding.btnDone, false)

        } else {
            ErrorActions.validateButton_dialog(binding.btnDone, true)

        }




        binding.rvList.adapter = adapter
        handleClick()

    }

    private fun handleClick() {
        binding.btnDone.setOnClickListener {
            dismiss()
            if (fromWhere == Constant.TAG_TYPE) {
                callBackListener!!.onCallBack(adapter.selPosValue, Constant.TAG_TYPE)
            } else {
                callBackListener!!.onCallBack(adapter.selPosValue, Constant.TAG_STORE)

            }
        }
    }

    override fun onItemClick(item: Any, position: Int) {
        if (callBackListener != null) {
            ErrorActions.validateButton_dialog(binding.btnDone, true)


        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_type_store
}