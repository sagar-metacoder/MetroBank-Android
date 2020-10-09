package com.ng.printtag.models.addItems

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.allrequests.ResponseClass.NewspaperId
import com.ng.printtag.models.allrequests.ShopResponse.ShopId
import com.ng.printtag.models.login.ErrorModel


class GetShopTranModel {
    @SerializedName("id")
    @Expose
    var id: Any? = null

    @SerializedName("jsonrpc")
    @Expose
    var jsonrpc: String? = null

    @SerializedName("result")
    @Expose
    var result: Result? = null

    @SerializedName("error")
    @Expose
    var error: ErrorModel? = null

    inner class Result {
        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("response")
        @Expose
        var response: ArrayList<Response>? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null


    }

    inner class Response {
        @SerializedName("total_tran_amt")
        @Expose
        var totalTranAmt: Double? = null

        @SerializedName("company_id")
        @Expose
        var companyId: String? = null

        @SerializedName("business_date")
        @Expose
        var businessDate: String? = null

        @SerializedName("previous_bal_amt")
        @Expose
        var previousBalAmt: Double? = null

        @SerializedName("supply_ids")
        @Expose
        var supplyIds: ArrayList<SupplyId>? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("shop_id")
        @Expose
        var shopId: ShopId? = null

        @SerializedName("balance_amt")
        @Expose
        var balanceAmt: Double? = null

        @SerializedName("total_received_amt")
        @Expose
        var totalReceivedAmt: Double? = null
    }

    inner class SupplyId {
        @SerializedName("free_copies")
        @Expose
        var freeCopies: Int? = null

        @SerializedName("copies_in_hand")
        @Expose
        var copiesInHand: Int? = null

        @SerializedName("tran_type")
        @Expose
        var tranType: String? = null

        @SerializedName("newspaper_id")
        @Expose
        var newspaperId: NewspaperId? = null

        @SerializedName("published_date")
        @Expose
        var publishedDate: String? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("shop_supply_id")
        @Expose
        var shopSupplyId: Int? = null

        @SerializedName("no_of_copies")
        @Expose
        var noOfCopies: Int? = null

        @SerializedName("price_per_copy")
        @Expose
        var pricePerCopy: Double? = null

        @SerializedName("total_copies")
        @Expose
        var totalCopies: Int? = null
    }

}