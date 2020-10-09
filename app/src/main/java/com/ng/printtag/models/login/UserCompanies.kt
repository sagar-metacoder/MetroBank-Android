package com.ng.printtag.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserCompanies {
    @SerializedName("allowed_companies")
    @Expose
    var allowedCompanies: ArrayList<List<String>>? = ArrayList()

    @SerializedName("current_company")
    @Expose
    var currentCompany: ArrayList<String>? = null
}