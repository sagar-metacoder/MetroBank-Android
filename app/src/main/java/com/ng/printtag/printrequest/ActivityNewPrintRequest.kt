package com.ng.printtag.printrequest

import android.text.Editable
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.api.RequestMethods
import com.ng.printtag.api.RestClient
import com.ng.printtag.api.RestClientModel
import com.ng.printtag.apputils.*
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityNewPrintRequestBinding
import com.ng.printtag.interfaces.HeaderInterface
import com.ng.printtag.models.newrequests.*
import com.symbol.emdk.EMDKManager
import com.symbol.emdk.EMDKManager.EMDKListener
import com.symbol.emdk.EMDKManager.FEATURE_TYPE
import com.symbol.emdk.EMDKResults
import com.symbol.emdk.barcode.*
import com.symbol.emdk.barcode.BarcodeManager.ConnectionState
import com.symbol.emdk.barcode.BarcodeManager.ScannerConnectionListener
import com.symbol.emdk.barcode.Scanner.*
import com.symbol.emdk.barcode.StatusData.ScannerStates
import kotlinx.android.synthetic.main.activity_new_print_request.*
import org.apache.commons.lang3.StringUtils
import org.json.JSONObject
import retrofit2.Response

class ActivityNewPrintRequest : BaseActivity<ActivityNewPrintRequestBinding>(), HeaderInterface,
    EMDKListener, DataListener, StatusListener, ScannerConnectionListener {


    private lateinit var binding: ActivityNewPrintRequestBinding
    var arrayStoreKey: ArrayList<String> = ArrayList()
    var arrayStoreValue: ArrayList<String> = ArrayList()
    var arrayDeptKey: ArrayList<String> = ArrayList()
    var arrayDeptValue: ArrayList<String> = ArrayList()
    lateinit var addProducts: MutableList<AddProductModel>

    lateinit var arrayTemplate: MutableList<DepartmentModel.Data.Template>
    var storeKey: String = ""
    var tagType: String = ""
    var departmentKey: String = ""
    var productInfo: String = ""
    var effectiveDate: String = ""
    var upcBarcode: String = ""

    var template_postion: Int = 0
    private var emdkManager: EMDKManager? = null
    private var barcodeManager: BarcodeManager? = null
    private var scanner: Scanner? = null
    private var deviceList: List<ScannerInfo>? = null
    private var scannerIndex = 0
    private var statusString = ""
    private var bSoftTriggerSelected = false
    private var bDecoderSettingsChanged = false
    private var bExtScannerDisconnected = false
    private val lock = Any()
    private val position = 1



    override fun initMethod() {
        binding = getViewDataBinding()
        deviceList = ArrayList()
        addProducts = ArrayList()

        actBaseBinding.headerToolBar.setHeaderInterface(this)

        val results = EMDKManager.getEMDKManager(applicationContext, this)
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            return
        }


        addSpinnerScannerDevicesListener()

    }

    fun callUpcMatchApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, true, "")

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        rootJson.put(resources.getString(R.string.department), departmentKey)
        rootJson.put(resources.getString(R.string.key_upc), upcBarcode)

        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_UPC_VALIDATE,
            this,
            restClientModel
        )
    }



    fun callStoreApi() {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, true, "")

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId), AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_STORE_URL,
            this,
            restClientModel
        )
    }

    fun callDepartmentApi(tagType: String, storeKey: String, department: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, true, "")

        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
//        rootJson.put(resources.getString(R.string.tagType), binding.edtTagType.text.toString())
        rootJson.put(resources.getString(R.string.tagType), tagType)
        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(resources.getString(R.string.department), department)
//        rootJson.put(resources.getString(R.string.storeNumber), storeKey)

        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_DEPARTMENT_URL,
            this,
            restClientModel
        )
    }

    fun callTemplateDetails(position: Int) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true
        ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, true, "")

        template_postion = position
        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        rootJson.put(resources.getString(R.string.key_templateId), arrayTemplate.get(position).id)
        rootJson.put(resources.getString(R.string.tagType), tagType)

        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(
            resources.getString(R.string.department), departmentKey
        )
        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_TEMPLETS_DETAILS,
            this,
            restClientModel
        )
    }

    fun callSubmitApi(action: String) {
        val restClientModel = RestClientModel()
        restClientModel.isProgressDialogShow = true


        val rootJson = JSONObject()
        rootJson.put(
            resources.getString(R.string.userId),
            AppUtils.getUserModel(this@ActivityNewPrintRequest).data!!.userId
        )
        rootJson.put(resources.getString(R.string.key_templateId), arrayTemplate.get(template_postion).id)
        rootJson.put(resources.getString(R.string.tagType), tagType)

        rootJson.put(resources.getString(R.string.storeNumber), storeKey)
        rootJson.put(resources.getString(R.string.department), departmentKey)
        rootJson.put(resources.getString(R.string.key_effectiveDate), effectiveDate)

        rootJson.put(
            resources.getString(R.string.key_language),
            BaseSharedPreference.getInstance(this@ActivityNewPrintRequest).getLanguage(
                resources.getString(R.string.pref_language)
            )
        )
        rootJson.put(resources.getString(R.string.key_action), action)
        rootJson.put(resources.getString(R.string.key_aisleInfo), productInfo)


        val body = RequestMethods.getRequestBody(rootJson)

        RestClient().apiRequest(
            this@ActivityNewPrintRequest,
            body,
            Constant.CALL_NEW_REQUEST_SUBMIT,
            this,
            restClientModel
        )
    }


    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
        super.onApiResponse(response, reqCode)
        when (reqCode) {
            Constant.CALL_STORE_URL -> {
                ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")

                val rootResponse = response.body() as StoreListModel
                when (rootResponse.success) {
                    true -> {
                        // val jsonObj = JSONObject(jsonObjectStore)
                        for (i in 0 until rootResponse.data!!.stores!!.size) {

                            arrayStoreKey.add(rootResponse.data!!.stores?.get(i)?.key!!)
                            arrayStoreValue.add(rootResponse.data!!.stores?.get(i)?.value!!)
                        }
                        if (!arrayStoreKey.isNullOrEmpty())
                            if (arrayStoreKey.size == 1) {
                                val currentFragment = getCurrentFragment()
                                //                                    binding.edtStoreNo.text = Editable.Factory.getInstance().newEditable(arrayStoreValue[0])
                                if (currentFragment != null && currentFragment is FragmentNewPrintRequest)
                                    currentFragment.binding.edtStoreNo.text =
                                        Editable.Factory.getInstance().newEditable(arrayStoreKey[0])
                                storeKey = arrayStoreValue[0]

                                for (i in 0 until rootResponse.data!!.departments!!.size) {

                                    arrayDeptKey.add(rootResponse.data!!.departments?.get(i)?.key!!)
                                    arrayDeptValue.add(rootResponse.data!!.departments?.get(i)?.value!!)
                                }
                            }

                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }

            Constant.CALL_DEPARTMENT_URL -> {
                ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")

                val rootResponse = response.body() as DepartmentModel
                when (rootResponse.success) {
                    true -> {
                        arrayDeptKey = ArrayList()
                        arrayDeptValue = ArrayList()
                        arrayTemplate = ArrayList()

                        for (i in 0 until rootResponse.data!!.departments!!.size) {

                            arrayDeptKey.add(rootResponse.data!!.departments?.get(i)?.key!!)
                            arrayDeptValue.add(rootResponse.data!!.departments?.get(i)?.value!!)
                        }
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentNewPrintRequest) {
                            if (!arrayDeptKey.isNullOrEmpty() && !currentFragment.isDeptSelected)
                                currentFragment.callDepartmentDialog()

                        }
                        if (!rootResponse.data!!.templates.isNullOrEmpty()) {
                            if (rootResponse.data!!.templates!!.size >= 1 && currentFragment is FragmentNewPrintRequest) {
                                currentFragment.binding.linearRv.visibility = View.VISIBLE
                                currentFragment.setAdapter(rootResponse.data!!.templates)
                                arrayTemplate =
                                    rootResponse.data!!.templates as ArrayList<DepartmentModel.Data.Template>
                                currentFragment.isDeptSelected = false
                            }
                        }
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }

            Constant.CALL_TEMPLETS_DETAILS -> {
                ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")


                val rootResponse = response.body() as TempletListModel
                when (rootResponse.success) {
                    true -> {
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentNewPrintRequest) {

                            currentFragment.binding.linearTemplateData.visibility = View.VISIBLE
                            currentFragment.binding.model = rootResponse.data!!.templateDetails


                        }
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.data!!.msg!!)
                    }
                }
            }
            Constant.CALL_UPC_VALIDATE -> {
                ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")


                val rootResponse = response.body() as UpcValidateModel
                when (rootResponse.success) {
                    true -> {
                        val currentFragment = getCurrentFragment()
                        if (currentFragment != null && currentFragment is FragmentAddProduct) {

                            val allProducts = AddProductModel()
                            allProducts.qty = "1"
                            allProducts.upcName = rootResponse.data!!.description
                            allProducts.upcNumber = upcBarcode
                            addProducts.add(allProducts)
                            currentFragment.adapter.notifyDataSetChanged()

                        }
                    }
                    false -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.msg!!)
                    }
                }
            }



            Constant.CALL_NEW_REQUEST_SUBMIT -> {
                ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")

                val rootResponse = response.body() as NewPrintReqSubmit
                when (rootResponse.success) {
                    true -> {

                        AppUtils.showLongToast(
                            this@ActivityNewPrintRequest,
                            resources.getString(R.string.a_msg_product_added)
                        )
                        Utils.gotoHomeScreen(this@ActivityNewPrintRequest)
                    }
                    else -> {
                        showError(getString(R.string.a_lbl_server_title), rootResponse.data!!.msg!!)
                    }
                }
            }


        }
    }

    override fun onApiError(response: Any, reqCode: Int) {
        super.onApiError(response, reqCode)
        ProgressDialog.displayProgressDialog(this@ActivityNewPrintRequest, false, "")

    }

    fun getCurrentFragment(): Fragment? {
        return try {
            navigation_new_print_request.childFragmentManager.findFragmentById(R.id.navigation_new_print_request)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun showError(title: String, message: String) {
        CallDialog.errorDialog(
            this@ActivityNewPrintRequest,
            title,
            message,
            "",
            getString(R.string.a_btn_ok),
            "", null
        )

    }

    override fun onRightImageClick() {

    }

    override fun onLeftImageClick() {
        super.onBackPressed()
    }

    override fun onMenuImageClick(item: String) {
    }

    override fun onHeaderMenuItemClick(view: View) {

        if (view.id == R.id.iv_bar_code) {
            bSoftTriggerSelected = true
            cancelRead()
        }
    }

    override fun onOpened(emdkManager: EMDKManager) {
        updateStatus("EMDK open success!")
        this.emdkManager = emdkManager
        // Acquire the barcode manager resources
        initBarcodeManager()
        // Enumerate scanner devices
        enumerateScannerDevices()
        // Set default scanner
        //spinnerScannerDevices.setSelection(defaultIndex);
    }

    override fun onResume() {
        super.onResume()
        // The application is in foreground
        if (emdkManager != null) {
            // Acquire the barcode manager resources
            initBarcodeManager()
            // Enumerate scanner devices
            enumerateScannerDevices()
            // Set selected scanner
            // spinnerScannerDevices.setSelection(scannerIndex);
            // Initialize scanner
            initScanner()
        }
    }

    override fun onPause() {
        super.onPause()
        // The application is in background
        // Release the barcode manager resources
        deInitScanner()
        deInitBarcodeManager()
    }

    override fun onClosed() {
        // Release all the resources
        if (emdkManager != null) {
            emdkManager!!.release()
            emdkManager = null
        }
        updateStatus(getString(R.string.a_msg_emdk))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release all the resources
        if (emdkManager != null) {
            emdkManager!!.release()
            emdkManager = null
        }
    }

    override fun onData(scanDataCollection: ScanDataCollection?) {
        if (scanDataCollection != null && scanDataCollection.result == ScannerResults.SUCCESS) {
            val scanData = scanDataCollection.scanData
            for (data in scanData) {
                upcBarcode = data.data
                Log.v("upcBarcode", upcBarcode)


                if (data.data.length != 13) {
                    upcBarcode = StringUtils.leftPad(upcBarcode, 13, '0')
                    Log.v("upcBarcode", upcBarcode)


                }
                callUpcMatchApi()
            }
        }
    }

    override fun onStatus(statusData: StatusData) {
        val state = statusData.state
        when (state) {
            ScannerStates.IDLE -> {
                statusString = statusData.friendlyName + getString(R.string.a_msg_idle)
                updateStatus(statusString)
                // set trigger type
                if (bSoftTriggerSelected) {
                    scanner!!.triggerType = TriggerType.SOFT_ONCE
                    bSoftTriggerSelected = false
                } else {
                    scanner!!.triggerType = TriggerType.HARD
                }
                // set decoders
                if (bDecoderSettingsChanged) {
                    setDecoders()
                    bDecoderSettingsChanged = false
                }
                // submit read
                if (!scanner!!.isReadPending() && !bExtScannerDisconnected) {
                    try {
                        scanner!!.read()
                    } catch (e: ScannerException) {
                        e.message?.let { updateStatus(it) }
                    }

                }
            }
            ScannerStates.WAITING -> {
                statusString = getString(R.string.a_msg_scanner_waiting)
                updateStatus(statusString)
            }
            ScannerStates.SCANNING -> {
                statusString = getString(R.string.a_msg_scanning)
                updateStatus(statusString)
            }
            ScannerStates.DISABLED -> {
                statusString = statusData.friendlyName + getString(R.string.a_msg_disable)
                updateStatus(statusString)
            }
            ScannerStates.ERROR -> {
                statusString = getString(R.string.a_msg_error)
                updateStatus(statusString)
            }
            else -> {
            }
        }
    }

    override fun onConnectionChange(scannerInfo: ScannerInfo, connectionState: ConnectionState) {
        val status: String
        var scannerName = ""
        val statusExtScanner = connectionState.toString()
        val scannerNameExtScanner = scannerInfo.friendlyName
        if (deviceList!!.size != 0) {
            scannerName = deviceList!!.get(scannerIndex).friendlyName
        }
        if (scannerName.equals(scannerNameExtScanner, ignoreCase = true)) {
            when (connectionState) {
                ConnectionState.CONNECTED -> {
                    bSoftTriggerSelected = false
                    synchronized(lock) {
                        initScanner()
                        bExtScannerDisconnected = false
                    }
                }
                ConnectionState.DISCONNECTED -> {
                    bExtScannerDisconnected = true
                    synchronized(lock) {
                        deInitScanner()
                    }
                }
            }
            status = "$scannerNameExtScanner:$statusExtScanner"
            updateStatus(status)
        } else {
            bExtScannerDisconnected = false
            status = "$statusString $scannerNameExtScanner:$statusExtScanner"
            updateStatus(status)
        }
    }

    private fun initScanner() {
        if (scanner == null) {
            if (deviceList != null && deviceList!!.size != 0) {
                if (barcodeManager != null)
                    scanner = barcodeManager!!.getDevice(deviceList!!.get(scannerIndex))
            } else {
                return
            }
            if (scanner != null) {
                scanner!!.addDataListener(this)
                scanner!!.addStatusListener(this)
                try {
                    scanner!!.enable()
                } catch (e: ScannerException) {
                    e.message?.let { updateStatus(it) }
                    deInitScanner()
                }

            } else {
            }
        }
    }

    private fun deInitScanner() {
        if (scanner != null) {
            try {
                scanner!!.disable()
            } catch (e: Exception) {
                e.message?.let { updateStatus(it) }
            }

            try {
                scanner!!.removeDataListener(this)
                scanner!!.removeStatusListener(this)
            } catch (e: Exception) {
                e.message?.let { updateStatus(it) }
            }

            try {
                scanner!!.release()
            } catch (e: Exception) {
                e.message?.let { updateStatus(it) }
            }

            scanner = null
        }
    }

    private fun initBarcodeManager() {
        barcodeManager = emdkManager!!.getInstance(FEATURE_TYPE.BARCODE) as BarcodeManager
        // Add connection listener
        if (barcodeManager != null) {
            barcodeManager!!.addConnectionListener(this)
        }
    }

    private fun deInitBarcodeManager() {
        if (emdkManager != null) {
            emdkManager!!.release(FEATURE_TYPE.BARCODE)
        }
    }

    private fun addSpinnerScannerDevicesListener() {
        if (scannerIndex != position || scanner == null) {
            scannerIndex = position
            bSoftTriggerSelected = false
            bExtScannerDisconnected = false
            deInitScanner()
            initScanner()
        }

    }

    private fun enumerateScannerDevices() {
        if (barcodeManager != null) {
            deviceList = barcodeManager!!.getSupportedDevicesInfo()

            if (scannerIndex != position || scanner == null) {
                scannerIndex = position
                bSoftTriggerSelected = false
                bExtScannerDisconnected = false
                deInitScanner()
                initScanner()
            }
        }
    }

    private fun setDecoders() {
        if (scanner != null) {
            try {
                val config = scanner!!.getConfig()
                // Set EAN8
                config.decoderParams.ean8.enabled = true
                // Set EAN13
                config.decoderParams.ean13.enabled = true
                // Set Code39
                config.decoderParams.code39.enabled = true
                //Set Code128
                config.decoderParams.code128.enabled = true
                scanner!!.setConfig(config)
            } catch (e: ScannerException) {
                e.message?.let { updateStatus(it) }
            }

        }
    }


    private fun cancelRead() {
        if (scanner != null) {
            if (scanner!!.isReadPending()) {
                try {
                    scanner!!.cancelRead()
                } catch (e: ScannerException) {
                    e.message?.let { updateStatus(it) }
                }

            }
        }
    }

    private fun updateStatus(status: String) {
        runOnUiThread {
            //textViewStatus.setText("" + status)
        }
    }



    override fun getLayoutId(): Int = R.layout.activity_new_print_request
}