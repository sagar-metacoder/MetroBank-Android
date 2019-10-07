package com.ng.printtag.login

import android.view.View
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.api.ApiResponseListener
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityLoginBinding
import com.ng.printtag.interfaces.HeaderInterface
import com.ng.printtag.models.login.LoginModel
import kotlinx.android.synthetic.main.activity_login.*


class ActivityLogin : BaseActivity<ActivityLoginBinding>(), ApiResponseListener, HeaderInterface {

    private lateinit var binding: ActivityLoginBinding
    var loginModel: LoginModel = LoginModel()

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

            is FragmentLogin ->
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

}