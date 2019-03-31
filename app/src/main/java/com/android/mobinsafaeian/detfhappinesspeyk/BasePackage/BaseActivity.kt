package com.android.mobinsafaeian.detfhappinesspeyk.BasePackage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window

/**
 * this class is created for other activities that extend this class
 * the same features that must be written in all activity , are written here
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //because i did'nt enable transition mode in my theme , i need to write this code to enable enter & exit transition
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            exitTransition = Explode()
        }

        // set orientation only portrait for all activities that extends this class
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //show a simple message
    protected fun SimpleDialog(title:String , message:String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .create()
            .show()
    }
    //show an exit dialog
    protected fun exitDialog(title:String, message:String, positiveMessage:String, negativeMessage:String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveMessage) { dialogInterface, i ->
                moveTaskToBack(true)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(1)
            }
            .setNegativeButton(negativeMessage) {dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

}
