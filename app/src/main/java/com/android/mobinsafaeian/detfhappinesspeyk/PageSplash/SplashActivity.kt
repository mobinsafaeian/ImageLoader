package com.android.mobinsafaeian.detfhappinesspeyk.PageSplash



import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.android.mobinsafaeian.detfhappinesspeyk.PageMain.MainActivity
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.BaseActivity
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.Utils
import com.android.mobinsafaeian.detfhappinesspeyk.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {

    //definitions
    private lateinit var zoomInAnimation:Animation
    private lateinit var translateUp:Animation
    private lateinit var utils: Utils
    private lateinit var animateTextSize:ValueAnimator
    private var animatedValue:Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //hide action bar
        supportActionBar?.hide()

        //initializations
        init()

        //initAnimations
        initAnimations()

        //waiting 3 seconds and startActivity function should call to pass to Activity with an transition
        Handler().postDelayed({
            startActivity(Intent(applicationContext , MainActivity::class.java))
        }, 3000)

    }

    /**
     * initializations
     */
    private fun init() {
        zoomInAnimation = AnimationUtils.loadAnimation(applicationContext , R.anim.zoom_in)
        translateUp = AnimationUtils.loadAnimation(applicationContext , R.anim.translate_up)
        utils = Utils(this)
        utils.setFont(text_app_logo)
    }


    /**
     * initAnimations starts to do animations for stand
     */
    private fun initAnimations() {
        runOnUiThread {
            text_container.startAnimation(translateUp)
            text_app_logo.startAnimation(translateUp)
            translateUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }
                override fun onAnimationStart(p0: Animation?) {
                }
                override fun onAnimationEnd(arg0: Animation) {
                        zoomInAnimationFun()
                }
            })
        }

    }

    /**
     * zoomInAnimation perform zoom animation on the text and it's container for having better splash page :D
     * this method will be called when the translate animation finished
     */
    fun zoomInAnimationFun(){
        text_container.startAnimation(zoomInAnimation)
        animateTextSize(22f , 72f , text_app_logo , 300)
        zoomInAnimation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationStart(p0: Animation?) {}
            //when zoomInAnimation done , animateTextSize gives a new value and the animateTextSize animation perform on a new TextView
            override fun onAnimationEnd(p0: Animation?) {
                desc_app_logo.text = "Mobin Safaeian"
                animateTextSize(-1f , 22f , desc_app_logo , 200)
            }
        })
    }

    /**
     * animateTextSize animates the size of text of an particular TextView
     * @param initialValue is the first value
     * @param finalValue is the last and final value
     * @param view is the view that the animation should perform on it
     * @param milliSec is the milli seconds that takes to done animation
     */
    fun animateTextSize(initialValue:Float, finalValue:Float, view: TextView, milliSec:Long){
        animateTextSize = ValueAnimator.ofFloat(initialValue , finalValue)
        animateTextSize.addUpdateListener {
            animatedValue = it.animatedValue as Float
            view.textSize = animatedValue
            view.requestLayout()
        }
        animateTextSize.duration = milliSec
        animateTextSize.start()
    }

}

