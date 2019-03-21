package com.android.mobinsafaeian.detfhappinesspeyk.BasePackage

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

class Utils {
    private val font: Typeface
    private val context: Context
    constructor(context:Context){
        this.context = context
        font = Typeface.createFromAsset(this.context.assets , "fonts/typo_hoop_bold_demo.otf")
    }

    fun setFont(view:TextView){
        view.typeface = font
    }

}