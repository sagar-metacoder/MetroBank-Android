package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class StoreListModel {

    @SerializedName("success")
    @Expose
    private val success: Boolean = false
    @SerializedName("data")
    @Expose
    private val data: Data? = null

    inner class Data
    {
        @SerializedName("stores")
        @Expose
        private val stores: Any? = null
        @SerializedName("departments")
        @Expose
        private val departments: Any? = null
        @SerializedName("msg")
        @Expose
        private val msg: String? = null


    }

}