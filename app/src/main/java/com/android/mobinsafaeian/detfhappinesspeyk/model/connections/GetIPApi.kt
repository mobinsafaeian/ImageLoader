package com.android.mobinsafaeian.detfhappinesspeyk.model.connections

import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.IPResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface GetIPApi {
    @GET("/json/")
    fun getData(): Observable<IPResponse>
}