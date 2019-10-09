package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DepartmentModel {

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
        @SerializedName("stores")
        @Expose
        var stores: Any? = null
        @SerializedName("departments")
        @Expose
        var departments: Any? = null
        @SerializedName("msg")
        @Expose
        var msg: String? = null


    }

}