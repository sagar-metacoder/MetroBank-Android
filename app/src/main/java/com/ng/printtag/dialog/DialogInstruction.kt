package com.ng.printtag.dialog

import android.view.View.GONE
import android.view.View.VISIBLE
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.Utils
import com.ng.printtag.dashboard.ActivityDashboard
import com.ng.printtag.databinding.DialogInstructionBinding
import com.ng.printtag.interfaces.OnItemClickListener
import ng.pdp.base.BaseDialog

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
                    getString(R.string.action_menu_logout) ->
                        Utils.gotoLoginScreen(activity!!)

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