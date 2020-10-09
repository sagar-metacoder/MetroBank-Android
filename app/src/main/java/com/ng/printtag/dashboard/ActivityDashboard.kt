package com.ng.printtag.dashboard


import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*


class ActivityDashboard : BaseActivity<ActivityDashboardBinding>() {


    lateinit var binding: ActivityDashboardBinding

    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()

        handleClick()

        /* val item = BottomBarItem(R.mipmap.ic_home, R.string.home)
        binding.bottomBar.addTab(item)
        val item1 = BottomBarItem(R.mipmap.ic_pay_transfer, R.string.pay_transfer)
        binding.bottomBar.addTab(item1)
        val item2 = BottomBarItem(R.mipmap.ic_insight, R.string.insights)
        binding.bottomBar.addTab(item2)
        val item3 = BottomBarItem(R.mipmap.ic_product_service, R.string.products_service)
        binding.bottomBar.addTab(item3)


        binding.bottomBar.selectTab(0,false)
        binding.bottomBar.showBadge(2, R.drawable.circle)*/
        binding.bottomBar.getOrCreateBadge(R.id.navigation_notifications).isVisible = true

        binding.bottomBar.selectedItemId = 0


    }

    private fun handleClick() {
        // binding.bottomBar.setOnSelectListener(OnSelectListener { position -> position })

    }

    /**
     * back pressed event
     */
    override fun onBackPressed() {
        if (getCurrentFragment() is FragmentDashboard) {

            // AppUtils.setStatusBar(window,this)

            CallDialog.errorDialog(
                this,
                getString(R.string.a_lbl_information),
                getString(R.string.a_lbl_exit_message),
                getString(R.string.a_btn_yes),
                getString(R.string.a_btn_no),
                getString(R.string.action_sign_out),
                null
            )
        } else {
            super.onBackPressed()
        }
    }


    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_dashboard

    /**
     * Get Current diaplay fragment
     */
    private fun getCurrentFragment(): Fragment {
        return navigation_dash_board.childFragmentManager.findFragmentById(R.id.navigation_dash_board)!!
    }


    override fun onResume() {
        super.onResume()
        val currentFragment: Fragment = getCurrentFragment()
        if (currentFragment is FragmentDashboard) {
            currentFragment.init()
        }

    }

}
