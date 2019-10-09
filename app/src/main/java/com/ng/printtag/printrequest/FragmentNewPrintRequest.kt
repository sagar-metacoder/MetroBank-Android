package com.ng.printtag.printrequest

import android.text.Editable
import android.util.Log
import com.ng.printtag.R
import com.ng.printtag.api.ApiResponseListener
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.apputils.Utils
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.models.login.LoginModel
import com.ng.printtag.models.newrequests.StoreListModel
import okhttp3.internal.Util
import org.json.JSONObject
import retrofit2.Response






class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    private lateinit var binding: FragmentNewPrintRequestBinding
    private lateinit var arrayStoreKey : ArrayList<String>
    private lateinit var arrayStoreValue : ArrayList<String>
    override fun initFragment() {
        binding = getFragmentDataBinding()
        arrayStoreKey = ArrayList()
        arrayStoreValue = ArrayList()
        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())
        handleClick()
    }


    private fun callTagTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_TYPE
        val tagType : ArrayList<String> = ArrayList()
        tagType.add("Fresh Tag")
        tagType.add("Inventory Tag")
        dialog.stringList = tagType

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                binding.edtTagType.text = Editable.Factory.getInstance().newEditable( item.toString())

                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun callStoreTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_TYPE

        dialog.stringList = arrayStoreKey

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                binding.edtTagType.text = Editable.Factory.getInstance().newEditable( item.toString())

                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }


    private fun callDepartmentDialog() {
        val dialog = DialogDepartment()
        dialog.title = getString(R.string.a_title_select_department)

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun handleClick() {
        binding.edtTagType.setOnClickListener {
            //            callTagTypeDialog()

            callTagTypeDialog()
        }
        binding.edtStoreNo.setOnClickListener {
            callStoreApi()
        }
    }

    private fun callStoreApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(resources.getString(R.string.userId),AppUtils.getUserModel(activity!!).data!!.userId)
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            activity!!,
            body,
            Constant.CALL_STORE_URL,
            this,
            restClientModel
        )
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when(reqCode) {
            Constant.CALL_STORE_URL -> {
                val rootResponse = response.body() as StoreListModel
                when (rootResponse.success) {
                    true -> {

                        val jsonObjectStore = rootResponse.data!!.stores as String
                        val jsonObj = JSONObject(jsonObjectStore)
                        for (i in 0 until jsonObj.length()) {

                            arrayStoreKey.add(jsonObj.names().getString(i))
                            arrayStoreValue.add(
                                jsonObj.get(jsonObj.names().getString(i)) as String

                            )
                        }
                        callStoreTypeDialog()
                    }else->
                    {
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





    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}