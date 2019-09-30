@file:Suppress("SpellCheckingInspection")

package com.ng.printtag.api


import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * This class for RestClient api interface
 */
interface ApiInteface {

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