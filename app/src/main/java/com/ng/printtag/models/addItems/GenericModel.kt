package com.ng.printtag.models.addItems

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.login.ErrorModel

class GenericModel {
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

        @SerializedName("success")
        @Expose
        var success: Boolean? = null

    }

}