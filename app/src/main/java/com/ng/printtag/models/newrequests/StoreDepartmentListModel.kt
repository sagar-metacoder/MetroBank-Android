package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StoreDepartmentListModel {


    @SerializedName("value")
    @Expose
    var value: String? = null

    var selected: Boolean = false


}