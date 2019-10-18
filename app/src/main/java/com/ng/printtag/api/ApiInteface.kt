@file:Suppress("SpellCheckingInspection")

package com.ng.printtag.api


import com.ng.printtag.BuildConfig.*
import com.ng.printtag.models.allrequests.AllRequestModel
import com.ng.printtag.models.login.LoginModel
import com.ng.printtag.models.newrequests.*
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

    @POST(API_BASE_MODULE + "tempDetails")
    fun callTemplateDetails(@Body model: RequestBody): Call<TempletListModel>

    @POST(API_WEBSERVICE_REQUEST + "submit")
    fun callNewRequestSubmit(@Body model: RequestBody): Call<NewPrintReqSubmit>


    @POST(API_WEBSERVICE_REQUEST + "records/all")
    fun callAllRequest(@Body model: RequestBody): Call<AllRequestModel>

    @POST(API_WEBSERVICE + "validateUPC")
    fun callUpcValidate(@Body model: RequestBody): Call<UpcValidateModel>

}