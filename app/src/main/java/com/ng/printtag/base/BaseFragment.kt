package com.ng.printtag.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.api.ApiResponseListener
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.Utils
import com.ng.printtag.login.FragmentLogin
import retrofit2.Response

/**
 * A [BaseFragment] class.
 * Activities that contain this fragment must implement the
 * [BaseFragment.FragmentCallBack] interface
 * to handle interaction events.
 */
abstract class BaseFragment<out T : ViewDataBinding> : Fragment(), View.OnClickListener, ApiResponseListener {

    private lateinit var fragmentChildBinding: T
    private var listener: FragmentCallBack? = null
    private var mActivity: BaseActivity<*>? = null
    private var mRootView: View? = null
    private var mLastClickTime: Long = 0

    /**
     * Called to do initial creation of a fragment. This is called after onAttach and before onCreateView.
     *
     * @param inflater           the LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState Bundle: If the fragment is being re-created from a previous saved state, this is the state.
     *                           This value may be null
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            Utils.setLocalForTheApp(
                activity!!,
                BaseSharedPreference.getInstance(activity!!).getLanguage(getString(R.string.pref_language))
            )

            fragmentChildBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
            mRootView = fragmentChildBinding.root
            initFragment()
            when (this) {
                is FragmentLogin ->
                    AppUtils.openKeyboardForceFully(activity!!)
                /* is FragmentStoreNumber ->
                     PdpUtils.openKeyboardForceFully(activity!!)
                 is FragmentLookUpPhoneLandLine ->
                     PdpUtils.openKeyboardForceFully(activity!!)
                 is FragmentLookUpByProspera ->
                     PdpUtils.openKeyboardForceFully(activity!!)*/



            }
        }
        return mRootView
    }

    /**
     * This method is respectively associates the Fragment with/from the Activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is BaseActivity<*> -> {
                val activity = context as BaseActivity<*>?
                this.mActivity = activity
            }
        }
    }

    /**
     * It will call when fragment will attach
     */
    protected abstract fun initFragment()

    /**
     * @return layout genericModel id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * This method is detaches the Fragment with/from the Activity
     */
    override fun onDetach() {
        mActivity = null
        listener = null
        super.onDetach()

    }

    /**
     * Get Binding Object
     */
    fun getFragmentDataBinding(): T = fragmentChildBinding

    /**
     * Click method of any view
     */
    override fun onClick(view: View) {
        if (mActivity != null) {
            AppUtils.hideSoftKeyboard(mActivity as Activity)
        }
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
    }

    /**
     * Fragment interface
     */
    interface FragmentCallBack {
        fun onFragmentAttached(tag: String)
        fun onFragmentDetached(tag: String)
    }

    /**
     * Override method of activity
     */
    override fun onResume() {
        super.onResume()
        (this.activity as BaseActivity<*>).setHeaderBase(this)
        /*when (this) {
            is FragmentInstructions ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentLogin ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, 0, "", VISIBLE, GONE)
            is FragmentStoreNumber ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, 0, "", VISIBLE, GONE)
            is FragmentDashboard ->
                (this.activity as BaseActivity<*>).setHeaderTitle(
                    VISIBLE,
                    R.mipmap.ic_hamburger_nav_white,
                    Utils.getLabel(getString(R.string.a_title_welcome)) + ", " + BaseSharedPreference.getInstance(
                        activity!!
                    ).getPrefValue(getString(R.string.pref_user_name))!!,
                    VISIBLE,
                    VISIBLE
                )
            is FragmentLookUpPhoneLandLine ->
                (this.activity as BaseActivity<*>).setHeaderTitle(
                    VISIBLE,
                    R.mipmap.ic_hamburger_nav_white,
                    Utils.getLabel(getString(R.string.a_title_welcome)) + ", " + BaseSharedPreference.getInstance(
                        activity!!
                    ).getPrefValue(getString(R.string.pref_user_name))!!,
                    VISIBLE,
                    VISIBLE
                )
            *//*   is FragmentLookUpPhoneLandLine -> {
                   (this.activity as BaseActivity<*>).setHeaderTitle(
                       VISIBLE,
                       R.mipmap.ic_hamburger_nav_white,
                       Utils.getLabel(getString(R.string.a_title_welcome)) + ", " + BaseSharedPreference.getInstance(
                           activity!!
                       ).getPrefValue(getString(R.string.pref_user_name))!!,
                       VISIBLE,
                       VISIBLE
                   )
               }*//*
            is FragmentLookUpByProspera ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentLookUpByName ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentLookUpResult ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentPhoneLandLine -> {
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            }
            is FragmentVerifyCode ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentEmail ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentIdTypeSelection -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            }
            is FragmentIdDetails ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentIdPreview -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            }
            *//* is FragmentIdCaptureInstruction -> {
                 (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
             }
             is FragmentIdBarCodeTutorial -> {
                 (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
             }*//*
            is FragmentPhysicalAddress ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentAddressDetail ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentAddressApartmentNo ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentPhotoTutorial -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            }
            is FragmentPhotoCapture -> {
                (this.activity as BaseActivity<*>).setNoStatusBar()
                (this.activity as BaseActivity<*>).setHeaderVisibility(GONE)
            }
            is FragmentPhotoPreview -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
            }
            is FragmentDigitalSignature ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is ActivityKairosMatchResults ->
                (this.activity as BaseActivity<*>).setHeaderTitle(GONE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentOfacMatchResults ->
                (this.activity as BaseActivity<*>).setHeaderTitle(GONE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentActiveCardActions ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)
            is FragmentActiveProsperaManual ->
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", VISIBLE, GONE)

            is FragmentDocList -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
            }
            is FragmentDocCapture -> {
                (this.activity as BaseActivity<*>).setNoStatusBar()
                (this.activity as BaseActivity<*>).setHeaderVisibility(GONE)
            }
            is FragmentDocPreview -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
            }
            is FragmentAuthorization -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderVisibility(GONE)
            }
            is FragmentCreateWaiver -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
            }
            is FragmentSignature -> {
                (this.activity as BaseActivity<*>).setStatusBar()
                (this.activity as BaseActivity<*>).setHeaderTitle(VISIBLE, R.mipmap.ic_back, "", GONE, GONE)
            }
        }*/

    }

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