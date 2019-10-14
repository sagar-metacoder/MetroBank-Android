package com.ng.printtag.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils.Companion.setStatusBar
import com.ng.printtag.interfaces.CallBackInterfaces


abstract class BaseDialog<out T : ViewDataBinding> : DialogFragment() {
    private lateinit var dialogChildBinding: T
    var title: String? = null

    var callBackListener: CallBackInterfaces? = null
    var fromWhere: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.StyleBaseDialog)
    }

    /**
     * This method used for set dialog height and width
     */
    private fun setMatchParent() {
        val dialog = dialog
        dialog?.setCanceledOnTouchOutside(false)
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            val window = getDialog()?.window
            setStatusBar(window!!, activity!!)
        }
    }

    /**
     * This method is used for setup dialog with style
     */
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialogChildBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity!!),
            getLayoutId(), null, false
        )
        val contentView = dialogChildBinding.root
        dialog.setContentView(contentView)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        (contentView.parent as View).setBackgroundColor(
            ContextCompat.getColor(
                activity as AppCompatActivity,
                android.R.color.transparent
            )
        )
//        }
    }


    /**
     * Bind the UI
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogChildBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(), null, false
        )

        return dialogChildBinding.root
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved state has been
     * restored in to the view. This gives subclasses a chance to initialize themselves once they know their view hierarchy has been
     * completely created.The fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view               -View: The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState -Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
    }


    /**
     * This method will override in all fragment to init the UI
     */
    protected abstract fun initDialog()

    /**
     * Get Binding Object
     */

    fun getDialogDataBinding(): T = dialogChildBinding

    /**
     * @return layout genericModel id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int


}