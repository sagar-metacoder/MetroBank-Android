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
import com.ng.printtag.models.newrequests.StoreDepartmentListModel


class FragmentNewPrintRequest : BaseFragment<FragmentNewPrintRequestBinding>() {
    lateinit var binding: FragmentNewPrintRequestBinding
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var context: ActivityNewPrintRequest? = null
    var isDeptSelected: Boolean = false
    lateinit var departmentValue: String
    var tagTypeExist: Boolean = false
    lateinit var tagTypeArrayList: ArrayList<StoreDepartmentListModel>
    var selectedTag: Int = -1
    var selectedStore: Int = -1
    var deptPositionValue: ArrayList<Int> = ArrayList()


    override fun initFragment() {
        binding = getFragmentDataBinding()
        context = activity as ActivityNewPrintRequest
        tagTypeArrayList = ArrayList()

        init()
        handleClick()

        context!!.callStoreApi()
        ErrorActions.validateButton(binding.btnSubmit, false)


    }

    private fun init() {
        binding.layoutManager = GridLayoutManager(activity, Constant.DOC_LIST_COLUMN)
        val spacing = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)).toInt()
        binding.rvTemplateList.addItemDecoration(GridItemDecoration(Constant.DOC_LIST_COLUMN, spacing, false))
        binding.edtEffectiveDate.text = Editable.Factory.getInstance().newEditable(AppUtils.currentDate())

    }

    fun setAdapter(templateList: MutableList<DepartmentModel.Data.Template>?) {
        val adapterTemplateList = TemplateListAdapter(
            activity!!,
            templateList!!,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                    context!!.templateId = context!!.arrayTemplate[position].id!!
                    context!!.callTemplateDetails(position)

                    binding.linearTemplateData.visibility = View.VISIBLE
                    ErrorActions.validateButton(binding.btnSubmit, true)
                }
            })
        binding.rvTemplateList.adapter = adapterTemplateList


    }


    private fun callTagTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_TYPE




        dialog.stringList = context!!.tagTypeArray
        dialog.selectedTag = selectedTag

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {

                if (fromWhere == Constant.TAG_TYPE) {

                    //  binding.edtDepartment.text = Editable.Factory.getInstance().newEditable("")
                    //deptPositionValue.clear()
                    //context!!.arrayDeptKey.clear()



                    selectedTag = item as Int
                    /* if (!binding.edtTagType.text.isNullOrBlank()) {
                         tagTypeExist = true
                     }*/

                    binding.edtTagType.text =
                        Editable.Factory.getInstance().newEditable(context!!.tagTypeArray.get(item))

                    context!!.tagType = context!!.tagTypeArray.get(item)


                    /*  if (item == 0) {
                          context!!.tagType = getString(R.string.key_freshtag)
                      } else {
                          context!!.tagType = getString(R.string.key_inventorytag)

                      }*/

                    //  if (tagTypeExist) {
                        if (!binding.edtDepartment.text.isNullOrBlank() && !binding.edtStoreNo.text.isNullOrBlank() && !binding.edtTagType.text.isNullOrBlank()) {
                            binding.linearRv.visibility = View.GONE
                            binding.linearTemplateData.visibility = View.GONE
                            binding.edtInfo.text = Editable.Factory.getInstance().newEditable("")

                            ErrorActions.validateButton(binding.btnSubmit, false)

                            isDeptSelected = true
                            context!!.callDepartmentApi(
                                context!!.tagType,
                                context!!.storeKey,
                                departmentValue
                            )
                            //  }
                    }
                }

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun callStoreTypeDialog() {
        val dialog = DialogTypeStore()
        dialog.fromWhere = Constant.TAG_STORE

        dialog.stringList = context!!.arrayStoreKey
        dialog.selectedTag = selectedStore

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                if (fromWhere == Constant.TAG_STORE) {
                    selectedStore = item as Int

                    //binding.edtDepartment.text = Editable.Factory.getInstance().newEditable("")
                    //deptPositionValue.clear()
                    //context!!.arrayDeptKey.clear()

                    binding.linearRv.visibility = View.GONE
                    ErrorActions.validateButton(binding.btnSubmit, false)

                    binding.edtStoreNo.text =
                        Editable.Factory.getInstance().newEditable(context!!.arrayStoreKey.get(item))


                    context!!.storeKey = context!!.arrayStoreValue.get(item)


                    if (!binding.edtDepartment.text.isNullOrBlank() && !binding.edtStoreNo.text.isNullOrBlank() && !binding.edtTagType.text.isNullOrBlank()) {
                        binding.linearRv.visibility = View.GONE
                        binding.linearTemplateData.visibility = View.GONE
                        binding.edtInfo.text = Editable.Factory.getInstance().newEditable("")

                        ErrorActions.validateButton(binding.btnSubmit, false)

                        isDeptSelected = true
                        context!!.callDepartmentApi(
                            context!!.tagType,
                            context!!.storeKey,
                            departmentValue
                        )

                    }




                }

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }


    fun callDepartmentDialog() {
        val dialog = DialogDepartment()
        dialog.title = getString(R.string.a_title_select_department)
        dialog.stringList = context!!.arrayDeptKey
        dialog.deptPosition = deptPositionValue

        dialog.callBackListener = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                val deptPosition: ArrayList<Int> = item as ArrayList<Int>
                deptPositionValue = deptPosition

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

                departmentValue = valueBuilder.substring(0, valueBuilder.length - 1)

                if (binding.edtTagType.text.isNullOrBlank()) {
                    AppUtils.showLongToast(activity!!, resources.getString(R.string.a_msg_tag_type))
                } else if (binding.edtStoreNo.text.isNullOrBlank()) {
                    AppUtils.showLongToast(activity!!, resources.getString(R.string.a_msg_store))
                } else {
                    context!!.callDepartmentApi(
                        context!!.tagType,
                        context!!.storeKey, departmentValue
                    )
                    context!!.departmentKey = valueBuilder.substring(0, valueBuilder.length - 1)
                }

//                Log.d("selected", item.toString())

            }
        }
        CallDialog.openDialog(activity!!, dialog)
    }

    private fun handleClick() {
        binding.edtTagType.setOnClickListener {
            //            callTagTypeDialog()
            if (context!!.tagTypeArray.isNotEmpty()) {
                binding.linearRv.visibility = View.GONE
                ErrorActions.validateButton(binding.btnSubmit, false)

                binding.linearTemplateData.visibility = View.GONE
                binding.edtInfo.text = Editable.Factory.getInstance().newEditable("")



                callTagTypeDialog()
            }
        }
        binding.edtStoreNo.setOnClickListener {

            if (context!!.arrayStoreKey.isNotEmpty()) {
                if (context!!.arrayStoreKey.size > 1) {
                    binding.linearRv.visibility = View.GONE
                    ErrorActions.validateButton(binding.btnSubmit, false)

                    binding.linearTemplateData.visibility = View.GONE
                    binding.edtInfo.text = Editable.Factory.getInstance().newEditable("")

                    callStoreTypeDialog()
                }
            }

            //callStoreApi()
        }
        binding.btnSubmit.setOnClickListener {
            context!!.newPrintReq = true
            context!!.effectiveDate = binding.edtEffectiveDate.text.toString()
            context!!.productInfo = binding.edtInfo.text.toString().trim()
            Utils.navigateTo(binding.btnSubmit, R.id.actionAddProducts, null)


            //  context!!.callSubmitApi(binding.edtEffectiveDate.text.toString(), binding.edtInfo.text.toString())

        }
        binding.edtDepartment.setOnClickListener {
            /*
                        if (binding.edtTagType.text.isNullOrBlank()) {
                            AppUtils.showLongToast(
                                activity!!,
                                resources.getString(R.string.a_msg_tag_type)
                            )
                        } else*/ if (context!!.storeKey.isBlank()) {
                AppUtils.showLongToast(
                    activity!!,
                    resources.getString(R.string.a_msg_store)
                )
        } else if (context!!.storeKey.isNotEmpty() && context!!.arrayDeptKey.isNullOrEmpty()) {

                context!!.callDepartmentApi(context!!.tagType, context!!.storeKey, "")
        } else if (!context!!.arrayDeptKey.isNullOrEmpty() && context!!.storeKey.isNotEmpty()) {

                callDepartmentDialog()
                binding.linearRv.visibility = View.GONE
                ErrorActions.validateButton(binding.btnSubmit, false)

                binding.linearTemplateData.visibility = View.GONE
            binding.edtInfo.text = Editable.Factory.getInstance().newEditable("")

            }
        }
        binding.edtEffectiveDate.setOnClickListener {
            openDatePicker()
        }
    }


    private fun openDatePicker() {

        val array = binding.edtEffectiveDate.text!!.split("/")
        mYear = array[2].toInt()
        mMonth = array[0].toInt() - 1
        mDay = array[1].toInt()


        val datePickerDialog = DatePickerDialog(
            activity!!,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->


                binding.edtEffectiveDate.text = Editable.Factory.getInstance()
                    .newEditable(AppUtils.formattedDate(monthOfYear + 1) + "/" + AppUtils.formattedDate(dayOfMonth) + "/" + year)
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }


    override fun getLayoutId(): Int = R.layout.fragment_new_print_request
}