package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.login.ErrorModel

class AllRequestList {
    /*@SerializedName("response")
    @Expose
    var response: ArrayList<ResponseClass>? = null*/
    @SerializedName("id")
    @Expose
    var id: Any? = null

    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null

    @SerializedName("error")
    @Expose
    var error: ErrorModel? = null

    @SerializedName("result")
    @Expose
    var result: Result? = null

    inner class Result {
        @SerializedName("response")
        @Expose
        var response: ArrayList<ResponseClass>? = null

        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null
    }

}