package com.ng.printtag.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.from
import android.view.MenuItem
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.databinding.HeaderToolBarBinding
import com.ng.printtag.interfaces.HeaderInterface
import androidx.databinding.DataBindingUtil.inflate as inflate1


/**
 * Header tool bar
 */
class HeaderToolBar : Toolbar, PopupMenu.OnMenuItemClickListener {

    companion object {


    }

    lateinit var binding: HeaderToolBarBinding
    private lateinit var headerInterface: HeaderInterface
    var langCode: String = ""

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private fun init(context: Context) {
        binding = DataBindingUtil.inflate(from(context), getLayoutId(), this, true)
        setContentInsetsAbsolute(0, 0)
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        binding.instance = this
    }


    /**
     * This wait for a menu item press and check which ID was pressed
     *
     * @param item MenuItem : which menu item pressed
     * @return Boolean : true/false
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {

/*
            R.id.menu_settings -> {
                PdpUtils.navigateToOtherScreen(
                    (context as Activity),
                    ActivitySetting::class.java,
                    false
                )
            }
*/

        }
        return true
    }



    /**
     * On back event.
     *
     * @param view the view
     */
    @SuppressLint
    fun onBackEvent(@Suppress("UNUSED_PARAMETER") view: View) {
        AppUtils.hideKeyBoard(context as Activity)
        if (::headerInterface.isInitialized)
            headerInterface.onLeftImageClick()
        else
            if (context != null)
                (this.context as BaseActivity<*>).onBackPressed()
    }

    @SuppressLint
    fun onBarCodeClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (::headerInterface.isInitialized)
            headerInterface.onRightImageClick()
    }

    /**
     * @see HeaderInterface
     */
    fun onHeaderMenuItemClick(view: View) {
        AppUtils.hideKeyBoard(context as Activity)
        if (::headerInterface.isInitialized) {
            headerInterface.onHeaderMenuItemClick(view)
        }
    }


    /**
     * Sets handler call back.
     *
     * @param headerInterface the handler call back
     */
    fun setHeaderInterface(headerInterface: HeaderInterface) {
        this.headerInterface = headerInterface
    }

    /**
     * @return layout genericModel id
     */
    @LayoutRes
    fun getLayoutId(): Int = R.layout.header_tool_bar
}