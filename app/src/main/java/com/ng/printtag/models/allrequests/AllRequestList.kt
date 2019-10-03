package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllRequestList {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: ArrayList<AllRequestModel>? = null
}