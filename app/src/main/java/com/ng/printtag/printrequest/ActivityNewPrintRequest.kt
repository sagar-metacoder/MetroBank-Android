package com.ng.printtag.printrequest

import android.text.Editable
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityNewPrintRequestBinding
import com.ng.printtag.models.newrequests.DepartmentModel
import com.ng.printtag.models.newrequests.StoreListModel
import kotlinx.android.synthetic.main.activity_new_print_request.*
import org.json.JSONObject
import retrofit2.Response
import java.util.*

class ActivityNewPrintRequest : BaseActivity<ActivityNewPrintRequestBinding>() {
    private lateinit var binding: ActivityNewPrintRequestBinding
    var arrayStoreKey: ArrayList<String> = ArrayList()
    var arrayStoreValue: ArrayList<String> = ArrayList()
    var arrayDeptKey: ArrayList<String> = ArrayList()
    var arrayDeptValue: ArrayList<String> = ArrayList()
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
                            arrayStoreValue.add(rootResponse.data!!.stores?.get(i)?.key!!)
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

                        for (i in 0 until rootResponse.data!!.departments!!.size) {

                            arrayDeptKey.add(rootResponse.data!!.departments?.get(i)?.key!!)
                            arrayDeptValue.add(rootResponse.data!!.departments?.get(i)?.key!!)
                        }
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentNewPrintRequest) {
                            if (!arrayDeptKey.isNullOrEmpty())

                                currentFragment.callDepartmentDialog()

                        }
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
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