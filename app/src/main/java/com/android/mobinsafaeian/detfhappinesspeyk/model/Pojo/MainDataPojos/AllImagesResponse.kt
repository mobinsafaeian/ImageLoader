package com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllImagesResponse {
    @SerializedName("name")
    @Expose
    lateinit var name:String

    @SerializedName("timestamp")
    @Expose
    lateinit var timestamp:String

    @SerializedName("url")
    @Expose
    lateinit var url:String

    @SerializedName("descriptionurl")
    @Expose
    lateinit var descriptionurl:String

    @SerializedName("descriptionshorturl")
    @Expose
    lateinit var descriptionshorturl:String

    @SerializedName("ns")
    @Expose
    var ns:Int = 0

    @SerializedName("title")
    @Expose
    lateinit var title:String
}