package com.ng.printtag.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenericResultModel {
    @SerializedName(value = "language")
    @Expose
    val data: List<GenericModels>? = null


    @SerializedName(value = "SignedUrl")
    @Expose
    val signedUrl: String? = null

    @SerializedName(value = "transactionPIN")
    @Expose
    val transactionPIN: String? = null

    @SerializedName("targetId")
    val targetId: String? = null
}