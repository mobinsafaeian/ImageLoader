package com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse {
    @SerializedName("batchcomplete")
    @Expose
    var batchcomplete:String? = null

    @SerializedName("continue")
    @Expose
    var continue1: continueResponse? = null

    @SerializedName("query")
    @Expose
    lateinit var query: queryResponse
}