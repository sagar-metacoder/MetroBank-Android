package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class NewPrintReqSubmit {

    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("msg")
        @Expose
        var msg: String? = null
    }
}