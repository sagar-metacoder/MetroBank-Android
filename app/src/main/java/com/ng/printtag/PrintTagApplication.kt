package com.ng.printtag

import android.app.Application
import android.os.CountDownTimer
import com.ng.printtag.api.RestClient


class PrintTagApplication : Application() {
    var countDownTimer: CountDownTimer? = null

    companion object {
        lateinit var applicationInstance: PrintTagApplication
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        //Fabric.with(this, Crashlytics())
        RestClient.getInstance(this)
//        MicroblinkSDK.setLicenseFile("MB_ng.pdp_BlinkID_Android_2019-05-06.mblic", this)


    }

}