package com.android.mobinsafaeian.detfhappinesspeyk.model.connections

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        fun create(): GetIPApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(BaseUrls.IPBaseUrl)
                .build()

            return retrofit.create(GetIPApi::class.java)
        }
    }
}