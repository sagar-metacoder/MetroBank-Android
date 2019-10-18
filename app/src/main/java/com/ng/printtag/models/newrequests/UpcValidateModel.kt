package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UpcValidateModel {

    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("msg")
    @Expose
    var msg: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("description")
        @Expose
        var description: String? = null

    }
}