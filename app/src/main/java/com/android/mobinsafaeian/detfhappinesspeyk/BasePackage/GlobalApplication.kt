package com.android.mobinsafaeian.detfhappinesspeyk.BasePackage


import android.app.Application
import android.content.Context

class GlobalApplication: Application() {
    // this static object helps us to get the context in any class
    companion object {
        private lateinit var context:Context

        fun getContext():Context {
            return context
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}