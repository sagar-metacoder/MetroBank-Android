package com.ng.printtag.printrequest

import com.ng.printtag.R
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentAddProductBinding
import com.ng.printtag.models.allrequests.AllRequestModel

class FragmentAddProduct : BaseFragment<FragmentAddProductBinding>() {

    private lateinit var binding: FragmentAddProductBinding
    lateinit var allRequest: MutableList<AllRequestModel.Data.Records>


    override fun initFragment() {
        binding = getFragmentDataBinding()

        handleClick()
        //  setAdapter()

    }

    override fun getLayoutId(): Int = R.layout.fragment_add_product


    private fun setAdapter() {
        /*  val adapter = AllRequestsAdapter(
              allRequest,
              object : OnItemClickListener {
                  override fun onItemClick(item: Any, position: Int) {

                  }
              })
          binding.rvAllProduct.adapter = adapter*/
    }

    private fun handleClick() {

    }


}