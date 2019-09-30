package com.ng.printtag.login


import com.ng.printtag.R
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.ErrorActions
import com.ng.printtag.apputils.Utils
import com.ng.printtag.databinding.FragmentSignInPasswordBinding
import ng.pdp.api.RestClientModel
import ng.pdp.base.BaseFragment
import retrofit2.Response


class FragmentPassword : BaseFragment<FragmentSignInPasswordBinding>() {

    private lateinit var binding: FragmentSignInPasswordBinding
    private lateinit var accessToken: String

    override fun initFragment() {
        binding = getFragmentDataBinding()
        binding.emailId = (activity as ActivityLogin).loginModel.mail
        binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
        BaseSharedPreference.getInstance(activity!!).putValue(getString(R.string.pref_auth_token), "")
        handelClick()
    }

    private fun handelClick() {
        binding.btnLoginPassword.setOnClickListener { view ->
            super.onClick(view)
            //getToken()
            validateUser()

        }
        binding.tvForgotPassword.setOnClickListener {
            CallDialog.openDialog(activity!!, DialogForgotPassword())
        }
    }


    private fun getToken() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        restClientModel.isErrorScreenShow = true

        val fieldMap = HashMap<String, String>()
        fieldMap[getString(R.string.key_client_id)] = Constant.CLIENT_ID
        fieldMap[getString(R.string.key_scope)] = Constant.scope
        fieldMap[getString(R.string.key_client_secret)] = Constant.clientSecret
        fieldMap[getString(R.string.key_grant_type)] = Constant.clientGrantType
        fieldMap[getString(R.string.key_resource)] = Constant.clientResource
        fieldMap[getString(R.string.key_username)] = (activity as ActivityLogin).loginModel.mail!!
        fieldMap[getString(R.string.key_password)] = binding.edtPassword.text.toString()

        RestClient().apiRequest(
            activity!!,
            fieldMap,
            CALL_AZURE_TOKEN,
            this,
            restClientModel
        )
    }

    private fun callGraphAPI() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        restClientModel.isErrorScreenShow = true

        RestClient().apiRequest(
            activity!!, arrayOf(
                accessToken
            ), CALL_AZURE_DETAIL, this, restClientModel
        )

    }


    private fun validateUser() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        RestClient().apiRequest(
            activity!!,
            (activity!! as ActivityLogin).loginModel,
            CALL_ASSOCIATE_DETAILS_REQUEST_CODE,
            this,
            restClientModel
        )
    }

    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            CALL_ASSOCIATE_DETAILS_REQUEST_CODE -> {
                val rootResponse = response.body() as GenericRootResponse
                when (rootResponse.success) {
                    true -> {
                        goToNext()
                    }
                    else -> {
                        showError(Utils.getLabel(getString(R.string.a_lbl_server_title)), rootResponse.msg!!)
                    }
                }
            }

            CALL_AZURE_TOKEN -> {
                val rootResponse = response.body() as AzurToken
                when (true) {
                    true -> {
                        if (rootResponse.accessToken != null) {
                            accessToken = rootResponse.accessToken.toString()
                            /* call graph */
                            BaseSharedPreference.getInstance(activity!!)
                                .putValue(getString(R.string.pref_auth_token), accessToken)
                            callGraphAPI()
                        } else {
                            val message =
                                Utils.getLabel(getString(R.string.a_lbl_auth_error)) + "\n\n" + Utils.getLabel(
                                    getString(R.string.a_lbl_auth_reason)
                                ) + "\n\n" + rootResponse.errorDescription + "\n\n" + Utils.getLabel(getString(R.string.a_lbl_contact_admin))
                            showError(Utils.getLabel(getString(R.string.a_auth_fail)), message)


                        }

                    }
                    false -> {
                        showError(
                            Utils.getLabel(getString(R.string.a_lbl_warning_title)),
                            Utils.getLabel(getString(R.string.a_msg_server_error))
                        )

                    }
                }
            }
            CALL_AZURE_DETAIL -> {
                val rootResponse = response.body() as LoginModel
                when (true) {
                    true -> {
                        if (rootResponse.id != null) {
                            BaseSharedPreference.getInstance(activity!!)
                                .putValue(getString(R.string.pref_auth_token), "")
                            BaseSharedPreference.getInstance(activity!!)
                                .putValue(getString(R.string.pref_auth_token), RequestMethods.getAuthToken(activity!!))
                            (activity as ActivityLogin).loginModel.userName = rootResponse.getFullProfileName()

                            validateUser()
                        } else {
                            ProgressDialog.displayProgressDialog(activity!!, false, "")
                            val message =
                                Utils.getLabel(getString(R.string.a_lbl_auth_error)) + "\n\n" + Utils.getLabel(
                                    getString(R.string.a_lbl_auth_reason)
                                ) + "\n\n" + rootResponse.error!!.message + "\n\n" + Utils.getLabel(getString(R.string.a_lbl_contact_admin))
                            showError(Utils.getLabel(getString(R.string.a_auth_fail)), message)

                        }
                    }
                    false -> {
                        showError(
                            Utils.getLabel(getString(R.string.a_lbl_warning_title)),
                            Utils.getLabel(getString(R.string.a_msg_server_error))
                        )
                    }
                }
            }
        }
    }

    override fun onApiError(response: Any, reqCode: Int) {
        super.onApiError(response, reqCode)
        when (reqCode) {
            CALL_AZURE_TOKEN -> {
                val message =
                    Utils.getLabel(getString(R.string.a_lbl_auth_error)) + "\n\n" + Utils.getLabel(getString(R.string.a_lbl_auth_reason)) + "\n\n" + Utils.getLabel(
                        getString(R.string.a_msg_invalid)
                    ) + "\n\n" + Utils.getLabel(
                        getString(R.string.a_lbl_contact_admin)
                    )
                showError(Utils.getLabel(getString(R.string.a_auth_fail)), message)
            }
            CALL_AZURE_DETAIL -> {
                val message =
                    Utils.getLabel(getString(R.string.a_lbl_auth_error)) + "\n\n" + Utils.getLabel(
                        getString(R.string.a_lbl_auth_reason)
                    ) + "\n\n" + Utils.getLabel(getString(R.string.a_msg_invalid)) + "\n\n" + Utils.getLabel(getString(R.string.a_lbl_contact_admin))
                showError(Utils.getLabel(getString(R.string.a_auth_fail)), message)
            }
            CALL_ASSOCIATE_DETAILS_REQUEST_CODE -> goToNext()
        }


    }

    private fun showError(title: String, message: String) {
        CallDialog.errorDialog(
            activity!!,
            title,
            message,
            "",
            Utils.getLabel(getString(R.string.a_btn_ok)),
            "", null
        )

    }

    private fun goToNext() {
        (activity as ActivityLogin).loginModel.password = binding.edtPassword.text.toString().trim()
        (activity as ActivityLogin).loginModel.userName = (activity as ActivityLogin).loginModel.mail
        Utils.navigateTo(binding.tvForgotPassword, R.id.actionStoreNumber, null)
    }

    override fun onResume() {
        super.onResume()
        val fieldValue = binding.edtPassword.text.toString()
        if (fieldValue.isNotEmpty()) {
            (activity as ActivityLogin).loginModel.password = fieldValue
            (activity as ActivityLogin).loginModel.userName = (activity as ActivityLogin).loginModel.mail

            ErrorActions.validateButton(binding.btnLoginPassword, true)
            binding.edtPassword.setText((activity as ActivityLogin).loginModel.password)
            binding.edtPassword.setSelection(fieldValue.length)
        } else
            ErrorActions.validateButton(binding.btnLoginPassword, false)
        setLabel()
    }

    fun setLabel() {
        binding.tvSignIn.text = Utils.getLabel(getString(R.string.a_title_sign_in))
        binding.inputPassword.hint = Utils.getLabel(getString(R.string.a_hint_password))
        binding.btnLoginPassword.text = Utils.getLabel(getString(R.string.a_btn_next))
        binding.tvHint.text = Utils.getLabel(getString(R.string.a_hint_employee_password))
        binding.tvForgotPassword.text = Utils.getLabel(getString(R.string.a_lbl_forgot_password))

    }

    override fun getLayoutId(): Int = R.layout.fragment_sign_in_password
}