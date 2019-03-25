package com.android.mobinsafaeian.detfhappinesspeyk.customizations

import android.content.Context
import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import android.renderscript.Allocation
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.RenderScript
import android.renderscript.Element


class BlurTransformations(private val context: Context? , private val radius:Float) : Transformation {

    override fun key(): String {
        return "Blur with radius $radius"
    }

    override fun transform(source: Bitmap?): Bitmap {
        val blurredBitmap = Bitmap.createBitmap(
            source!!.width,
            source.height, Bitmap.Config.ARGB_8888
        )
        // Initialize RenderScript and the script to be used
        val renderScript = RenderScript.create(context)
        val script = ScriptIntrinsicBlur.create(
            renderScript,
            Element.U8_4(renderScript)
        )
        // Allocate memory for Renderscript to work with
        val input = Allocation.createFromBitmap(renderScript, source)
        val output = Allocation
            .createFromBitmap(renderScript, blurredBitmap)

        script.setInput(input)
        script.setRadius(radius)
        script.forEach(output)
        output.copyTo(blurredBitmap)

        renderScript.destroy()
        source.recycle()
        return blurredBitmap
    }
}