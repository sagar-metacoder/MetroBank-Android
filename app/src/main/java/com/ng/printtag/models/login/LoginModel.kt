package com.ng.printtag.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("@odata.context")
    @Expose
    var odataContext: String? = null
    @SerializedName("businessPhones")
    @Expose
    var businessPhones: List<Any>? = null
    @SerializedName("displayName")
    @Expose
    var displayName: String? = null
    @SerializedName("givenName")
    @Expose
    var givenName: String? = null
    @SerializedName("jobTitle")
    @Expose
    var jobTitle: Any? = null
    @SerializedName("email")
    @Expose
    var mail: String? = null
    @SerializedName("mobilePhone")
    @Expose
    var mobilePhone: String? = null
    @SerializedName("officeLocation")
    @Expose
    var officeLocation: String? = null
    @SerializedName("preferredLanguage")
    @Expose
    var preferredLanguage: String? = null
    @SerializedName("surname")
    @Expose
    var surname: String? = null
    @SerializedName("userPrincipalName")
    @Expose
    var userPrincipalName: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null

    var userName: String? = null

    var password: String? = null
//    var storeList: MutableList<StoreModels>? = null

    @SerializedName("error")
    @Expose
    var error: MSGraphError? = null

    inner class MSGraphError {

        @SerializedName("code")
        @Expose
        var code: String? = null
        @SerializedName("message")
        @Expose
        var message: String? = null
    }

    fun getFullProfileName(): String {
        val stringBuilder = StringBuilder()

        if (displayName != null) {
            stringBuilder.append(displayName)
        }
        if (surname != null) {
            stringBuilder.append(" ")
            stringBuilder.append(surname)
        }
        return stringBuilder.toString()
    }
}

