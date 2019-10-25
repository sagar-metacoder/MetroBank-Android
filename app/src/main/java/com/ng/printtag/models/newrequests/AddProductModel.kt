package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddProductModel {

    @SerializedName("upc")
    @Expose
    var upcNumber: String? = null
    @SerializedName("name")
    @Expose
    var upcName: String? = null
    @SerializedName("qty")
    @Expose
    var qty: String? = null


}