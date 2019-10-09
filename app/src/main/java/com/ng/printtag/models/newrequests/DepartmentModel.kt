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
        @SerializedName("templates")
        @Expose
        var templates: Template? = null
        @SerializedName("departments")
        @Expose
        var departments: Any? = null



    }

    inner class Template {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("size")
        @Expose
        var size: String? = null

        @SerializedName("html")
        @Expose
        var html: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
    }

}