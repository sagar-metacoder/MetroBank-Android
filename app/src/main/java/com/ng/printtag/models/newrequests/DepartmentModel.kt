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
        @SerializedName("templatesCount")
        @Expose
        var templatesCount: String? = null
        @SerializedName("templatesMsg")
        @Expose
        var templatesMsg: String? = null
        @SerializedName("templates")
        @Expose
        var templates: MutableList<Template>? = null
        @SerializedName("departments")
        @Expose
        var departments: MutableList<Departments>? = null


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

        var departmentSelected: Boolean? = false
    }

    inner class Departments {
        @SerializedName("key")
        @Expose
        var key: String? = null
        @SerializedName("value")
        @Expose
        var value: String? = null
    }

}