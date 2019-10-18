@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.ng.printtag.dashboard.slidemenu

import android.view.View
import com.ng.printtag.R
import com.ng.printtag.apputils.custom.CustomWebViewClient
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.base.BaseFragment
import com.ng.printtag.databinding.FragmentSlideMenuOptionsBinding
import com.ng.printtag.interfaces.CallBackInterfaces

class FragmentMenuOptions : BaseFragment<FragmentSlideMenuOptionsBinding>() {
    private var htmlData: String = ""
    private lateinit var binding: FragmentSlideMenuOptionsBinding

    /**
     * @see BaseFragment#initFragment()
     */
    override fun initFragment() {
        binding = getFragmentDataBinding()
        val webViewClient = CustomWebViewClient()
        htmlData = arguments!!.getString(getString(R.string.slide_menu_value))
        webViewClient.callBackInterfaces = object : CallBackInterfaces {
            override fun onCallBack(item: Any, fromWhere: Any) {
                binding.wvView.visibility = View.VISIBLE
                binding.progressView.visibility = View.GONE
            }
        }
        binding.wvView.webViewClient = webViewClient
        binding.wvView.loadData(htmlData, "text/html", "UTF-8")
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onResume() {
        super.onResume()
        (this.activity as BaseActivity<*>).setSlidMenuHeader(
            View.VISIBLE,
            arguments!!.getString(getString(R.string.slide_menu_title))
        )

    }

    /**
     * @see BaseFragment#getLayoutId()
     */
    override fun getLayoutId(): Int = R.layout.fragment_slide_menu_options
}