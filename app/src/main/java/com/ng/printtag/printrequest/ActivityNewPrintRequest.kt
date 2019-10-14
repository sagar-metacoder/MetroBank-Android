package com.ng.printtag.printrequest

import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.*
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityNewPrintRequestBinding
import com.ng.printtag.models.newrequests.DepartmentModel
import com.ng.printtag.models.newrequests.NewPrintReqSubmit
import com.ng.printtag.models.newrequests.StoreListModel
import com.ng.printtag.models.newrequests.TempletListModel
import kotlinx.android.synthetic.main.activity_new_print_request.*
import org.json.JSONObject
import retrofit2.Response

class ActivityNewPrintRequest : BaseActivity<ActivityNewPrintRequestBinding>() {
    private lateinit var binding: ActivityNewPrintRequestBinding
    var arrayStoreKey: ArrayList<String> = ArrayList()
    var arrayStoreValue: ArrayList<String> = ArrayList()
    var arrayDeptKey: ArrayList<String> = ArrayList()
    var arrayDeptValue: ArrayList<String> = ArrayList()
    lateinit var arrayTemplate: MutableList<DepartmentModel.Data.Template>
    var storeKey: String = ""
    var tagType: String = ""
    var department_key: String = ""
    var template_postion: Int = 0


    override fun initMethod() {
        binding = getViewDataBinding()
    }


    fun callStoreApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_STORE_URL,
            this,
            restClientModel
        )
    }

    fun callDepartmentApi(tagType: String, storeKey: String, department: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
//        rootJson.put(resources.getString(R.string.tagType), binding.edtTagType.text.toString())
        rootJson.put(resources.getString(R.string.tagType), tagType)
        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(resources.getString(R.string.department), department)
//        rootJson.put(resources.getString(R.string.storeNumber), storeKey)

        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_DEPARTMENT_URL,
            this,
            restClientModel
        )
    }

    fun callTemplateDetails(position: Int) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        template_postion = position
        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        rootJson.put(resources.getString(R.string.key_templateId), arrayTemplate.get(position).id)
        rootJson.put(resources.getString(R.string.tagType), tagType)

        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(
            resources.getString(R.string.department), department_key
        )
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_TEMPLETS_DETAILS,
            this,
            restClientModel
        )
    }

    fun callSubmitApi(date: String, product_info: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true


        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        rootJson.put(resources.getString(R.string.key_templateId), arrayTemplate.get(template_postion).id)
        rootJson.put(resources.getString(R.string.tagType), tagType)

        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(resources.getString(R.string.department), department_key)
        rootJson.put(resources.getString(R.string.key_effectiveDate), Utils.parseDateToMMddyyyy(date))

        rootJson.put(
            resources.getString(R.string.key_language),
            BaseSharedPreference.getInstance(this@ActivityNewPrintRequest).getLanguage(
                resources.getString(R.string.pref_language)
            )
        )
        rootJson.put(resources.getString(R.string.key_action), resources.getString(R.string.action_submit))
        rootJson.put(resources.getString(R.string.key_aisleInfo), product_info)


        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_NEW_REQUEST_SUBMIT,
            this,
            restClientModel
        )
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            Constant.CALL_STORE_URL -> {
                val rootResponse = response.body() as StoreListModel
                when (rootResponse.success) {
                    true -> {


                        // val jsonObj = JSONObject(jsonObjectStore)
                        for (i in 0 until rootResponse.data!!.stores!!.size) {

                            arrayStoreKey.add(rootResponse.data!!.stores?.get(i)?.key!!)
                            arrayStoreValue.add(rootResponse.data!!.stores?.get(i)?.value!!)
                        }
                        if (!arrayStoreKey.isNullOrEmpty())
                            if (arrayStoreKey.size == 1) {
                                val currentFragment = getCurrentFragment()
                                //                                    binding.edtStoreNo.text = Editable.Factory.getInstance().newEditable(arrayStoreValue[0])
                                if (currentFragment != null && currentFragment is FragmentNewPrintRequest) currentFragment.binding.edtStoreNo.text =
                                    Editable.Factory.getInstance().newEditable(arrayStoreValue[0])
                            }

                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }

            Constant.CALL_DEPARTMENT_URL -> {
                val rootResponse = response.body() as DepartmentModel
                when (rootResponse.success) {
                    true -> {
                        arrayDeptKey = ArrayList()
                        arrayDeptValue = ArrayList()
                        arrayTemplate = ArrayList()

                        for (i in 0 until rootResponse.data!!.departments!!.size) {

                            arrayDeptKey.add(rootResponse.data!!.departments?.get(i)?.key!!)
                            arrayDeptValue.add(rootResponse.data!!.departments?.get(i)?.value!!)
                        }
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentNewPrintRequest) {
                            if (!arrayDeptKey.isNullOrEmpty() && !currentFragment.isDeptSelected)
                                currentFragment.callDepartmentDialog()

                        }
                        if (!rootResponse.data!!.templates.isNullOrEmpty()) {
                            if (rootResponse.data!!.templates!!.size >= 1 && currentFragment is FragmentNewPrintRequest) {
                                currentFragment.setAdapter(rootResponse.data!!.templates)
                                arrayTemplate =
                                    rootResponse.data!!.templates as ArrayList<DepartmentModel.Data.Template>
                                currentFragment.isDeptSelected = false
                            }
                        }
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }

            Constant.CALL_TEMPLETS_DETAILS -> {
                val rootResponse = response.body() as TempletListModel
                when (rootResponse.success) {
                    true -> {
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentNewPrintRequest) {

                            currentFragment.binding.linearTemplateData.visibility = View.VISIBLE
                            currentFragment.binding.model = rootResponse.data!!.templateDetails


                        }
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.data!!.msg!!)
                    }
                }
            }

            Constant.CALL_NEW_REQUEST_SUBMIT -> {
                val rootResponse = response.body() as NewPrintReqSubmit
                when (rootResponse.success) {
                    true -> {

                        AppUtils.showLongToast(this@ActivityNewPrintRequest, "Product Addded Successfully.")
                        Utils.gotoHomeScreen(this@ActivityNewPrintRequest)
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.data!!.msg!!)
                    }
                }
            }


        }
    }

    fun getCurrentFragment(): Fragment? {
        return try {
            navigation_new_print_request.childFragmentManager.findFragmentById(R.id.navigation_new_print_request)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun showError(title: String, message: String) {
        CallDialog.errorDialog(
            this@ActivityNewPrintRequest,
            title,
            message,
            "",
            getString(R.string.a_btn_ok),
            "", null
        )

    }


    override fun getLayoutId(): Int = R.layout.activity_new_print_request
}