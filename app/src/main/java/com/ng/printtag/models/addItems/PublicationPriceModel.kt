package com.ng.printtag.models.addItems

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.login.ErrorModel


class PublicationPriceModel {
    @SerializedName("id")
    @Expose
    var id: Any? = null

    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null

    @SerializedName("result")
    @Expose
    var result: Result? = null

    @SerializedName("error")
    @Expose
    var error: ErrorModel? = null

    inner class Result {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("response")
        @Expose
        var response: ArrayList<Response>? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

    }

    inner class Response {
        @SerializedName("price_per_copy")
        @Expose
        var price_per_copy: String? = "0"

        @SerializedName("id")
        @Expose
        var id: Int? = null


    }
}