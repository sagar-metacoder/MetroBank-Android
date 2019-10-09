@file:Suppress("SpellCheckingInspection")

package com.ng.printtag.api


import com.ng.printtag.BuildConfig.API_BASE_MODULE
import com.ng.printtag.BuildConfig.API_WEBSERVICE
import com.ng.printtag.models.allrequests.AllRequestModel
import com.ng.printtag.models.login.LoginModel
import com.ng.printtag.models.newrequests.DepartmentModel
import com.ng.printtag.models.newrequests.StoreListModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This class for RestClient api interface
 */
interface ApiInteface {

    @POST(API_BASE_MODULE + "login")
    fun callLogin(@Body model: RequestBody): Call<LoginModel>

    @POST(API_BASE_MODULE + "storeDept")
    fun callStoreList(@Body model: RequestBody): Call<StoreListModel>

    @POST(API_BASE_MODULE + "deptTemp")
    fun callDepartmentList(@Body model: RequestBody): Call<DepartmentModel>


    @POST(API_WEBSERVICE + "records/all")
    fun callAllRequest(@Body model: RequestBody): Call<AllRequestModel>

    // Language Section
   /* @GET(API_BASE_MODULE + "applanguage")
    fun callLanguageApi(): Call<GenericRootResponse>

    // Language Data
    @POST(API_BASE_MODULE + "language/data")
    fun getAppText(@Body model: RequestBody): Call<ResponseBody>

    // Address Section
    @GET(API_BASE_MODULE + "country")
    fun callCountryApi(): Call<AddressResponse>

    // Country Code
    @GET(API_BASE_MODULE + API_GENERAL_MODULE + "filter/country/isRegistrationActivate/1")
    fun callCodeCountryApi(): Call<AddressResponse>


    // Check Verification
    @POST(API_BASE_MODULE + API_CHECK_CASHING_MODULE + "underwriting/update")
    fun callUpdateUnderWrittingStatus(@Body model: UnderWritingUpdateStatus): Call<UnderwritingResponse>


    *//* // Update Underwriting
     @POST(API_BASE_MODULE + API_CHECK_CASHING_MODULE + "underwriting/update")
     fun callUpdateUnderWriting(@Body model: CheckVerificationModel): Call<CheckVerificationResponse>*//*

    // Send Receipt
    @POST(API_BASE_MODULE + API_CHECK_CASHING_MODULE + "send-receipt")
    fun callSendReceipt(@Body model: SendReceiptModel): Call<GenericRootResponse>*/
}