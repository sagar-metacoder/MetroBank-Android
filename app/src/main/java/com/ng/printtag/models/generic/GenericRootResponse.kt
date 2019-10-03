package com.ng.printtag.models.generic

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenericRootResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("data")
    @Expose
    val result: GenericResultModel? = null
}