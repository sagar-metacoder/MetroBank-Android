package com.ng.printtag.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Base64
import com.ng.printtag.BuildConfig
import com.ng.printtag.R
import com.ng.printtag.apputils.BaseSharedPreference
import com.ng.printtag.apputils.Constant
import com.ng.printtag.interfaces.CallBackInterfaces
import com.ng.printtag.models.generic.GenericRootResponse
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


object RequestMethods {


    @SuppressLint("ObsoleteSdkInt")
    fun getAuthToken(context: Context): String {
        val authToken =
            BaseSharedPreference.getInstance(context).getPrefValue(context.getString(R.string.pref_auth_token))
        return if (authToken!!.isEmpty()) {
            val credentials = BuildConfig.USER_NAME + ":" + BuildConfig.PASSWORD
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ->
                    credentials.toByteArray(StandardCharsets.UTF_8)
                else ->
                    credentials.toByteArray(Charset.forName("UTF-8"))
            }
            val base64 = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            BaseSharedPreference.getInstance(context).putValue(context.getString(R.string.pref_auth_token), base64)
            base64
        } else {
            authToken
        }
    }

    fun getRequestBody(json: JSONObject): RequestBody {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString())
    }

    fun getLogin(context: Context, userName: String?, password: String?): RequestBody {
        val rootJson = JSONObject()
        rootJson.put(context.getString(R.string.key_userName), userName)
        rootJson.put(context.getString(R.string.key_password), password)
        return getRequestBody(rootJson)
    }





    fun callImageApi(activity: Context, image: String?, callBackInterfaces: CallBackInterfaces) {
        if (!image.isNullOrEmpty()) {
            val rootJson = JSONObject()
            val restClientModel = RestClientModel()
            restClientModel.isProgressDialogShow = false
            restClientModel.isErrorScreenShow = true
            rootJson.put("assetPath", image)
            val body = getRequestBody(rootJson)
            RestClient().apiRequest(activity, body, Constant.CALL_SIGN_URL, object : ApiResponseListener {
                override fun onApiResponse(response: Response<Any>, reqCode: Int) {
                    when (reqCode) {
                        Constant.CALL_SIGN_URL -> {
                            val rootModel = response.body() as GenericRootResponse
                            when (rootModel.success) {
                                true -> {
                                    callBackInterfaces.onCallBack(rootModel.result!!.signedUrl!!, 1)
                                }
                                false ->
                                    callBackInterfaces.onCallBack("", 0)
                            }
                        }
                    }
                }

                override fun onApiError(response: Any, reqCode: Int) {
                    //ProgressDialog.displayProgressDialog(activity, false, "")
                }

                override fun onApiNetwork(response: Any, reqCode: Int) {
                    //ProgressDialog.displayProgressDialog(activity, false, "")
                }

            }, restClientModel)
        } else {
            callBackInterfaces.onCallBack("", -1)
        }
    }



    fun getDateFormat(dateValue: String): String {
        val year = dateValue.substring(0, 4)
        val month = dateValue.substring(4, 6)
        val date = dateValue.substring(6, 8)

        return "$month/$date/$year"
    }

}

