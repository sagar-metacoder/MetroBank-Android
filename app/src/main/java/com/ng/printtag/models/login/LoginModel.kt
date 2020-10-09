package com.ng.printtag.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null

    @SerializedName("id")
    @Expose
    var id: Any? = null

    @SerializedName("result")
    @Expose
    var result: Result? = null

    @SerializedName("error")
    @Expose
    var error: ErrorModel? = null


    var userName: String? = null

    var password: String? = null
//    var storeList: MutableList<StoreModels>? = null


    inner class Result {
        @SerializedName("partner_id")
        @Expose
        var partnerId: Int? = null

        @SerializedName("db")
        @Expose
        var db: String? = null

        @SerializedName("server_version")
        @Expose
        var serverVersion: String? = null

        @SerializedName("is_system")
        @Expose
        var isSystem: Boolean? = null

        @SerializedName("server_version_info")
        @Expose
        var serverVersionInfo: List<String>? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("uid")
        @Expose
        var uid: Int? = null

        @SerializedName("show_effect")
        @Expose
        var showEffect: String? = null

        @SerializedName("has_advance_search_group")
        @Expose
        var hasAdvanceSearchGroup: Boolean? = null

        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("company_id")
        @Expose
        var companyId: Int? = null

        @SerializedName("is_admin")
        @Expose
        var isAdmin: Boolean? = null

        @SerializedName("session_id")
        @Expose
        var sessionId: String? = null

        @SerializedName("partner_display_name")
        @Expose
        var partnerDisplayName: String? = null

        @SerializedName("web.base.url")
        @Expose
        var webBaseUrl: String? = null

        @SerializedName("web_tours")
        @Expose
        var webTours: List<Any>? = null

        @SerializedName("user_companies")
        @Expose
        var userCompanies: UserCompanies? = null

    }
    /* inner class UserCompanies
     {
         @SerializedName("allowed_companies")
         @Expose
        var allowedCompanies: ArrayList<List<String>>? = null
         @SerializedName("current_company")
         @Expose
        var currentCompany: ArrayList<String>? = null
     }*/

}

