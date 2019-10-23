package com.ng.printtag.printrequest

import android.view.View
import com.ng.printtag.R
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddProductBinding
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.AddProductModel

class FragmentAddProduct : BaseFragment<FragmentAddProductBinding>() {

    lateinit var binding: FragmentAddProductBinding
    var context: ActivityNewPrintRequest? = null
    lateinit var adapter: AddProductAdapter


    override fun initFragment() {
        binding = getFragmentDataBinding()
        context = activity as ActivityNewPrintRequest


        if (context!!.addProducts.isNullOrEmpty()) {
            binding.rvAllProduct.visibility = View.GONE
            binding.linearFound.visibility = View.VISIBLE
            binding.relSave.visibility = View.GONE
        }


        handleClick()
        setAdapter()

    }

    override fun getLayoutId(): Int = R.layout.fragment_add_product


    fun setAdapter() {

        adapter = AddProductAdapter(
            context!!.addProducts,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                    if (item.equals("view")) {
                        /*  val mBottomSheetDialog = Dialog(activity!!, R.style.MaterialDialogSheet)

                          val binding = DataBindingUtil.inflate<ViewDataBinding>(
                              LayoutInflater.from(getContext()),
                              R.layout.dialog_product_view,
                              null,
                              false
                          )
                          mBottomSheetDialog.setContentView(binding.getRoot())
                          binding.se
                         // mBottomSheetDialog.setContentView(R.layout.dialog_product_view)
                          mBottomSheetDialog.setCancelable(true)
                          mBottomSheetDialog.window!!
                              .setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                          mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
                          val upcNumber = mBottomSheetDialog.findViewById<View>(R.id.upcNumber)
                          upcNumber
                          mBottomSheetDialog.show()*/
                    } else if (item.equals("delete")) {

                        CallDialog.errorDialog(
                            activity!!,
                            getString(R.string.a_lbl_warning_title),
                            getString(R.string.a_msg_delete),
                            getString(R.string.a_btn_yes),
                            getString(R.string.a_btn_no),
                            getString(R.string.action_delete),
                            object : CallBackInterfaces {
                                override fun onCallBack(item: Any, fromWhere: Any) {

                                    if (fromWhere.equals("ok")) {
                                        context!!.addProducts.removeAt(position)
                                        adapter.notifyDataSetChanged()
                                    }

                                    if (context!!.addProducts.isNullOrEmpty()) {
                                        binding.rvAllProduct.visibility = View.GONE
                                        binding.linearFound.visibility = View.VISIBLE
                                        binding.relSave.visibility = View.GONE
                                    }
                                }

                            })


                    } else {

                        if (context!!.maxQuantity.toInt() < (item as String).toInt()) {
                            CallDialog.errorDialog(
                                activity!!,
                                getString(R.string.a_lbl_warning_title),
                                getString(R.string.a_msg_delete),
                                getString(R.string.a_btn_yes),
                                getString(R.string.a_btn_no),
                                getString(R.string.action_delete), null
                            )
                        } else {
                            val allProducts = AddProductModel()
                            allProducts.qty = item
                            allProducts.upcName = context!!.addProducts[position].upcName
                            allProducts.upcNumber = context!!.addProducts[position].upcNumber
                            context!!.addProducts.set(position, allProducts)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            })
        binding.rvAllProduct.adapter = adapter
    }

    private fun handleClick() {

        binding.btnSubmit.setOnClickListener {
            context!!.callSubmitApi(resources.getString(R.string.action_submit))
        }
        binding.btnDraft.setOnClickListener {
            context!!.callSubmitApi(resources.getString(R.string.action_draft))

        }

    }


}