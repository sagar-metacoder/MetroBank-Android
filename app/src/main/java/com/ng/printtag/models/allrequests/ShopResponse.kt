package com.ng.printtag.models.allrequests

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.addItems.GetShopTranModel

import com.ng.printtag.models.allrequests.ResponseClass.CompanyId


class ShopResponse {

    @SerializedName("total_received_amt")
    @Expose
    var totalReceivedAmt: String? = null

    @SerializedName("company_id")
    @Expose
    var companyId: CompanyId? = null

    @SerializedName("balance_amt")
    @Expose
    var balanceAmt: String? = "0.00"

    @SerializedName("previous_bal_amt")
    @Expose
    var previousBalAmt: String? = "0.00"

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("business_date")
    @Expose
    var businessDate: String? = null

    @SerializedName("total_tran_amt")
    @Expose
    var totalTranAmt: String? = "0.00"

    @SerializedName("shop_id")
    @Expose
    var shopId: ShopId? = null

    @SerializedName("supply_ids")
    @Expose
    var supplyIds: ArrayList<GetShopTranModel.SupplyId>? = null

    inner class ShopId {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null
    }
}