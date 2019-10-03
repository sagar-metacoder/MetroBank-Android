package com.ng.printtag.login


import com.ng.printtag.R
import com.ng.printtag.api.RestClient
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant.CALL_SIGN_URL
import com.ng.printtag.apputils.ErrorActions
import com.ng.printtag.apputils.Utils
import com.ng.printtag.databinding.FragmentSignInPasswordBinding
import com.ng.printtag.models.generic.GenericRootResponse
import ng.pdp.api.RestClientModel
import ng.pdp.base.BaseFragment
import retrofit2.Response


class FragmentPassword : BaseFragment<FragmentSignInPasswordBinding>() {

    private lateinit var binding: FragmentSignInPasswordBinding
    private lateinit var accessToken: String

    override fun initFragment() {
        binding = getFragmentDataBinding()
        binding.userName = (activity as ActivityLogin).loginModel.userName
        binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
        binding.edtPassword.requestFocus()

        handelClick()
    }

    override fun getLayoutId(): Int = R.layout.fragment_sign_in_password


    private fun handelClick() {
        binding.btnLoginPassword.setOnClickListener { view ->
            super.onClick(view)

            Utils.gotoHomeScreen(activity!!)
            //validateUser()

        }
    }






    private fun validateUser() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        RestClient().apiRequest(
            activity!!,
            (activity!! as ActivityLogin).loginModel,
            CALL_SIGN_URL,
            this,
            restClientModel
        )
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            CALL_SIGN_URL -> {
                val rootResponse = response.body() as GenericRootResponse
                when (rootResponse.success) {
                    true -> {
                        goToNext()
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }
        }
    }
    private fun showError(title: String, message: String) {
        CallDialog.errorDialog(
            activity!!,
            title,
            message,
            "",
            getString(R.string.a_btn_ok),
            "", null
        )

    }


    override fun onApiError(response: Any, reqCode: Int) {
        super.onApiError(response, reqCode)


    }


    private fun goToNext() {
        (activity as ActivityLogin).loginModel.password = binding.edtPassword.text.toString().trim()
    }

    override fun onResume() {
        super.onResume()
        val fieldValue = binding.edtPassword.text.toString()
        if (fieldValue.isNotEmpty()) {
            (activity as ActivityLogin).loginModel.password = fieldValue
            (activity as ActivityLogin).loginModel.userName = (activity as ActivityLogin).loginModel.userName

            ErrorActions.validateButton(binding.btnLoginPassword, true)
            binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
            binding.edtPassword.setSelection(fieldValue.length)
        } else
            ErrorActions.validateButton(binding.btnLoginPassword, false)
        setLabel()
    }

    fun setLabel() {
        binding.tvSignIn.text = getString(R.string.a_title_sign_in)
        binding.inputPassword.hint = getString(R.string.a_hint_password)
        binding.btnLoginPassword.text = getString(R.string.a_btn_login)


    }

}