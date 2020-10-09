@file:Suppress("SpellCheckingInspection")

package com.ng.printtag.api


import com.ng.printtag.BuildConfig.DOMAIN_URL
import com.ng.printtag.models.addItems.*
import com.ng.printtag.models.allrequests.AllRequestList
import com.ng.printtag.models.allrequests.ShopSupplyList
import com.ng.printtag.models.login.LoginModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * This class for RestClient api interface
 */
interface ApiInteface {

    @POST(DOMAIN_URL + "web/session/authenticate")
    fun callLogin(@Body model: RequestBody): Call<LoginModel>

    @POST(DOMAIN_URL + "get_received_details")
    fun callReceived(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<AllRequestList>

    @POST(DOMAIN_URL + "get_shop_supply")
    fun callShopSupply(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<ShopSupplyList>

    @POST(DOMAIN_URL + "get_publsih_names")
    fun callPublication(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<PublicationModel>

    @POST(DOMAIN_URL + "get_shop_names")
    fun callShopName(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<ShopNameModel>

    @POST(DOMAIN_URL + "get_publication_price")
    fun callPublicationPrice(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<PublicationPriceModel>

    @POST(DOMAIN_URL + "get_shop_tran")
    fun callGetShopTran(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<GetShopTranModel>


    @POST(DOMAIN_URL + "create_received_copies")
    fun callCreateReceivedCopies(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<GenericModel>

    @POST(DOMAIN_URL + "create_shopsupply_tran")
    fun callCreateSupply(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<GenericModel>

    @POST(DOMAIN_URL + "create_shop_payment")
    fun callCreateShopPayment(
        @Header("Authorization") authorization: String,
        @Body model: RequestBody
    ): Call<GenericModel>

}