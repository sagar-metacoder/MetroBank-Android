package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllRequestModel {

    @SerializedName("printType")
    @Expose
    var printType: String? = null
    @SerializedName("templateName")
    @Expose
    var templateName: String? = null
    @SerializedName("store")
    @Expose
    var store: String? = null
    @SerializedName("department")
    @Expose
    var department: String? = null
    @SerializedName("effectiveDate")
    @Expose
    var effectiveDate: String? = null
    @SerializedName("reqDate")
    @Expose
    var reqDate: String? = null
    @SerializedName("expectedDate")
    @Expose
    var expectedDate: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
}