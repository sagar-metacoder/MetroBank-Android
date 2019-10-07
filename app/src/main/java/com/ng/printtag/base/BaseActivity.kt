package com.ng.printtag.base

import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ng.printtag.R
import com.ng.printtag.api.ApiResponseListener
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.AppUtils.Companion.noStatusBar
import com.ng.printtag.databinding.ActivityBaseBinding

import retrofit2.Response

/**
 * This class is used for define all the common behaviour of activity
 * and have abstract methods, which you can override in your actual implementations.
 */
abstract class BaseActivity<out T : ViewDataBinding> : AppCompatActivity(), OnClickListener,
    BaseFragment.FragmentCallBack, ApiResponseListener {

    private lateinit var childBinding: T
    lateinit var actBaseBinding: ActivityBaseBinding
    private var mLastClickTime: Long = 0
    private var isValidScreen = true
    private lateinit var headerModel: HeaderModel

    /**
     * Call when class has been initialize
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        setHeader()
        setHeaderBaseActivity()
        performDataBinding()
        initMethod()

    }

    override fun onClick(view: View?) {
        AppUtils.hideSoftKeyboard(this)
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    override fun onFragmentAttached(tag: String) {
    }

    override fun onFragmentDetached(tag: String) {

    }

    /**
     * Get Binding Object
     */
    fun getViewDataBinding(): T = childBinding

    /**
     * It will call when activity will start to init component
     */
    protected abstract fun initMethod()

    private fun performDataBinding() {
        childBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this),
                getLayoutId(),
                actBaseBinding.frmBaseContainer,
                false
            )
        actBaseBinding.frmBaseContainer.addView(childBinding.root)
    }

    private fun setHeader() {
        /*  if (this is ActivitySplash) {
              setHeaderVisibility(GONE)
          } else*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setStatusBar()
                setHeaderVisibility(VISIBLE)
            }
    }

    /**
     * This method used for remove statusbar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setNoStatusBar() {
        noStatusBar(window)
    }

    /**
     * This method used for display statusbar
     */
    fun setStatusBar() {
        AppUtils.setStatusBar(window, this)
    }

    fun setHeaderBase(baseFragment: BaseFragment<*>) {
        headerModel = HeaderModel()
        headerModel.baseActivity = this
        headerModel.baseFragment = baseFragment
        headerModel.setHeaderValues()
    }

    private fun setHeaderBaseActivity() {
        headerModel = HeaderModel()
        headerModel.baseActivity = this
        headerModel.setHeaderValues()
    }

    fun setDashBoardHeaderIcon() {
    }

    /**
     * This method used for setup slide menu navigation drawer
     */
    fun setSlidMenuHeader(backVisibility: Int, title: String) {
        actBaseBinding.headerToolBar.binding.ivBarCode.visibility = GONE
        actBaseBinding.headerToolBar.binding.tvHeaderTitle.visibility = GONE
        actBaseBinding.headerToolBar.binding.ivBack.setCompoundDrawablesWithIntrinsicBounds(
            R.mipmap.ic_back,
            0,
            0,
            0
        )
        actBaseBinding.headerToolBar.binding.ivBack.visibility = backVisibility
        //actBaseBinding.headerToolBar.binding.ivSettings.visibility = GONE
        if (title.isNotEmpty()) {
            actBaseBinding.headerToolBar.binding.ivBack.text = title
        }
    }

    /**
     * Header toolbar VISIBILITY true or false
     *
     * @param toolBarVisibility which use to display visibility
     */
    fun setHeaderVisibility(toolBarVisibility: Int) {
        actBaseBinding.headerToolBar.visibility = toolBarVisibility
    }

    /**
     * Used for display timeout if user not interact with screen
     */
    /*override fun onUserInteraction() {
        super.onUserInteraction()
        isValidScreen = true
        when (this) {
            is ActivityLogin -> {
                val fragment = navigation_login.childFragmentManager.findFragmentById(R.id.navigation_login)
                if (fragment != null && fragment is FragmentUsername) {
                    isValidScreen = false
                }
            }
            is ActivityAppSessionTimeout -> isValidScreen = false
        }
        if (PdpApplication.applicationInstance.countDownTimer != null) {
            PdpApplication.applicationInstance.countDownTimer!!.cancel()
        }
        if (isValidScreen) {
            PdpApplication.applicationInstance.countDownTimer = object : CountDownTimer(300000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    BaseSharedPreference.getInstance(this@BaseActivity)
                        .putValue(getString(R.string.pref_session_out), true)
                    AppUtils.hideKeyBoard(this@BaseActivity)
                    isValidScreen = false
                    AppUtils.navigateToOtherScreen(this@BaseActivity, ActivityAppSessionTimeout::class.java, true)
                }
            }
            PdpApplication.applicationInstance.countDownTimer!!.start()
        }
    }*/

    /**
     * @return layout genericModel id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * @see ApiResponseListener interface
     */
    @Throws(Exception::class)
    override fun onApiResponse(response: Response<Any>, reqCode: Int) {
    }

    /**
     * @see ApiResponseListener interface
     */
    @Throws(Exception::class)
    override fun onApiError(response: Any, reqCode: Int) {
    }

    /**
     * @see ApiResponseListener interface
     */
    @Throws(Exception::class)
    override fun onApiNetwork(response: Any, reqCode: Int) {
    }
}
