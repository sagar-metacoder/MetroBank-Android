package com.ng.printtag.apputils.custom

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.ng.printtag.R
import java.lang.reflect.Method

/** This is */
class CollapsedHintTextInputLayout : TextInputLayout {

    private lateinit var collapseHintMethod: Method

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    internal fun init() {
        isHintAnimationEnabled = false

        try {
            collapseHintMethod =
                TextInputLayout::class.java.getDeclaredMethod("collapseHint", Boolean::class.javaPrimitiveType)
            collapseHintMethod.isAccessible = true
            setErrorTextAppearance(R.style.ErrorLogInText)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    override fun invalidate() {
        super.invalidate()
        try {
            collapseHintMethod.invoke(this, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}