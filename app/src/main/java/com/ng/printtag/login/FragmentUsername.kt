package com.ng.printtag.login

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.ng.printtag.R
import com.ng.printtag.apputils.*
import com.ng.printtag.databinding.FragmentSignInUsernameBinding
import ng.pdp.base.BaseFragment

import java.util.regex.Pattern

class FragmentUsername : BaseFragment<FragmentSignInUsernameBinding>() {

   // private lateinit var binding: FragmentSignInUsernameBinding

    override fun initFragment() {
      /*  binding = getFragmentDataBinding()
//        binding.edtEmailAddress.requestFocus()
        handelClick()
        binding.edtEmailAddress.requestFocus()*/
    }

    private fun handelClick() {
        /*binding.btnLoginName.setOnClickListener { view ->
            super.onClick(view)
           // goToNext()
        }*/

        /*     binding.tvBackHome.setOnClickListener { view ->
                 super.onClick(view)
                 AppUtils.navigateToOtherScreen(activity!!, ActivityAssocPhoto::class.java, false)
             }*/
    }
    override fun getLayoutId(): Int = R.layout.fragment_sign_in_username


    /* private fun goToNext() {
         val fieldValue = binding.edtEmailAddress.text.toString().trim()
         if (!(activity as ActivityLogin).loginModel.mail.isNullOrEmpty()) {
             if ((activity as ActivityLogin).loginModel.mail != fieldValue)
                 (activity as ActivityLogin).loginModel.password = ""
         }
         (activity as ActivityLogin).loginModel.mail = fieldValue
         Utils.navigateTo(binding.btnLoginName, R.id.actionPassword, null)
     }

     override fun onResume() {
         super.onResume()
         val fieldValue = binding.edtEmailAddress.text.toString()
         if (fieldValue.isNotEmpty())
             (activity as ActivityLogin).loginModel.mail = fieldValue
         if (Pattern.matches(Constant.emailPattern, fieldValue)) {
             ErrorActions.validateButton(binding.btnLoginName, true)
             binding.edtEmailAddress.setText((activity as ActivityLogin).loginModel.mail)
             binding.edtEmailAddress.setSelection(fieldValue.length)
         } else
             ErrorActions.validateButton(binding.btnLoginName, false)

         if (BaseSharedPreference.getInstance(activity!!).getPrefBoolean(getString(R.string.pref_session_out))) {
             binding.tvLoggedOut.visibility = VISIBLE
         } else {
             binding.tvLoggedOut.visibility = INVISIBLE
         }
         setLabel()
     }

     fun setLabel() {
         binding.tvSignIn.text = getString(R.string.a_title_sign_in)
         binding.inputEmail.hint = getString(R.string.a_hint_email)
         binding.btnLoginName.text = getString(R.string.a_btn_next)
         binding.tvLoggedOut.text = getString(R.string.a_lbl_logged_out_automatically)

     }*/
}