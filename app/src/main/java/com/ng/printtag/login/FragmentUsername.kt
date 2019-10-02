package com.ng.printtag.login

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.ng.printtag.R
import com.ng.printtag.apputils.*
import com.ng.printtag.databinding.FragmentSignInUsernameBinding
import ng.pdp.base.BaseFragment

import java.util.regex.Pattern

class FragmentUsername : BaseFragment<FragmentSignInUsernameBinding>() {

    private lateinit var binding: FragmentSignInUsernameBinding

    override fun initFragment() {
        binding = getFragmentDataBinding()
        handelClick()

        binding.edtUserName.requestFocus()
    }

    override fun getLayoutId(): Int = R.layout.fragment_sign_in_username


    private fun handelClick() {
        binding.btnLoginName.setOnClickListener { view ->
            super.onClick(view)
            goToNext()
        }

    }

    private fun goToNext() {
        val fieldValue = binding.edtUserName.text.toString().trim()
        if (!(activity as ActivityLogin).loginModel.userName.isNullOrEmpty()) {
            if ((activity as ActivityLogin).loginModel.userName != fieldValue)
                (activity as ActivityLogin).loginModel.password = ""
        }
        (activity as ActivityLogin).loginModel.userName = fieldValue
        Utils.navigateTo(binding.btnLoginName, R.id.actionPassword, null)
    }

    override fun onResume() {
        super.onResume()
        val fieldValue = binding.edtUserName.text.toString()
        if (fieldValue.isNotEmpty()){
            (activity as ActivityLogin).loginModel.userName = fieldValue
        (activity as ActivityLogin).loginModel.userName = (activity as ActivityLogin).loginModel.userName

        ErrorActions.validateButton(binding.btnLoginName, true)
        binding.edtUserName.setText((activity as ActivityLogin).loginModel.userName)
        binding.edtUserName.setSelection(fieldValue.length)
    } else
    ErrorActions.validateButton(binding.btnLoginName, false)

        setLabel()
    }

    fun setLabel() {
        binding.tvSignIn.text = getString(R.string.a_title_sign_in)
        binding.inputUser.hint = getString(R.string.a_hint_username)
        binding.btnLoginName.text = getString(R.string.a_btn_next)

    }
}