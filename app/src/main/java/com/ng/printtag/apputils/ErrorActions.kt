package com.ng.printtag.apputils

import android.graphics.PorterDuff
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.ng.printtag.R.color.*
import com.ng.printtag.R.id
import com.ng.printtag.R.string
import com.ng.printtag.apputils.custom.CollapsedHintTextInputLayout


/**
 *  Class used for handle error action based on validation matches
 */
class ErrorActions {
    companion object {
        @JvmStatic
        @BindingAdapter(
            value = ["binding:textInputLayout", "binding:materialButton", "binding:action"], requireAll = false
        )
        fun validateLocal(
            edtText: TextInputEditText,
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton,
            action: String
        ) {
            edtText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.isNotEmpty()) {
                        when (action) {
                            edtText.context.getString(string.input_act_user) -> {
                                checkUsername(edtText, textInputLayout, materialButton)
                            }
                            edtText.context.getString(string.input_act_password) -> {
                                checkLoginPassword(edtText, textInputLayout, materialButton)
                            }

                        }
                    } else {
                        validateButton(materialButton, false)
                        textInputLayout.error = ""
                    }
                }
            })
        }


        @JvmStatic
        @BindingAdapter(
            value = ["binding:secondEdtText", "binding:materialButton", "binding:isEnable"], requireAll = false
        )
        fun validateName(
            edtText: TextInputEditText,
            secondEdtText: TextInputEditText,
            materialButton: MaterialButton,
            isEnable: Boolean
        ) {
            validateButton(materialButton, isEnable)
            edtText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.isNotEmpty()) {
                        if (edtText.id == id.edt_user_name) {
                            if (secondEdtText.text.toString().isNotEmpty())
                                validateButton(materialButton, true)
                        } else {
                            if (secondEdtText.text.toString().isNotEmpty())
                                validateButton(materialButton, true)
                        }

                    } else {
                        validateButton(materialButton, false)
                    }
                }
            })
        }

        private fun checkLoginPassword(
            edtText: TextInputEditText,
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton
        ) {
            if (edtText.text.toString().length <= 1) {
                validateButton(materialButton, false)
            } else {
                validateButton(materialButton, true)
            }
            textInputLayout.error = null
        }

        private fun checkUsername(
            edtText: TextInputEditText,
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton
        ) {
            if (edtText.text.toString().length <= 1) {
                validateButton(materialButton, false)
            } else {
                validateButton(materialButton, true)
            }
            textInputLayout.error = null
        }

        @JvmStatic
        fun validateServer(
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton,
            serverMessage: String?
        ) {
            textInputLayout.error =
                serverMessage
            validateButton(materialButton, false)

        }

        fun validateButton(materialButton: MaterialButton, isEnable: Boolean) {
            materialButton.isEnabled = isEnable
            when (isEnable) {
                true -> {
                    val color = ResourcesCompat.getColor(materialButton.context.resources, colorWhite, null)

                    materialButton.background.setColorFilter(
                        color,
                        PorterDuff.Mode.MULTIPLY
                    )
                    materialButton.setTextColor(
                        ContextCompat.getColorStateList(
                            materialButton.context,
                            color_4A4A4A
                        )
                    )


                }
                false -> {
                    val color = ResourcesCompat.getColor(materialButton.context.resources, colorWhite50, null)
                    materialButton.background.setColorFilter(
                        color,
                        PorterDuff.Mode.MULTIPLY
                    )
                    materialButton.setTextColor(
                        ContextCompat.getColorStateList(
                            materialButton.context,
                            color_4A4A4A
                        )
                    )

                }
            }
        }

        fun validateButton_dialog(materialButton: MaterialButton, isEnable: Boolean) {
            materialButton.isEnabled = isEnable
            when (isEnable) {
                true -> {
                    val color = ResourcesCompat.getColor(materialButton.context.resources, colorPrimary, null)

                    materialButton.background.setColorFilter(
                        color,
                        PorterDuff.Mode.MULTIPLY
                    )
                    materialButton.setTextColor(
                        ContextCompat.getColorStateList(
                            materialButton.context,
                            colorWhite
                        )
                    )


                }
                false -> {
                    val color = ResourcesCompat.getColor(materialButton.context.resources, color_CDE0EC, null)
                    materialButton.background.setColorFilter(
                        color,
                        PorterDuff.Mode.MULTIPLY
                    )
                    materialButton.setTextColor(
                        ContextCompat.getColorStateList(
                            materialButton.context,
                            colorWhite
                        )
                    )

                }
            }
        }
    }
}