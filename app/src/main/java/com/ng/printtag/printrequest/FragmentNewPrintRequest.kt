package com.ng.printtag.printrequest

import android.app.DatePickerDialog
import android.text.Editable
import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.apputils.custom.GridItemDecoration
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import java.util.*
import kotlin.collections.ArrayList


class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    lateinit var binding: FragmentNewPrintRequestBinding
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var context: ActivityNewPrintRequest? = null

    var storeKey: String = ""
    override fun initFragment() {
        binding = getFragmentDataBinding()
        context = activity as ActivityNewPrintRequest
        binding.layoutManager = GridLayoutManager(activity, Constant.DOC_LIST_COLUMN)
        val spacing = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)).toInt()
        binding.rvTemplateList.addItemDecoration(GridItemDecoration(Constant.DOC_LIST_COLUMN, spacing, false))

        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())
        handleClick()
        context!!.callStoreApi()

    }


    private fun callTagTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_TYPE
        val tagType: ArrayList<String> = ArrayList()
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

        dialog.stringList = context!!.arrayStoreKey

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                if (fromWhere == Constant.TAG_STORE) {
                    binding.edtStoreNo.text =
                        Editable.Factory.getInstance().newEditable(context!!.arrayStoreValue.get(item as Int))
                    storeKey = context!!.arrayStoreKey.get(item as Int)
                }
                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }


    fun callDepartmentDialog() {
        val dialog = DialogDepartment()
        dialog.title = getString(R.string.a_title_select_department)
        dialog.stringList = context!!.arrayDeptKey

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                val deptPosition: ArrayList<Int> = item as ArrayList<Int>
                val stringBuilder = StringBuilder()
                val valueBuilder = StringBuilder()
                for (i in 0 until deptPosition.size) {
                    stringBuilder.append(context!!.arrayDeptKey[deptPosition[i]])
                    stringBuilder.append(",")
                    valueBuilder.append(context!!.arrayDeptValue[deptPosition[i]])
                    valueBuilder.append(",")
                }
                binding.edtDepartment.text =
                    Editable.Factory.getInstance().newEditable(stringBuilder)
//                con
//                Log.d("selected", item.toString())

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

            if (context!!.arrayStoreKey.isNotEmpty()) {
                if (context!!.arrayStoreKey.size > 1) {
                    callStoreTypeDialog()
                }
            }

            //callStoreApi()
        }
        binding.edtDepartment.setOnClickListener {
            if (storeKey.isNotEmpty() && !binding.edtTagType.text.isNullOrBlank()) {
                context!!.callDepartmentApi(binding.edtTagType.text.toString(), storeKey, "")
            }
        }
        binding.edtEffectiveDate.setOnClickListener {
            openDatePicker()
        }
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


    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}