package com.ng.printtag.allrequest

import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.*
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.common.DateRangePickerFragment
import com.ng.printtag.databinding.ActivityAllRequestsBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.allrequests.AllRequestModel
import com.ng.printtag.models.newrequests.AddProductModel
import org.json.JSONObject
import retrofit2.Response


class ActivityAllRequests : BaseActivity<ActivityAllRequestsBinding>(),
    DateRangePickerFragment.OnDateRangeSelectedListener, OnItemClickListener {

    private lateinit var binding: ActivityAllRequestsBinding
    var allRequest: MutableList<AllRequestModel.Data.Records>? = null
    var products: ArrayList<AddProductModel>? = null
    private val dateRangePickerFragment = DateRangePickerFragment()
    var action: String = ""
    var searchKey = ""
    var dateRange = ""
    private var page = 1
    lateinit var adapter: AllRequestsAdapter
    var totalPage: Int = 0

    companion object {
        var isLoading = false
        var isLastPage = false
    }

    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        products = ArrayList()
        actBaseBinding.rlMain.removeView(actBaseBinding.headerToolBar)
        action = intent.getStringExtra(getString(R.string.action_from))
        dateRangePickerFragment.newInstance(this@ActivityAllRequests, false)
        dateRangePickerFragment.setOnDateRangeSelectedListener(this@ActivityAllRequests)
        if (action == getString(R.string.action_from_all)) {
            binding.tvHeaderTitle.text = getString(R.string.a_lbl_all_requests)
        } else {
            binding.tvHeaderTitle.text = getString(R.string.a_lbl_pending_requests)
        }
        loadData()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.rvAllRequests.layoutManager = linearLayoutManager


        handleClick()
        setSearchView()
        binding.rvAllRequests.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun loadMoreItems() {
                isLoading = true
                if (!isLastPage) {
                    Handler().postDelayed({
                        loadData()
                    }, 200)
                }
            }
        })
    }

    private fun loadData() {
        if (action == getString(R.string.action_from_all)) {
            callAllRequestApi(resources.getString(R.string.value_all), page.toString(), searchKey, dateRange)
        } else {
            callAllRequestApi(resources.getString(R.string.value_pending), page.toString(), searchKey, dateRange)
        }
    }

    private fun setSearchView() {
        val id = binding.searchView.getContext()
            .resources
            .getIdentifier("android:id/search_src_text", null, null)
        val textView = binding.searchView.findViewById(id) as EditText
        textView.setTextColor(Color.WHITE)
        textView.hint = getString(R.string.a_hint_search)
        textView.setHintTextColor(Color.WHITE)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                AppUtils.hideKeyBoard(this@ActivityAllRequests)
                searchKey = query
                dateRange = ""
                page = 1
                if (action == getString(R.string.action_from_all)) {
                    binding.tvHeaderTitle.text = getString(R.string.a_lbl_all_requests)

                } else {
                    binding.tvHeaderTitle.text = getString(R.string.a_lbl_pending_requests)
                }
                allRequest!!.clear()
                loadData()
                binding.searchView.clearFocus()
                return false
            }

        })


        binding.searchView.setOnSearchClickListener {
            binding.ivCalendar.visibility = GONE
            binding.tvHeaderTitle.visibility = GONE
        }
        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                binding.ivCalendar.visibility = VISIBLE
                binding.tvHeaderTitle.visibility = VISIBLE
                searchKey = ""
                dateRange = ""
                page = 1
                allRequest!!.clear()
                loadData()


                return false
            }

        })

    }

    private fun callAllRequestApi(status: String, page: String, searchKey: String, dateRange: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(this@ActivityAllRequests, true, "")

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityAllRequests).data!!.userId
        )
        rootJson.put(resources.getString(R.string.key_status), status)
        rootJson.put(resources.getString(R.string.key_page), page)
        rootJson.put(resources.getString(R.string.key_searchkey), searchKey)
        rootJson.put(resources.getString(R.string.key_date_range), dateRange)
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityAllRequests,
            body,
            Constant.CALL_ALL_REQUEST,
            this,
            restClientModel
        )
    }

    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            Constant.CALL_ALL_REQUEST -> {
                ProgressDialog.displayProgressDialog(this@ActivityAllRequests, false, "")

                val rootResponse = response.body() as AllRequestModel
                when (rootResponse.success) {
                    true -> {

                        if (rootResponse.data!!.records != null) {

                            val tempList = rootResponse.data!!.records!! as ArrayList<AllRequestModel.Data.Records>
                            if (tempList.size == 0 || tempList.isEmpty() && page == 1) {
                                binding.tvMsg.visibility = VISIBLE
                                binding.tvMsg.text = rootResponse.data!!.recordsMsg
                                binding.rvAllRequests.visibility = GONE
                            } else {
                                totalPage = rootResponse.data!!.totalPages!!.toInt()
                                if (allRequest.isNullOrEmpty()) {
                                    allRequest = tempList
                                    adapter = AllRequestsAdapter(allRequest!!, this)
                                    binding.rvAllRequests.adapter = adapter

                                } else {
                                    allRequest!!.addAll(tempList)
                                    isLoading = false
                                    adapter.notifyDataSetChanged()

                                }
                                if (page == totalPage) {
                                    isLastPage = true
                                } else {
                                    page += 1
                                }
                                binding.tvMsg.visibility = GONE
                                binding.rvAllRequests.visibility = VISIBLE

                            }
                        } else {
                            binding.tvMsg.visibility = VISIBLE
                            binding.tvMsg.text = rootResponse.data!!.recordsMsg
                            binding.rvAllRequests.visibility = GONE
                        }
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
            this@ActivityAllRequests,
            title,
            message,
            "",
            getString(R.string.a_btn_ok),
            "", null
        )

    }

    private fun handleClick() {
        binding.ivBack.setOnClickListener {
            isLoading = false
            isLastPage = false
            onBackPressed()
        }
        binding.ivCalendar.setOnClickListener {

            dateRangePickerFragment.show(supportFragmentManager, "datePicker")
        }
    }

    override fun onDateRangeSelected(
        startDay: Int,
        startMonth: Int,
        startYear: Int,
        endDay: Int,
        endMonth: Int,
        endYear: Int
    ) {
        val selectedDate =
            startMonth.toString() + "/" + startDay + "/" + startYear + "-" + endMonth + "/" + endDay + "/" + endYear
        Log.d("selectedDate", selectedDate)
        searchKey = ""
        dateRange = selectedDate
        page = 1
        allRequest!!.clear()
        loadData()
    }

    override fun onItemClick(item: Any, position: Int) {

        Utils.navigateToNewPrintRequest(this@ActivityAllRequests, allRequest!!.get(position))
    }


    override fun onResume() {
        super.onResume()
        if (!allRequest.isNullOrEmpty()) {
            allRequest!!.clear()
            page = 1
            searchKey = ""
            dateRange = ""
            loadData()

        }
    }
    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_all_requests
}