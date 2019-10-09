package com.ng.printtag.dashboard

import android.view.View
import androidx.fragment.app.Fragment
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.base.HeaderToolBar
import com.ng.printtag.databinding.ActivityDashboardBinding
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.interfaces.HeaderInterface
import kotlinx.android.synthetic.main.activity_dashboard.*

class ActivityDashboard : BaseActivity<ActivityDashboardBinding>(), HeaderInterface, CallBackInterfaces {


    private lateinit var binding: ActivityDashboardBinding


    /**
     * @see BaseActivity#initMethod()
     */
    override fun initMethod() {
        binding = getViewDataBinding()
        actBaseBinding.rlMain.removeView(actBaseBinding.headerToolBar)
        binding.liToolBar.addView(actBaseBinding.headerToolBar)
        (binding.liToolBar.getChildAt(0) as HeaderToolBar).setHeaderInterface(this)
        binding.manageSlideMenu.setCallBackInterfaces(this@ActivityDashboard)

        handleClick()

    }

    /**
     * @see CallBackInterfaces
     */
    override fun onCallBack(item: Any, fromWhere: Any) {
        closeDrawer()
    }


    private fun handleClick() {
//        val controller = AppNavigation.findNavController(view)
//        controller.popBackStack(R.id.fragmentC, true)
        /*fab.setClosedOnTouchOutside(true)
        fab.hideMenuButton(false)
        fab.setOnMenuButtonClickListener {
            if (fab.isOpened) {
                Toast.makeText(this, fab.menuButtonLabelText, Toast.LENGTH_SHORT).show()
            }
            fab.toggle(true)
        }*/
    }


    override fun onRightImageClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * @see HeaderInterface
     */
    override fun onLeftImageClick() {
        AppUtils.hideKeyBoard(this)
        val currentFragment: Fragment = getCurrentFragment()
        if (currentFragment is FragmentDashboard) {
            openDrawer()
        } else {
            onBackPressed()
        }
    }

    /**
     * @see HeaderInterface
     */
    override fun onHeaderMenuItemClick(view: View) {

    }

    /**
     * back pressed event
     */
    override fun onBackPressed() {
        if (getCurrentFragment() is FragmentDashboard) {
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
     * @see HeaderInterface
     */
    override fun onMenuImageClick(item: String) {
    }


    /**
     * Init layout genericModel id
     */
    override fun getLayoutId(): Int = R.layout.activity_dashboard

    private fun openDrawer() {
        drawer_layout.openDrawer(manage_slide_menu)
    }

    private fun closeDrawer() {
        this.drawer_layout.closeDrawer(manage_slide_menu)
    }


    /**
     * Get Current diaplay fragment
     */
    private fun getCurrentFragment(): Fragment {
        return navigation_dash_board.childFragmentManager.findFragmentById(R.id.navigation_dash_board)!!
    }


}
