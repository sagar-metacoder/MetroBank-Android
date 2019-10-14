package com.ng.printtag.allrequest

import android.graphics.Color
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.common.DateRangePickerFragment
import com.ng.printtag.databinding.ActivityAllRequestsBinding
import com.ng.printtag.interfaces.OnItemClickListener
import com.ng.printtag.models.allrequests.AllRequestModel
import org.json.JSONObject
import retrofit2.Response
import java.util.*


class ActivityAllRequests : BaseActivity<ActivityAllRequestsBinding>(),
    DateRangePickerFragment.OnDateRangeSelectedListener {


    private lateinit var binding: ActivityAllRequestsBinding
    lateinit var allRequest: MutableList<AllRequestModel.Data.Records>
    private val dateRangePickerFragment = DateRangePickerFragment()
    var action: String = ""


    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        actBaseBinding.rlMain.removeView(actBaseBinding.headerToolBar)
        allRequest = ArrayList()
        action = intent.getStringExtra(getString(R.string.action_from))
        dateRangePickerFragment.newInstance(this@ActivityAllRequests, false)
        dateRangePickerFragment.setOnDateRangeSelectedListener(this@ActivityAllRequests)
        if (action == getString(R.string.action_from_all)) {
            binding.tvHeaderTitle.text = getString(R.string.a_lbl_all_requests)
            callAllRequestApi(resources.getString(R.string.value_all), "1", "", "")
        } else {
            binding.tvHeaderTitle.text = getString(R.string.a_lbl_pending_requests)
            callAllRequestApi(resources.getString(R.string.value_pending), "1", "", "")
        }


        handleClick()
        setSearchView()

    }

    private fun setAdapter() {
        val adapter = AllRequestsAdapter(
            this@ActivityAllRequests,
            allRequest,
            object : OnItemClickListener {
                override fun onItemClick(item: Any, position: Int) {

                }
            })
        binding.rvAllRequests.adapter = adapter
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
                if (action == getString(R.string.action_from_all)) {
                    binding.tvHeaderTitle.text = getString(R.string.a_lbl_all_requests)
                    callAllRequestApi(resources.getString(R.string.value_all), "1", query, "")
                } else {
                    binding.tvHeaderTitle.text = getString(R.string.a_lbl_pending_requests)
                    callAllRequestApi(resources.getString(R.string.value_pending), "1", query, "")
                }
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
                if (action == getString(R.string.action_from_all)) {
                    callAllRequestApi(resources.getString(R.string.value_all), "1", "", "")
                } else {
                    callAllRequestApi(resources.getString(R.string.value_pending), "1", "", "")
                }


                return false
            }

        })

    }

    private fun callAllRequestApi(status: String, page: String, searchKey: String, dateRange: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
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
                val rootResponse = response.body() as AllRequestModel
                when (rootResponse.success) {
                    true -> {
                        allRequest = rootResponse.data!!.records!! as ArrayList<AllRequestModel.Data.Records>
                        if (allRequest.size == 0 || allRequest.isEmpty()) {
                            binding.tvMsg.visibility = VISIBLE
                            binding.tvMsg.text = rootResponse.data!!.recordsMsg
                            binding.rvAllRequests.visibility = GONE
                        } else {
                            binding.tvMsg.visibility = GONE
                            binding.rvAllRequests.visibility = VISIBLE
                            setAdapter()
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

        if (action == getString(R.string.action_from_all)) {
            callAllRequestApi(resources.getString(R.string.value_all), "1", "", selectedDate)
        } else {
            callAllRequestApi(resources.getString(R.string.value_pending), "1", "", selectedDate)
        }
    }


    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_all_requests
}