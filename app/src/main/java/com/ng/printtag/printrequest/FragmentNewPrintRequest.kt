package com.ng.printtag.printrequest

import android.app.DatePickerDialog
import android.text.Editable
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ng.printtag.R
import com.ng.printtag.apputils.*
import com.ng.printtag.apputils.custom.GridItemDecoration
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentNewPrintRequestBinding
import com.ng.printtag.dialog.DialogDepartment
import com.ng.printtag.dialog.DialogTypeStore
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.DepartmentModel
import java.util.*
import kotlin.collections.ArrayList


class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    lateinit var binding: FragmentNewPrintRequestBinding
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var context: ActivityNewPrintRequest? = null
    var isDeptSelected: Boolean = false
    var valueBuilder = ""

    override fun initFragment() {
        binding = getFragmentDataBinding()
        context = activity as ActivityNewPrintRequest
        binding.layoutManager = GridLayoutManager(activity, Constant.DOC_LIST_COLUMN)
        val spacing = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)).toInt()
        binding.rvTemplateList.addItemDecoration(GridItemDecoration(Constant.DOC_LIST_COLUMN, spacing, false))

        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())
        handleClick()
        context!!.callStoreApi()
        ErrorActions.validateButton(binding.btnSubmit, false)


    }

    fun setAdapter(templateList: MutableList<DepartmentModel.Template>?) {
        val adapterTemplateList = TemplateListAdapter(
            activity!!,
            templateList!!,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                }
            })
        binding.rvTemplateList.adapter = adapterTemplateList

        context!!.callTemplateDetails()
        binding.linearTemplateData.visibility = View.VISIBLE
        ErrorActions.validateButton(binding.btnSubmit, true)
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

                    if (item == 0) {
                        context!!.tagType = "FreshTag"
                    } else {
                        context!!.tagType = "InventoryTag"

                    }
                    BaseSharedPreference.getInstance(activity!!).putValue(getString(R.string.tagType), item.toString())

                }

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
                        Editable.Factory.getInstance().newEditable(context!!.arrayStoreKey.get(item as Int))


                    context!!.storeKey = context!!.arrayStoreValue.get(item as Int)
                    BaseSharedPreference.getInstance(activity!!)
                        .putValue(getString(R.string.storeNumber), item.toString())

                }

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
                    stringBuilder.append(", ")
                    valueBuilder.append(context!!.arrayDeptValue[deptPosition[i]])
                    valueBuilder.append(",")
                }
                isDeptSelected = true
                binding.edtDepartment.text =
                    Editable.Factory.getInstance().newEditable(stringBuilder.substring(0, stringBuilder.length - 2))
                context!!.callDepartmentApi(
                    context!!.tagType,
                    context!!.storeKey,
                    valueBuilder.substring(0, valueBuilder.length - 1)
                )
                context!!.department_key = valueBuilder.substring(0, valueBuilder.length - 1)
                BaseSharedPreference.getInstance(activity!!)
                    .putValue(getString(R.string.department), context!!.department_key)

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
        binding.btnSubmit.setOnClickListener {
            context!!.callSubmitApi(binding.edtEffectiveDate.text.toString(), binding.edtInfo.text.toString())
        }
        binding.edtDepartment.setOnClickListener {
            if (context!!.storeKey.isNotEmpty() && !binding.edtTagType.text.isNullOrBlank() && context!!.arrayDeptKey.isNullOrEmpty()) {

                context!!.callDepartmentApi(context!!.tagType, context!!.storeKey, "")
            }
            if (!context!!.arrayDeptKey.isNullOrEmpty()) {

                callDepartmentDialog()
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