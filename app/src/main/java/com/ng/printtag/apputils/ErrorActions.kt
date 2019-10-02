package com.ng.printtag.apputils

import android.graphics.PorterDuff
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.INVISIBLE
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.ng.printtag.R.color.*
import com.ng.printtag.R.string
import com.ng.printtag.apputils.custom.CollapsedHintTextInputLayout
import java.util.regex.Pattern


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

        private fun checkLoginPassword(
            edtText: TextInputEditText,
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton
        ) {
            if (edtText.text.toString().length < 4) {
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
            if (edtText.text.toString().length < 4) {
                validateButton(materialButton, false)
            } else {
                validateButton(materialButton, true)
            }
            textInputLayout.error = null
        }



        fun checkEmail(
            edtText: TextInputEditText,
            textInputLayout: CollapsedHintTextInputLayout,
            materialButton: MaterialButton
        ) {
            if (!Pattern.matches(Constant.emailPattern, edtText.text.toString())) {
                validateButton(materialButton, false)
            } else {
                validateButton(materialButton, true)
            }
            textInputLayout.error = null

        }




        private fun validateIssuerOrIdNo(
            edtText: TextInputEditText,
            materialButton: MaterialButton
        ) = if (edtText.text.toString().isEmpty()) {
            validateButton(materialButton, false)
        } else {
            validateButton(materialButton, true)
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


        @JvmStatic
        @BindingAdapter(
            value = ["binding:lookupButton", "binding:isEnable"], requireAll = false
        )
        fun validateLookup(
            inputEditText: TextInputEditText,
            lookupButton: MaterialButton,
            isEnable: Boolean
        ) {
            validateButton(lookupButton, isEnable)
            inputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun afterTextChanged(text: Editable?) {
                    if (text!!.trim().isNotEmpty()) {
                        validateButton(lookupButton, true)
                    } else {
                        validateButton(lookupButton, false)
                    }
                }
            })
        }

        fun validateButton(materialButton: MaterialButton, isEnable: Boolean) {
            materialButton.isEnabled = isEnable
            when (isEnable) {
                true -> {

                    materialButton.background.setColorFilter(
                        materialButton.context.resources.getColor(colorWhite),
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
                    materialButton.background.setColorFilter(
                        materialButton.context.resources.getColor(color_1AFF007F),
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


        fun setPhoneErrorInvisible(
            materialButton: MaterialButton,
            view: View?,
            tvHint: AppCompatTextView?,
            errorTextView: AppCompatTextView?
        ) {
            view?.setBackgroundColor(ContextCompat.getColor(view.context, colorWhite))
            errorTextView?.visibility = INVISIBLE
            tvHint?.setTextColor(ContextCompat.getColor(tvHint.context, colorWhite))
        }

        fun setPhoneErrorInvisible(
            materialButton: MaterialButton,
            errorTextView: AppCompatTextView?
        ) {
            errorTextView?.visibility = INVISIBLE
        }

        fun setPhoneErrorVisible(
            message: String, materialButton: MaterialButton, view: View?,
            tvHint: AppCompatTextView?,
            errorTextView: AppCompatTextView?
        ) {
            errorTextView!!.text = message
            view!!.setBackgroundColor(ContextCompat.getColor(view.context!!, color_FA70B4))
            errorTextView.visibility = View.VISIBLE
            tvHint!!.setTextColor(ContextCompat.getColor(tvHint.context!!, color_FA70B4))
            validateButton(materialButton, false)
        }

        fun setBirthDateErrorVisible(
            message: String, materialButton: MaterialButton, view: View?,
            errorTextView: AppCompatTextView?
        ) {
            errorTextView!!.text = message
            view!!.setBackgroundColor(ContextCompat.getColor(view.context!!, color_FA70B4))
            errorTextView.visibility = View.VISIBLE
            validateButton(materialButton, false)
        }

        fun setBirthDateErrorInvisible(
            materialButton: MaterialButton, view: View?,
            errorTextView: AppCompatTextView?
        ) {
            view?.setBackgroundColor(ContextCompat.getColor(view.context, colorWhite))
            errorTextView?.visibility = INVISIBLE
            validateButton(materialButton, true)
        }

        fun setPhoneErrorVisible(
            message: String, materialButton: MaterialButton,
            errorTextView: AppCompatTextView?
        ) {
            errorTextView!!.text = message
            errorTextView.visibility = View.VISIBLE
            validateButton(materialButton, false)
        }
    }
}