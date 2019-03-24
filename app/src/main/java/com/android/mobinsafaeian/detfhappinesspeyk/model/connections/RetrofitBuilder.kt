package com.android.mobinsafaeian.detfhappinesspeyk.model.connections

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        fun create(BASE_URL:String): GetApisInterface {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .client(OkHttpClient())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GetApisInterface::class.java)
        }
    }
}