package com.ng.printtag.allrequest

import com.ng.printtag.R
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityAllRequestsBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.allrequests.AllRequestModel

class AllRequests : BaseActivity<ActivityAllRequestsBinding>() {


    private lateinit var binding: ActivityAllRequestsBinding
    lateinit var allRequest: MutableList<AllRequestModel>

    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        actBaseBinding.rlMain.removeView(actBaseBinding.headerToolBar)
        allRequest = ArrayList()
        setData()


        // (binding.liToolBar.getChildAt(il) as HeaderToolBar).setHeaderInterface(this)
        //binding.manageSlideMenu.setCallBackInterfaces(this@AllRequests)


        val adapterDisburse = AllRequestsAdapter(
            this@AllRequests,
            allRequest,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {
                    //context!!.disbursePosition = (position + 1).toString()
                    // context!!.disburseSize = context!!.transDisburseDetails.completedDisbursement!!.size.toString()

                    // Utils.navigateTo(navigation_transaction.view!!, R.id.actionTransDisbursement, null)
                }
            })
        binding.rvAllRequests.adapter = adapterDisburse

    }

    private fun setData() {
        val allRequestModel = AllRequestModel()
        allRequestModel.printType = "Fresh Tag"
        allRequestModel.templateName = "Schematic Tag"
        allRequestModel.store = "Store No.2"
        allRequestModel.department = "Beverage,Bakery"
        allRequestModel.expectedDate = "Sep 27,2019"
        allRequestModel.effectiveDate = "Sep 27,2019"
        allRequestModel.reqDate = "Sep 27,2019"
        allRequestModel.status = "Completed"

        for (i in 0 until 5) {
            allRequest.add(allRequestModel)
        }

    }

    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_all_requests
}