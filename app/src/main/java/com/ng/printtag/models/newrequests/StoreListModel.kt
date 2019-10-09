package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StoreListModel {

    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("msg")
    @Expose
    var msg : String?= null
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("stores")
        @Expose
        var stores: ArrayList<Store>? = null
        @SerializedName("departments")
        @Expose
        var departments: ArrayList<Department>? = null
        @SerializedName("msg")
        @Expose
        var msg: String? = null


    }

    inner class Department {
        @SerializedName("key")
        @Expose
        var key: String? = null
        @SerializedName("value")
        @Expose
        var value: String? = null
    }

    inner class Store {

        @SerializedName("key")
        @Expose
        var key: String? = null
        @SerializedName("value")
        @Expose
        var value: String? = null


    }


}