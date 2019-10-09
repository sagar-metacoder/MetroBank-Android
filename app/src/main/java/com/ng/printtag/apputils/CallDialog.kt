package com.ng.printtag.apputils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ng.printtag.base.BaseDialog
import com.ng.printtag.dialog.DialogInstruction
import com.ng.printtag.interfaces.CallBackInterfaces


/**
 * This class is used for open dialog with message and button(s)
 */
class CallDialog {
    companion object {
        fun openDialog(context: Context, dialogFragment: BaseDialog<*>) {
            try {
                val ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                dialogFragment.show(ft, "dialog")
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }


        fun errorDialog(
            context: Context,
            title: String,
            message: String,
            okButton: String,
            cancelButton: String,
            type:String,
            onClickListener: CallBackInterfaces?
        ) {
            val dialog = DialogInstruction()
            dialog.dialogTitle = title
            dialog.type=type
            dialog.dialogMessage = message
            dialog.okButton = okButton
            dialog.cancelButton = cancelButton
            dialog.callBackListener = onClickListener
            openDialog(context, dialog)
        }

    }

}