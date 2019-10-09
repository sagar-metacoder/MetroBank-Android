package com.ng.printtag.printrequest

import android.app.DatePickerDialog
import android.text.Editable
import android.util.Log
import android.widget.TextView
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.models.login.LoginModel
import org.json.JSONObject
import retrofit2.Response
import java.util.*


class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    private lateinit var binding: FragmentNewPrintRequestBinding
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    override fun initFragment() {
        binding = getFragmentDataBinding()

        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())
        handleClick()
    }


    private fun callTagTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.title = getString(R.string.a_title_select_tag_type)

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                binding.edtTagType.text = Editable.Factory.getInstance().newEditable(item.toString())

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
        binding.edtEffectiveDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun callStoreApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true

        val rootJson = JSONObject()
        rootJson.put("userId", AppUtils.getUserModel(activity!!).data!!.userId)
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            activity!!,
            body,
            Constant.CALL_STORE_URL,
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
            activity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                binding.edtEffectiveDate.text = Editable.Factory.getInstance()
                    .newEditable((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
            },
            mYear,
            mMonth,
            mDay
        )
        val tv = TextView(activity)

        /* // Create a TextView programmatically
         val lp = RelativeLayout.LayoutParams(
             ActionBar.LayoutParams.WRAP_CONTENT, // Width of TextView
             ActionBar.LayoutParams.WRAP_CONTENT
         ) // Height of TextView
         tv.layoutParams = lp
         tv.setPadding(10, 10, 10, 10)
         tv.gravity = Gravity.CENTER
         tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
         tv.text = "This is a custom title."
         tv.setTextColor(Color.parseColor("#FFD2DAA7"))
         tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"))*/

        datePickerDialog.show()
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            Constant.CALL_STORE_URL -> {
                val rootResponse = response.body() as LoginModel
                when (rootResponse.success) {
                    true -> {


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





    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}