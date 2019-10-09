package com.ng.printtag.allrequest

import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityAllRequestsBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.allrequests.AllRequestModel
import org.json.JSONObject
import retrofit2.Response

class ActivityAllRequests : BaseActivity<ActivityAllRequestsBinding>() {


    private lateinit var binding: ActivityAllRequestsBinding
    lateinit var allRequest: MutableList<AllRequestModel.Data>

    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        actBaseBinding.rlMain.removeView(actBaseBinding.headerToolBar)
        allRequest = ArrayList()
        callAllRequestApi()
    }

    private fun setAdapter() {
        val adapterDisburse = AllRequestsAdapter(
            this@ActivityAllRequests,
            allRequest,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                }
            })
        binding.rvAllRequests.adapter = adapterDisburse
    }

    private fun callAllRequestApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityAllRequests).data!!.userId
        )
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityAllRequests,
            body,
            Constant.CALL_ALL_REQUEST,
            this,
            restClientModel
        )
    }

    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            Constant.CALL_ALL_REQUEST -> {
                val rootResponse = response.body() as AllRequestModel
                when (rootResponse.success) {
                    true -> {
                        allRequest = rootResponse.data!!
                        setAdapter()
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
            this@ActivityAllRequests,
            title,
            message,
            "",
            getString(R.string.a_btn_ok),
            "", null
        )

    }


    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_all_requests
}