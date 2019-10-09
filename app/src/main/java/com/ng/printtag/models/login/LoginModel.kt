package com.ng.printtag.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("success")
    @Expose
    var success: Boolean = false

    @SerializedName("msg")
    @Expose
    var msg : String?= null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    var userName: String? = null

    var password: String? = null
//    var storeList: MutableList<StoreModels>? = null





    inner class Data {
        @SerializedName("userId")
        @Expose
        var userId: Int = 0
        @SerializedName("username")
        @Expose
        var username: String? = null
        @SerializedName("firstName")
        @Expose
        var firstName: String? = null
        @SerializedName("lastName")
        @Expose
        var lastName: Any? = null
        @SerializedName("password")
        @Expose
        var password: String? = null
        @SerializedName("session")
        @Expose
        var session: String? = null
        @SerializedName("roles")
        @Expose
        var roles: ArrayList<String>? = null


        fun getFullProfileName(): String {
            val stringBuilder = StringBuilder()

            if (firstName != null) {
                stringBuilder.append(firstName)
            }
            if (lastName != null) {
                stringBuilder.append(" ")
                stringBuilder.append(lastName)
            }
            return stringBuilder.toString()
        }
    }
}

