package com.ng.printtag.models

import android.content.Context
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap


class UserDetailsModel {

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("middleName")
    var middleName: String? = null

    @SerializedName("secondLastName")
    var secondLastName: String? = null

    @SerializedName("dob")
    var dob: String? = null

    @SerializedName("countryofBirth")
    var countryBirth: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("optin")
    var optIns: Any? = null

    @SerializedName("isActive")
    var isActive: String? = null

    @SerializedName("isAgreed")
    var isAgreed: Boolean? = null


    @SerializedName("modificationDate")
    var modificationDate: String = ""

    @SerializedName("prosperaId")
    var prosperaId: String = ""


    @SerializedName("customerRefNo", alternate = ["customerReferenceNumber"])
    var customerRefNo: String? = ""

    @SerializedName("primaryPhoneNumber")
    var primaryPhoneNumber: String? = null

    @SerializedName("email")
    var email: String? = null

    var masterId: String = ""
    var masterType: String = ""

    @SerializedName("ofacStatus")
    var ofacStatus: Boolean? = null

    @SerializedName("status")
    var userStatus: Any? = null

    @SerializedName("qrCode")
    var qrCode: String? = null

    @SerializedName("phoneType")
    var phoneType: String? = null

    @SerializedName("miFamiliaCard")
    var miFamiliaCard: String? = null

    @SerializedName("lastTransactionDate")
    var lastTransactionDate: String? = null

    @SerializedName("creationDate")
    var creationDate: String = ""

    @SerializedName("dialCode")
    var phoneCode: String? = null

    @SerializedName("relationship")
    var relationship: String? = null

    @SerializedName("checkcashingsettings")
    var checkSettings: Boolean? = null

    @SerializedName("couponsharingsettings")
    var couponSettings: Boolean? = null

    @SerializedName("lastOptinUpdate")
    var lastOptInUpdate: String? = null

    @SerializedName("ofacResultStatus")
    var ofacResultStatus: String? = null

    @SerializedName("kairosResultsStatus")
    var kairosResultsStatus: String? = null

    @SerializedName("ssn")
    var ssNo: String? = null

    @SerializedName("itin")
    var itin: String? = null

    @SerializedName("referenceDialCode")
    var referenceDialCode: String? = null

    @SerializedName("referenceContactNumber")
    var referenceContactNumber: String? = null

    @SerializedName("registrationStage")
    var registrationStage: String? = null

    @SerializedName("isPhoneVerified")
    var isPhoneVerified: Boolean? = null

    @SerializedName("prosperaCardNumber")
    var cardNumber: String? = null

    @SerializedName("prosperaCardIssueDate")
    var prosperaCardIssueDate: String? = null

    @SerializedName("profilePicture")
    var imageModel: Any? = null



    @SerializedName("source")
    var source: String? = null

    var localImagePath: String = ""
    var localStage: String? = null
    var dataType: String = ""
    var verifyCode: String? = null
    var idDetailBy: Int = 0
    var referenceMaskedContactNumber: String = ""
    var genderSelected: Int = 0
    var temPath: String = ""
    var profileSentOnServer: Boolean = false
    var profileRecognize: Boolean = false
    var profileEnroll: Boolean = false
    var isPhoneOrLandLineSkip = false

    fun getStatus(): String {
        if (userStatus != null) {
            if (userStatus is String) {
                return userStatus.toString()
            } else if (userStatus is LinkedTreeMap<*, *>) {
                val value = (userStatus as LinkedTreeMap<*, *>)
                if (value.contains("selected")) {
                    return value["selected"].toString()
                }
            }
        }
        return ""
    }

    fun getImageUrl(): String {
        if (imageModel != null) {
            if (imageModel is String) {
                return imageModel.toString()
            } else if (imageModel is LinkedTreeMap<*, *>) {
                val value = (imageModel as LinkedTreeMap<*, *>)
                if (value.contains("imageUrl")) {
                    return value["imageUrl"].toString()
                }
                if (value.contains("profilePicture")) {
                    return value["profilePicture"].toString()
                }
            }
        }
        return ""
    }

    fun getImageDate(): String {
        if (imageModel != null) {
            if (imageModel is LinkedTreeMap<*, *>) {
                val value = (imageModel as LinkedTreeMap<*, *>)
                if (value.contains("createdDate")) {
                    return value["createdDate"].toString()
                }
            }
        }
        return ""
    }

    fun getFullName(): String {
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

    fun getFullProfileName(): String {
        val stringBuilder = StringBuilder()

        if (firstName != null) {
            stringBuilder.append(firstName)
        }
        if (middleName != null) {
            stringBuilder.append(" ")
            stringBuilder.append(middleName)
        }
        if (lastName != null) {
            stringBuilder.append(" ")
            stringBuilder.append(lastName)
        }
        if (secondLastName != null) {
            stringBuilder.append(" ")
            stringBuilder.append(secondLastName)
        }
        return stringBuilder.toString()
    }

}

