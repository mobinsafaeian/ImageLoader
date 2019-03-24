package com.android.mobinsafaeian.detfhappinesspeyk.model.connections

import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.IPPojos.IPResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface GetApisInterface {
    @GET("/json/")
    fun getIp(): Observable<IPResponse>

    @GET("/w/api.php?action=query&format=json&list=allimages&aifrom=Graffiti_000&ailimit=10")
    fun getData(): Observable<DataResponse>
}