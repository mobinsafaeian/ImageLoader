package com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IPResponse {
    @SerializedName("ip")
    @Expose
    lateinit var ip:String

    @SerializedName("city")
    @Expose
    lateinit var city:String


    @SerializedName("region")
    @Expose
    lateinit var region:String

    @SerializedName("region_code")
    @Expose
    var region_code:String? = null

    @SerializedName("country")
    @Expose
    lateinit var country:String

    @SerializedName("country_name")
    @Expose
    lateinit var country_name:String

    @SerializedName("continent_code")
    @Expose
    lateinit var continent_code:String

    @SerializedName("in_eu")
    @Expose
    var in_eu:Boolean = false

    @SerializedName("postal")
    @Expose
    var postal:String? = null

    @SerializedName("latitude")
    @Expose
    var latitude:Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude:Double? = null

    @SerializedName("timezone")
    @Expose
    var timezone:String? = null

    @SerializedName("utc_offset")
    @Expose
    var utc_offset:String? = null

    @SerializedName("country_calling_code")
    @Expose
    var country_calling_code:String? = null

    @SerializedName("currency")
    @Expose
    var currency:String? = null

    @SerializedName("languages")
    @Expose
    var languages:String? = null

    @SerializedName("asn")
    @Expose
    var asn:String? = null

    @SerializedName("org")
    @Expose
    var org:String? = null
}