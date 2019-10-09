
package com.ng.printtag.api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.ng.printtag.BuildConfig
import com.ng.printtag.R
import com.ng.printtag.apputils.AppUtils
import com.ng.printtag.apputils.CallDialog
import com.ng.printtag.apputils.Constant
import com.ng.printtag.apputils.TLSSocketFactory
import com.ng.printtag.base.BaseActivity
import com.ng.printtag.databinding.ManageExceptionsBinding
import com.ng.printtag.interfaces.SkipGetSerialisation
import com.ng.printtag.interfaces.SkipPostSerialisation
import com.ng.printtag.models.login.LoginModel
import com.ng.printtag.models.newrequests.StoreListModel

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/* add networkInterceptor for enable log */
//builderOkHttp.addInterceptor(logging)
//builderOkHttp.addInterceptor(ChuckInterceptor(context))

/**
 * Used for api calling
 */

@Suppress("UNCHECKED_CAST", "SpellCheckingInspection")
open class RestClient {
    @Suppress("SpellCheckingInspection")
    companion object {
        private const val DURATION = 500 // Seconds
        @Suppress("SpellCheckingInspection")
        var apiInteface: ApiInteface? = null

        fun getInstance(context: Context): ApiInteface {
            if (apiInteface == null) {
                setupRestClient(context)
            }
            return apiInteface as ApiInteface
        }

        private fun setupRestClient(context: Context) {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })
                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("TLSv1.2")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = TLSSocketFactory()
                val builderOkHttp = OkHttpClient.Builder()
                builderOkHttp.hostnameVerifier(getHostNameVerifier())
                builderOkHttp.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builderOkHttp.connectTimeout(DURATION.toLong(), TimeUnit.SECONDS)
                builderOkHttp.readTimeout(DURATION.toLong(), TimeUnit.SECONDS)
                val interceptor = HeaderInterceptor(context)
                builderOkHttp.networkInterceptors().add(interceptor)
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val client = builderOkHttp.addInterceptor(logging)
                    .connectTimeout(DURATION.toLong(), TimeUnit.SECONDS)
                    .readTimeout(DURATION.toLong(), TimeUnit.SECONDS)
                    .build()
                val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .addSerializationExclusionStrategy(object : ExclusionStrategy {
                        override fun shouldSkipField(f: FieldAttributes): Boolean {
                            return f.getAnnotation(SkipPostSerialisation::class.java) != null
                        }

                        override fun shouldSkipClass(clazz: Class<*>): Boolean {
                            return false
                        }
                    }).addDeserializationExclusionStrategy(object : ExclusionStrategy {
                        override fun shouldSkipField(f: FieldAttributes): Boolean {
                            return f.getAnnotation(SkipGetSerialisation::class.java) != null
                        }

                        override fun shouldSkipClass(clazz: Class<*>): Boolean {
                            return false
                        }
                    })
                    .setPrettyPrinting()
                    .create()
                apiInteface = Retrofit.Builder().client(client).baseUrl(BuildConfig.DOMAIN_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder)).build()
                    .create<ApiInteface>(ApiInteface::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        private fun getHostNameVerifier(): HostnameVerifier {
            return HostnameVerifier { _, _ -> true }
        }

        /** This method call when need to network call
         * @param activity Which activity/fragment use this method  to network call
         * @param call body parameter that want to pass during network call
         * @param reqCode which request is call. Multiple request may be call on screen so it will differentiate by request code
         * @param restClientModel if need to progressbar show/hide during network call
         * @param apiResponseListener Response will depend on Request. It will return object and we can type cast this object according to Response
         */

        fun makeApiRequest(
            activity: Context, requestObject: Any,
            call: Any,
            reqCode: Int,
            restClientModel: RestClientModel,
            apiResponseListener: ApiResponseListener
        ) {
            try {
                if (restClientModel.isProgressDialogShow) {
                }
                (activity as BaseActivity<*>).actBaseBinding.frmBaseContainer.visibility = View.VISIBLE
                activity.actBaseBinding.frmExceptionContainer.visibility = GONE
                if (AppUtils.isNetworkAvailable(activity)) {
                    val callApi = (call as Call<Any>).clone()
                    callApi.enqueue(object : Callback<Any> {
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            when (t) {
//                                {"msg":"ciam server is not responding.","success":false}
                                is ConnectException, is SocketTimeoutException -> {
                                    handleException(
                                        activity,
                                        activity.getString(R.string.a_lbl_net_title),
                                        activity.getString(R.string.a_msg_time_out_error),
                                        callApi,
                                        reqCode,
                                        apiResponseListener,
                                        restClientModel
                                    )
                                }
                                else -> {
                                    if (!call.request().url().encodedPath().contains("assets/signed/url"))
                                        setError(
                                            activity,
                                            t.toString(),
                                            restClientModel,
                                            reqCode,
                                            apiResponseListener,
                                            callApi
                                        )
                                }
                            }
                        }

                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            when {
                                response.body() == null -> {
                                    //{"msg":"ciam server is not responding.","success":false}
                                    var isFilterApi = true
                                    val urlPath = call.request().url().encodedPath()

                                    if (urlPath.contains("assets/signed/url"))
                                        isFilterApi = false
                                    if (urlPath.contains("associateDetail")) {
                                        isFilterApi = false
                                        apiResponseListener.onApiError("", reqCode)
                                    }

                                    if (isFilterApi) {
                                        when {
                                            response.raw().code() == 404 -> {
                                                setError(
                                                    activity,
                                                    activity.getString(R.string.a_msg_server_error),
                                                    restClientModel,
                                                    reqCode,
                                                    apiResponseListener, callApi

                                                )
                                            }
                                            else -> {
                                                var message: String?
                                                message =
                                                    if (response.errorBody() != null && response.errorBody()?.string() != null) {
                                                        response.errorBody()?.string().toString()
                                                } else {
                                                       activity.getString(R.string.a_msg_server_error)
                                                }
                                                if (message.isNullOrEmpty())
                                                    message =
                                                        activity.getString(R.string.a_msg_server_error)
                                                message?.let {
                                                    setError(
                                                        activity,
                                                        it,
                                                        restClientModel,
                                                        reqCode,
                                                        apiResponseListener, callApi

                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                else -> {
                                    if (response.isSuccessful) {
                                        when (restClientModel.isProgressDialogShow) {
                                            true -> {
                                            }
                                        }
                                        try {
                                            apiResponseListener.onApiResponse(response, reqCode)
                                        } catch (e: Exception) {
                                            setException(
                                                e,
                                                activity,
                                                restClientModel,
                                                reqCode,
                                                apiResponseListener,
                                                callApi
                                            )
                                        }

                                    } else {
                                        setError(
                                            activity,
                                            response.message(),
                                            restClientModel,
                                            reqCode,
                                            apiResponseListener, callApi

                                        )
                                    }
                                }
                            }
                        }
                    })
                } else {
                    handleException(
                        activity,
                        activity.getString(R.string.a_lbl_network_error),
                        activity.getString(R.string.a_net_message),
                        call,
                        reqCode,
                        apiResponseListener,
                        restClientModel
                    )
                    /* setNetworkError(
                         activity,
                         Utils.getLabel(activity.getString(R.string.a_lbl_network_error)),
                         restClientModel,
                         reqCode,
                         apiResponseListener
                     )*/
                }
            } catch (e: Exception) {
                setException(e, activity, restClientModel, reqCode, apiResponseListener, call)
            }
        }

        /** This method will call when any Exception will acquire when api will call
         * @param e If any exception will acquire it will goes to RestClient class will show error Screen
         * @param activity Which activity/fragment use this method  to network call
         * @param restClientModel if need to progressbar show/hide during network call
         * @param apiResponseListener Response will depend on Request. It will return object and we can type cast this object according to Response
         * @param reqCode  which request is call. Multiple request may be call on screen so it will differentiate by request code
         */
        private fun setException(
            e: Exception,
            activity: Context,
            restClientModel: RestClientModel,
            reqCode: Int,
            apiResponseListener: ApiResponseListener, call: Any
        ) {
            e.printStackTrace()
            val message = activity.getString(R.string.a_msg_server_error)
            setError(activity, message, restClientModel, reqCode, apiResponseListener, call)
        }

        /** This method will call when any error will acquire when api will call
         * @param activity Which activity/fragment use this method  to network call
         * @param message Any error message that need to be shown when network call fail
         * @param restClientModel if need to progressbar show/hide during network call
         * @param apiResponseListener Response will depend on Request. It will return object and we can type cast this object according to Response
         * @param reqCode  which request is call. Multiple request may be call on screen so it will differentiate by request code
         */
        private fun setError(
            activity: Context,
            message: String,
            restClientModel: RestClientModel,
            reqCode: Int,
            apiResponseListener: ApiResponseListener,
            call: Any
        ) {
            if (restClientModel.isErrorScreenShow) {
                apiResponseListener.onApiError("", reqCode)
            } else {
                handleException(
                    activity,
                   activity.getString(R.string.a_lbl_warning_title),
                    message,
                    call,
                    reqCode,
                    apiResponseListener,
                    restClientModel
                )
            }

            /* try {
                 ProgressDialog.displayProgressDialog(activity, false, "")
                 if (restClientModel.isErrorScreenShow)
                     apiResponseListener.onApiError(message, reqCode)
                 else {
                     callErrorDialog(activity, message, Utils.getLabel(activity.getString(R.string.a_lbl_warning_title)))
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
             }*/
        }

        /* private fun setNetworkError(
             activity: Context,
             message: String,
             restClientModel: RestClientModel,
             reqCode: Int,
             apiResponseListener: ApiResponseListener
         ) {
             try {
                 ProgressDialog.displayProgressDialog(activity, false, "")
                 if (restClientModel.isNetWorkScreenShow)
                     apiResponseListener.onApiNetwork(message, reqCode)
                 else {
                     callErrorDialog(activity, message, Utils.getLabel(activity.getString(R.string.a_lbl_net_title)))
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }*/


        private fun handleException(
            activity: Context, title: String, message: String,
            requestObject: Any,
            reqCode: Int,
            apiResponseListener: ApiResponseListener,
            restClientModel: RestClientModel
        ) {
            try {
                when {
                    restClientModel.isNetWorkScreenShow -> {
                        CallDialog.errorDialog(
                            activity,
                            activity.getString(R.string.a_lbl_network_error),
                            activity.getString(R.string.a_net_message),
                            "",
                            activity.getString(R.string.a_btn_ok),
                            "",
                            null
                        )

                    }
                    else -> throwException(
                        activity,
                        title,
                        message,
                        requestObject,
                        reqCode,
                        apiResponseListener,
                        restClientModel
                    )
                    /*else -> callErrorDialog(
                        activity,
                        message,
                        Utils.getLabel(title)
                    )*/
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun throwException(
            activity: Context, title: String, message: String,
            requestObject: Any,
            reqCode: Int,
            apiResponseListener: ApiResponseListener,
            restClientModel: RestClientModel
        ) {
            val frmBaseContainer: FrameLayout = (activity as BaseActivity<*>).actBaseBinding.frmBaseContainer
            val frmExceptionContainer: FrameLayout = activity.actBaseBinding.frmExceptionContainer
            frmBaseContainer.visibility = GONE
            frmExceptionContainer.visibility = View.VISIBLE
            frmExceptionContainer.removeAllViews()
            val binding: ManageExceptionsBinding =
                setExceptionView(activity, frmExceptionContainer, R.layout.manage_exceptions) as ManageExceptionsBinding
            binding.tvExceptionTitle.text = title
            binding.tvExceptionMessage.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
//            binding.tvCancel.visibility=GONE
            AppUtils.hideKeyBoard(activity as AppCompatActivity, binding.btnRetry)
            binding.tvCancel.setOnClickListener {
                frmBaseContainer.visibility = View.VISIBLE
                frmExceptionContainer.visibility = GONE
                frmBaseContainer.invalidate()
                frmExceptionContainer.invalidate()
            }
            binding.btnRetry.setOnClickListener {
                makeApiRequest(activity, requestObject, requestObject, reqCode, restClientModel, apiResponseListener)
            }
        }

        private fun setExceptionView(activity: Context, container: FrameLayout, layout: Int): ViewDataBinding {
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(activity), layout, container, false)
            val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            params.addRule(RelativeLayout.BELOW, R.id.header_tool_bar)
            container.layoutParams = params
            container.addView(binding.root)
            container.visibility = View.VISIBLE
            return binding
        }
    }

    private fun getApiClient(): ApiInteface? {
        return apiInteface
    }

    /** This method call when need to network call
     * @param activity Which activity/fragment use this method  to network call
     * @param requestObject body parameter that want to pass during network call
     * @param reqCode which request is call. Multiple request may be call on screen so it will differentiate by request code
     * @param restClientModel if need to progressbar show/hide during network call
     * @param apiResponseListener Response will depend on Request. It will return object and we can type cast this object according to Response
     */
    fun apiRequest(
        activity: Context,
        requestObject: Any,
        reqCode: Int,
        apiResponseListener: ApiResponseListener,
        restClientModel: RestClientModel
    ) {
        when (reqCode) {
            Constant.CALL_SIGN_URL -> {
                val callApi: Call<LoginModel> = getApiClient()!!.callLogin(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }

            Constant.CALL_STORE_URL -> {
                val callApi: Call<StoreListModel> = getApiClient()!!.callStoreList(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            /*Constant.CALL_SEND_PRIVACY_DETAILS -> {
                val callApi: Call<GenericRootResponse> =
                    getApiClient()!!.sendPrivacyDetails(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_SIGN_URL -> {
                val callApi: Call<GenericRootResponse> = getApiClient()!!.getSingUrl(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            *//* Constant.CALL_LOGIN_STORE_REQ_CODE -> {
                 val callApi: Call<StoreRootModel> = getApiClient()!!.callValidateUser(requestObject as String)
                 makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                 return
             }*//*
            Constant.CALL_VALIDATE_STORE_REQ_CODE -> {
                val callApi: Call<GenericRootResponse> = getApiClient()!!.callValidStores(requestObject as String)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_GET_LANGUAGE_REQ_CODE -> {
                val callApi: Call<GenericRootResponse> = getApiClient()!!.callLanguageApi()
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_APP_TEXT_LANGUAGE_REQ_CODE -> {
                val callApi: Call<ResponseBody> = getApiClient()!!.getAppText(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_TERMS_CONDITIONS_REQ_CODE -> {
                val callApi: Call<TermConditionsResponse> =
                    getApiClient()!!.callTermsConditionApi(requestObject as RequestBody)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_TERMS_CONDITIONS_OLD_REQ_CODE -> {
                val callApi: Call<TermConditionsResponse> =
                    getApiClient()!!.callTermsConditionOldApi(requestObject as String)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_COUNTRY_REQ_CODE -> {
                val callApi: Call<AddressResponse> = getApiClient()!!.callCountryApi()
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_COUNTRY_CODE_REQ_CODE -> {
                val callApi: Call<AddressResponse> = getApiClient()!!.callCodeCountryApi()
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }
            Constant.CALL_US_STREET_REQ -> {
                val queryMap: HashMap<String, String> = requestObject as HashMap<String, String>
                queryMap["auth-id"] = Constant.SMARTY_STREET_AUTH_KEY
                queryMap["auth-token"] = Constant.SMARTY_STREET_AUTH_TOKEN
                val callApi: Call<List<StreetResponse>> = getApiClient()!!.getStreet(queryMap)
                makeApiRequest(activity, requestObject, callApi, reqCode, restClientModel, apiResponseListener)
                return
            }*/
        }
    }
}