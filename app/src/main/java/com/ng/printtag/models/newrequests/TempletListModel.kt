package com.ng.printtag.models.newrequests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TempletListModel {

    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("templateDetails")
        @Expose
        var templateDetails: TemplateDetails? = null
        @SerializedName("msg")
        @Expose
        var msg: String? = null


        inner class TemplateDetails {
            @SerializedName("id")
            @Expose
            var id: Int = 0
            @SerializedName("maximumQuantity")
            @Expose
            var maximumQuantity: String? = null
            @SerializedName("cutOffDay")
            @Expose
            var cutOffDay: String? = null
            @SerializedName("printingDay")
            @Expose
            var printingDay: String? = null

        }
    }
}