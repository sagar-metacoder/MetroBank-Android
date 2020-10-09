package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ResponseClass {
    @SerializedName("company_id")
    @Expose
    var companyId: CompanyId? = null

    @SerializedName("cost_per_copy")
    @Expose
    var costPerCopy: String? = "0.00"

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("newspaper_id")
    @Expose
    var newspaperId: NewspaperId? = null

    @SerializedName("received_date")
    @Expose
    var receivedDate: String? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("published_date")
    @Expose
    var publishedDate: String? = null

    @SerializedName("no_of_copies")
    @Expose
    var noOfCopies: String? = null

    inner class CompanyId {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

    }

    inner class NewspaperId {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }

}