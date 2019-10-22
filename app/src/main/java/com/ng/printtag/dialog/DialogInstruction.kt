package com.ng.printtag.dialog

import android.view.View.GONE
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.Utils
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.databinding.DialogInstructionBinding
import com.ng.printtag.interfaces.OnItemClickListener

class DialogInstruction : BaseDialog<DialogInstructionBinding>(), OnItemClickListener {

    private lateinit var binding: DialogInstructionBinding
    var dialogTitle: String = ""
    var dialogMessage: String = ""
    var okButton = ""
    var cancelButton = ""
    var type: String = ""
    var isIconVisible: Boolean = false

    override fun initDialog() {
        binding = getDialogDataBinding()
        setData()
    }

    private fun setData() {
        binding.tvTitle.text = dialogTitle
        binding.tvMessage.text = dialogMessage
        if (okButton.isNotEmpty())
            binding.btnOk.text = okButton
        else
            binding.btnOk.visibility = GONE
        if (cancelButton.isNotEmpty())
            binding.btnCancel.text = cancelButton
        else
            binding.btnCancel.visibility = GONE

        binding.btnOk.setOnClickListener {
            dismiss()
            if (callBackListener != null) {
                callBackListener!!.onCallBack("", "ok")
            } else {
                when (type) {
                    getString(R.string.action_menu_logout) -> {
                        AppUtils.showLongToast(activity!!, activity!!.resources.getString(R.string.a_msg_loggedout))
                        Utils.gotoLoginScreen(activity!!)
                    }
                    getString(R.string.action_sign_out) -> {
                        BaseSharedPreference.getInstance(activity!!)
                            .putValue(getString(R.string.pref_session_out), false)
                        AppUtils.finishActivity(activity!!)
                    }
                    getString(R.string.action_delete) -> {
                        dismiss()
                    }
                    /* getString(R.string.action_sign_out) -> {
                        BaseSharedPreference.getInstance(activity!!)
                            .putValue(getString(R.string.pref_session_out), false)
                        AppUtils.finishActivity(activity!!)

                    }
                    getString(R.string.action_home) -> {
                        AppUtils.navigateToOtherScreen(
                            activity!!,
                            ActivityDashboard::class.java,
                            true
                        )
                    }*/
                    }

            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
            if (callBackListener != null) {
                callBackListener!!.onCallBack("", "cancel")
            }
        }

    }

    override fun getLayoutId(): Int = R.layout.dialog_instruction

    override fun onItemClick(item: Any, position: Int) {
    }
}