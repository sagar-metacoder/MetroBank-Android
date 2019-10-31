package com.ng.printtag.login


import androidx.databinding.ObservableBoolean
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.*
import com.ng.printtag.apputils.Constant.CALL_SIGN_URL
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentLoginBinding
import com.ng.printtag.models.login.LoginModel
import org.json.JSONObject
import retrofit2.Response


class FragmentLogin : BaseFragment<FragmentLoginBinding>() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var accessToken: String

    override fun initFragment() {
        binding = getFragmentDataBinding()
        binding.isEnable = ObservableBoolean(false)
        binding.userName = (activity as ActivityLogin).loginModel.userName
        binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
        binding.edtUserName.setText((activity as ActivityLogin).loginModel.userName)
        binding.edtUserName.requestFocus()

        handelClick()


    }

    override fun getLayoutId(): Int = R.layout.fragment_login


    private fun handelClick() {
        binding.btnLoginPassword.setOnClickListener { view ->
            super.onClick(view)


            validateUser()

        }
    }


    private fun validateUser() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(activity!!, true, "")

        val rootJson = JSONObject()
        rootJson.put(activity!!.resources.getString(R.string.key_userName), binding.edtUserName.text.toString().trim())
        rootJson.put(activity!!.resources.getString(R.string.key_password), binding.edtPassword.text.toString().trim())
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            activity!!,
            body,
            CALL_SIGN_URL,
            this,
            restClientModel
        )
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            CALL_SIGN_URL -> {
                ProgressDialog.displayProgressDialog(activity!!, false, "")

                val rootResponse = response.body() as LoginModel
                when (rootResponse.success) {
                    true -> {
                        AppUtils.setUserData(
                            activity!!,
                            rootResponse
                        )
                        Utils.gotoHomeScreen(activity!!)
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }
        }
    }

    override fun onApiError(response: Any, reqCode: Int) {
        super.onApiError(response, reqCode)
        ProgressDialog.displayProgressDialog(activity!!, false, "")

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


    override fun onResume() {
        super.onResume()
        val edtPassword = binding.edtPassword.text.toString().trim()
        val edtUsername = binding.edtUserName.text.toString().trim()
        if (edtPassword.isNotEmpty() && edtUsername.isNotEmpty()) {
            (activity as ActivityLogin).loginModel.password = edtPassword
            (activity as ActivityLogin).loginModel.userName = edtUsername

            ErrorActions.validateButton(binding.btnLoginPassword, true)
            binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
            binding.edtPassword.setSelection(edtPassword.length)
            binding.edtUserName.setText((activity as ActivityLogin).loginModel.userName)
            binding.edtUserName.setSelection(edtUsername.length)
        } else
            ErrorActions.validateButton(binding.btnLoginPassword, false)
        setLabel()
    }

    fun setLabel() {
        binding.tvSignIn.text = getString(R.string.a_title_sign_in)
        binding.inputPassword.hint = getString(R.string.a_hint_password)
        binding.inputUser.hint = getString(R.string.a_hint_username)
        binding.btnLoginPassword.text = getString(R.string.a_btn_login)


    }

}