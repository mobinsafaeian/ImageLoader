package com.android.mobinsafaeian.detfhappinesspeyk.MainPackage

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * this class is created for other activities that extend this class
 * the same features that must be written in all activity , are written here
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set orientation only portrait for all activities that extends this class
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
