package com.ng.printtag.models.newrequests

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


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
         var stores: String?=null
        @SerializedName("departments")
        @Expose
        var departments: Any? = null
        @SerializedName("msg")
        @Expose
        var msg: String? = null


    }
    inner class StoreValue
    {
        var hashMap: HashMap<String,String>? =null

    }


}