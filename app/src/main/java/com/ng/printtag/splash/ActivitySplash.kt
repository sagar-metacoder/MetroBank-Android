package com.ng.printtag.splash

import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.mancj.slideup.SlideUp
import com.ng.printtag.R
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Utils
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ActivitySplashBinding


class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    private lateinit var binding: ActivitySplashBinding
    lateinit var slideUp: SlideUp
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initMethod() {

        //AppUtils.noStatusBar(window)
        binding = getViewDataBinding()
        val slideUpAnimation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move
        )

        binding.pinLockView.attachIndicatorDots(binding.indicatorDots)
        binding.pinLockView.setPinLockListener(mPinLockListener)
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        binding.pinLockView.pinLength = 4
        binding.pinLockView.textColor = ContextCompat.getColor(this, R.color.white)

        binding.indicatorDots.indicatorType = IndicatorDots.IndicatorType.FIXED


/*
        slideUp = SlideUpBuilder(binding.ivSplash)
            .withListeners(object : SlideUp.Listener.Events {
                override fun onSlide(percent: Float) {


                }

                override fun onVisibilityChanged(visibility: Int) {
                   // binding.incldePin.linearPinlock.visibility =View.VISIBLE





                }
            })
            .withStartGravity(Gravity.CENTER)
            .withLoggingEnabled(true)
            .withGesturesEnabled(true)
            .withStartState(SlideUp.State.HIDDEN)
             .withSlideFromOtherView(findViewById(R.id.linear_top))
            .build()
*/


        //binding.linearTop.startAnimation(animMoveToTop);

        Handler().postDelayed({

            //  binding.ivSplash.visibility =View.GONE

            //slideUp.show()

            val animation = TranslateAnimation(0f, 0f, 0f, -350f)
            animation.setDuration(500)
            animation.setFillAfter(false)

            binding.linearSecond.startAnimation(animation)


            // binding.linearSecond.startAnimation(animMoveToTop)


            /* binding.ivSplash.animate()
                 .translationY(-((binding.linearTop.height - (binding.ivSplash.height / 2))).toFloat())
                 .setInterpolator(AccelerateInterpolator()).duration =500

 */
            //  binding.linearSecond.visibility =View.GONE

            //binding.incldePin.linearPinlock.visibility =View.VISIBLE
            // slideUp!!.show()

            //  binding.incldePin.linearPinlock.visibility =View.VISIBLE

            // binding.linearPinlock.startAnimation(animMoveToTop);

        }, 1000)


        Handler().postDelayed({


            //  binding.linearSecond.visibility =View.GONE

            //binding.incldePin.linearPinlock.visibility =View.VISIBLE
            // slideUp!!.show()
            binding.linearPinlock.visibility = View.VISIBLE

            // binding.linearPinlock.startAnimation(animMoveToTop);

        }, 1500)

        //slideUp!!.show()
    }


    private val mPinLockListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {


            if ((BaseSharedPreference.getInstance(this@ActivitySplash)
                    .getPrefValue(resources.getString(R.string.pref_pin), "1234")) == pin.toString()
            ) {
                binding.linearSecond.alpha = 0.4f
                binding.linearPinlock.alpha = 0.4f


                binding.ivImage.visibility = View.VISIBLE
                val pulse = AnimationUtils.loadAnimation(this@ActivitySplash, R.anim.logo_animation)
                binding.ivImage.startAnimation(pulse)

                Handler().postDelayed({


                    Utils.gotoHomeScreen(this@ActivitySplash)


                }, 4000)


            } else
                CallDialog.errorDialog(
                    this@ActivitySplash,
                    getString(R.string.a_lbl_warning_title),
                    "Please enter correct pin",
                    "",
                    "ok",
                    "",
                    null
                )

        }


        override fun onEmpty() {
            Log.d("TAG", "Pin empty")
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String) {
            Log.d(
                "TAG",
                "Pin changed, new length $pinLength with intermediate pin $intermediatePin"
            )
        }
    }

}