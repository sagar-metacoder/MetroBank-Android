package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.ng.printtag.R


@Suppress("DEPRECATION")
class ProgressDialog {

    companion object {
        private var progressDialog: ProgressDialog? = null
        @SuppressLint("StaticFieldLeak")
        private var inflatedView: View? = null

        /**
         * This function is used to show/hide global progress loader dialog.
         *
         * @param context [Context] used to initialize progress loader
         * @param show    boolean flag to make dialog visible or hide
         * @param message [String] text message to be shown on progress dialog
         */
        @JvmStatic
        fun displayProgressDialog(context: Context, show: Boolean, message: String?) {
            try {
                if (show) {
                    if (progressDialog == null) {
                        progressDialog = getProgressDialog(context, message!!)
                        progressDialog?.setOnCancelListener {
                            progressDialog = null
                        }
                        try {
                            if (progressDialog != null && !(context as Activity).isFinishing) {
                                progressDialog!!.show()
                                progressDialog!!.setCancelable(true)

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else {
                        progressDialog?.setOnCancelListener {
                            progressDialog = null
                        }
                        //                    if (!((Activity) context).isFinishing()) {
                        Handler().postDelayed({
                            if (progressDialog != null && !(context as Activity).isFinishing) {
//                                progressDialog!!.setMessage(message)
                                progressDialog!!.setCancelable(true)
                                progressDialog!!.show()
                            }
                            // start time consuming background process here
                        }, 1000) // starting it in 1 second
                    }
//                    val window = progressDialog!!.window
//                    Utils.setStatusBar(window, context)

                } else {
                    if (progressDialog != null && !(context as Activity).isFinishing) {
                        progressDialog?.setOnCancelListener {
                            progressDialog = null
                        }
                        progressDialog!!.dismiss()
                        progressDialog!!.setCancelable(true)
                        progressDialog = null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        /**
         * Show progress dialog in between some kind of process which are visible to user
         *
         * @param mContext The context to initialize ProgressDialog. [Activity]
         */
        @SuppressLint("InflateParams")
        private fun getProgressDialog(mContext: Context, message: String): ProgressDialog? {
            val dialog = ProgressDialog(mContext, android.R.style.Theme_Translucent_NoTitleBar)
            try {
                dialog.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
            }
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null)
            if (message.isNotEmpty()) {
                dialog.window!!.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_app))
                (view.findViewById(R.id.li_message) as LinearLayout).visibility = View.VISIBLE
                val fullMessage = message.split("||")
                (view.findViewById(R.id.tv_title) as AppCompatTextView).text = fullMessage[0]
                (view.findViewById(R.id.tv_message) as AppCompatTextView).text = fullMessage[1]
            }
            Handler().postDelayed({
                AppUtils.animateProgressDot(view.findViewById(R.id.progress1))
            }, 0)
            Handler().postDelayed({
                AppUtils.animateProgressDot(view.findViewById(R.id.progress2))
            }, 250)
            Handler().postDelayed({
                AppUtils.animateProgressDot(view.findViewById(R.id.progress3))
            }, 500)
            dialog.setContentView(view)
            inflatedView = view
            return dialog
        }


        /*private fun getCurrentDialog(): ProgressDialog? {
            return progressDialog
        }*/
    }
}