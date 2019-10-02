package com.ng.printtag.apputils.custom

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ng.printtag.interfaces.CallBackInterfaces

class CustomWebViewClient : WebViewClient() {
    lateinit var callBackInterfaces: CallBackInterfaces
    /**
     * @param view
     * @param request
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        //            final Uri uri = request.getUrl();
        return true
    }

    /**
     * @param view
     * @param url
     */
    override fun onPageFinished(view: WebView, url: String) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url)
        callBackInterfaces.onCallBack("", "webView")

    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        callBackInterfaces.onCallBack("", "start")

    }
}
