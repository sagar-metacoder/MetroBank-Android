package com.ng.printtag.printrequest

import android.view.View
import com.ng.printtag.R
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddProductBinding
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.interfaces.OnItemClickListener

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

        /*  for (i in 0 until 5) {
              val allProducts = AddProductModel()
              allProducts.qty = "5"
              allProducts.upcName = "Food Cans"
              allProducts.upcNumber = "00000023443"
              allRequest.add(allProducts)
          }*/
        handleClick()
        setAdapter()

    }

    override fun getLayoutId(): Int = R.layout.fragment_add_product


    fun setAdapter() {

        adapter = AddProductAdapter(
            context!!.addProducts,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                    if (item.equals("delete")) {

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