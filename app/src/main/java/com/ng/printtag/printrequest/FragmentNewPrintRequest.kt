package com.ng.printtag.printrequest

import android.app.DatePickerDialog
import android.text.Editable
import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.apputils.custom.GridItemDecoration
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.models.newrequests.StoreListModel
import org.json.JSONObject
import retrofit2.Response
import java.util.*


class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    private lateinit var binding: FragmentNewPrintRequestBinding
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    private lateinit var arrayStoreKey : ArrayList<String>
    private lateinit var arrayStoreValue : ArrayList<String>
    private lateinit var arrayDeptKey : ArrayList<String>
    private lateinit var arrayDeptValue : ArrayList<String>
    var storeKey : String = ""
    override fun initFragment() {
        binding = getFragmentDataBinding()
        arrayStoreKey = ArrayList()
        arrayStoreValue = ArrayList()
        arrayDeptKey = ArrayList()
        arrayDeptValue = ArrayList()

        binding.layoutManager = GridLayoutManager(activity, Constant.DOC_LIST_COLUMN)
        val spacing = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)).toInt()
        binding.rvTemplateList.addItemDecoration(GridItemDecoration(Constant.DOC_LIST_COLUMN, spacing, false))

        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())
        handleClick()
        callStoreApi()

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
                if (fromWhere == Constant.TAG_TYPE) {
                    binding.edtTagType.text = Editable.Factory.getInstance().newEditable(tagType.get(item as Int))
                }
                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun callStoreTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_STORE

        dialog.stringList = arrayStoreKey

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                if (fromWhere == Constant.TAG_STORE)
                {
                    binding.edtStoreNo.text = Editable.Factory.getInstance().newEditable(arrayStoreValue.get(item as Int))
                    storeKey = arrayStoreKey.get(item as Int)
            }
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
                binding.edtDepartment.text =Editable.Factory.getInstance().newEditable(item.toString())
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

            if(!arrayStoreKey.isNullOrEmpty())
            {
                if(arrayStoreKey.size >1) {
                    callStoreTypeDialog()
                }
            }

            //callStoreApi()
        }
        binding.edtDepartment.setOnClickListener{
            if(storeKey.isNotEmpty() && !binding.edtTagType.text.isNullOrBlank())
            {
                //callDepartmentApi()
            }
        }
        binding.edtEffectiveDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun callStoreApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(resources.getString(R.string.userId), AppUtils.getUserModel(activity!!).data!!.userId)
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            activity!!,
            body,
            Constant.CALL_STORE_URL,
            this,
            restClientModel
        )
    }

    private fun callDepartmentApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put(resources.getString(R.string.userId), AppUtils.getUserModel(activity!!).data!!.userId)
        rootJson.put(resources.getString(R.string.tagType),binding.edtTagType.text.toString())
        rootJson.put(resources.getString(R.string.storeNumber),storeKey)

        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            activity!!,
            body,
            Constant.CALL_DEPARTMENT_URL,
            this,
            restClientModel
        )
    }


    private fun openDatePicker() {
        val c = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            activity!!,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.edtEffectiveDate.text = Editable.Factory.getInstance()
                    .newEditable((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
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
                        if(!arrayStoreKey.isNullOrEmpty())
                            if(arrayStoreKey.size == 1)
                            {
                                binding.edtStoreNo.text = Editable.Factory.getInstance().newEditable(arrayStoreValue[0])
                            }

                    }else->
                    {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }
/*
            Constant.CALL_DEPARTMENT_URL -> {
                val rootResponse = response.body() as StoreListModel
                when (rootResponse.success) {
                    true -> {

                        val jsonObjectStore = rootResponse.data!!.stores
                        val jsonObj = JSONObject(jsonObjectStore)
                        for (i in 0 until jsonObj.length()) {

                            arrayDeptKey.add(jsonObj.names().getString(i))
                            arrayDeptKey.add(
                                jsonObj.get(jsonObj.names().getString(i)) as String

                            )
                        }
                        if(!arrayDeptKey.isNullOrEmpty())
                            if(arrayDeptKey.size == 1)
                            {
                                binding.edtDepartment.text = Editable.Factory.getInstance().newEditable(arrayDeptValue[0])
                            }
                        else
                            {
                                callDepartmentDialog()
                            }
                    }else->
                {
                    showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                }
                }
            }
*/


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