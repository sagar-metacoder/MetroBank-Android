package com.ng.printtag.printrequest

import com.ng.printtag.R
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddProductBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.newrequests.AddProductAdapter
import com.ng.printtag.models.newrequests.AddProductModel

class FragmentAddProduct : BaseFragment<FragmentAddProductBinding>() {

    private lateinit var binding: FragmentAddProductBinding
    lateinit var allRequest: MutableList<AddProductModel>


    override fun initFragment() {
        binding = getFragmentDataBinding()

        allRequest = ArrayList()

        for (i in 0 until 5) {
            val allProducts = AddProductModel()
            allProducts.qty = "5"
            allProducts.upcName = "Food Cans"
            allProducts.upcNumber = "00000023443"
            allRequest.add(allProducts)
        }
        handleClick()
        setAdapter()

    }

    override fun getLayoutId(): Int = R.layout.fragment_add_product


    private fun setAdapter() {
        val adapter = AddProductAdapter(
            allRequest,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                }
            })
        binding.rvAllProduct.adapter = adapter
    }

    private fun handleClick() {

    }


}