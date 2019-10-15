package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllRequestModel {
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
        @SerializedName("records")
        @Expose
        var records: MutableList<Records>? = null
        @SerializedName("recordsMsg")
        @Expose
        var recordsMsg: String? = null

        inner class Records {
            @SerializedName("reqId")
            @Expose
            var reqId: String? = null
            @SerializedName("tagType")
            @Expose
            var tagType: String? = null
            @SerializedName("templateName")
            @Expose
            var templateName: String? = null
            @SerializedName("storeName")
            @Expose
            var storeName: String? = null
            @SerializedName("departmentName")
            @Expose
            var department: String? = null
            @SerializedName("effectiveDate")
            @Expose
            var effectiveDate: String? = null
            @SerializedName("requestedDate")
            @Expose
            var reqDate: String? = null
            @SerializedName("expectedDate")
            @Expose
            var expectedDate: String? = null
            @SerializedName("status")
            @Expose
            var status: String? = null


        }
    }

}