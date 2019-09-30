package com.ng.printtag.interfaces

import android.view.View

/**
 * Sets handler call back on toolbar.
 */
interface HeaderInterface {

    /**
     * Right image click action on toolbar
     */
    fun onRightImageClick()

    /**
     * Left image click action on toolbar
     */
    fun onLeftImageClick()

    /**
     * Menu item click action based on name
     */
    fun onMenuImageClick(item: String)

    /**
     * menu item click action based on view id on toolbar
     */
    fun onHeaderMenuItemClick(view: View)
    /*fun onExportClick()
    fun onPrintClick()
    fun onFilterClick()
    fun onPrintReceiptClick()
    fun onCloseClick()
    fun onGenerateNewPinClick()*/
}