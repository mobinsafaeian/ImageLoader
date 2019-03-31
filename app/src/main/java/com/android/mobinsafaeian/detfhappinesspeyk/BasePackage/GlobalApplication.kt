package com.android.mobinsafaeian.detfhappinesspeyk.BasePackage

import android.app.Application
import android.content.Context

class GlobalApplication: Application() {

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