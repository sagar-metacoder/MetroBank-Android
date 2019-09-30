package com.ng.printtag.login

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.interfaces.HeaderInterface
import ng.pdp.api.ApiResponseListener
import ng.pdp.login.FragmentPassword
import ng.pdp.login.FragmentUsername

class ActivityLogin : BaseActivity<ActivityLoginBinding>(), ApiResponseListener, HeaderInterface,
   {

    private lateinit var binding: ActivityLoginBinding
    var loginModel: LoginModel = LoginModel()
    private lateinit var mLocationProvider: LocationProvider
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var isApiCall: Boolean = false
    private lateinit var permissions: Array<String>
    var storeModels: List<StoreModels>? = null
    private var authenticationContext: AuthenticationContext? = null
    private lateinit var accessToken: String
    var storeModel: StoreModels? = null
    /**
     * Init components
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        actBaseBinding.headerToolBar.setHeaderInterface(this)
        localUserData()
    }

    private fun localUserData(): LoginModel {
        loginModel = LoginModel()
        return loginModel
    }

    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onRightImageClick() {
    }

    override fun onLeftImageClick() {
        onBackPressed()
    }

    override fun onMenuImageClick(item: String) {
        val fragment = getCurrentFragment()
        fragment.onDetach()
        fragment.onAttach(this)
        when (fragment) {
            is FragmentUsername ->
                fragment.setLabel()
            is FragmentPassword ->
                fragment.setLabel()
            is FragmentStoreNumber ->
                fragment.setLabel()
        }
    }

    override fun onHeaderMenuItemClick(view: View) {

    }

    private fun getCurrentFragment(): Fragment {
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_login)
        //return  navHostFragment?.childFragmentManager?.fragments!![0]
        return navigation_login.childFragmentManager.findFragmentById(R.id.navigation_login)!!
    }


    private fun showError(title: String, message: String) {
        CallDialog.errorDialog(
            this@ActivityLogin,
            title,
            message,
            "",
            Utils.getLabel(getString(R.string.a_btn_ok)),
            "", null
        )

    }


}