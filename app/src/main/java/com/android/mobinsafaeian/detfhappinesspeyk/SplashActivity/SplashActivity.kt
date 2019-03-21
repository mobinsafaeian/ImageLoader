package com.android.mobinsafaeian.detfhappinesspeyk.SplashActivity


import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.mobinsafaeian.detfhappinesspeyk.MainPackage.BaseActivity
import com.android.mobinsafaeian.detfhappinesspeyk.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation: Animation = AnimationUtils.loadAnimation(applicationContext , R.anim.zoom_in)
        maintxt.setOnClickListener { it
            maintxt.startAnimation(animation)
        }
    }
}
