package ng.pdplib.models.generic

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ng.printtag.models.GenericResultModel

class GenericRootResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("data")
    @Expose
    val result: GenericResultModel? = null
}