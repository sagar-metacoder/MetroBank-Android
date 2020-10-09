package com.ng.printtag.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ErrorModel {

    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("debug")
        @Expose
        var debug: String? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("arguments")
        @Expose
        var arguments: List<String>? = null

        @SerializedName("exception_type")
        @Expose
        var exceptionType: String? = null
        /* @SerializedName("context")
         @Expose
         var context: Context? = null*/

    }

}