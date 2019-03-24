package com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class queryResponse {
    @SerializedName("allimages")
    @Expose
    lateinit var allimages:List<AllImagesResponse>
}