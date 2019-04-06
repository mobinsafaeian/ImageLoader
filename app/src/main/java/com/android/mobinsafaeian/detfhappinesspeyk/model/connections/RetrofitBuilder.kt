package com.android.mobinsafaeian.detfhappinesspeyk.model.connections

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        object RetrofitObj{
            lateinit var retrofit:Retrofit
        }
        fun create(BASE_URL:String): GetApisInterface {

            RetrofitObj.retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .client(OkHttpClient())
                .baseUrl(BASE_URL)
                .build()

            return RetrofitObj.retrofit.create(GetApisInterface::class.java)
        }
    }
}