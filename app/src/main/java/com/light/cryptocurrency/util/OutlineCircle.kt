package com.light.cryptocurrency.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.NonNull
import kotlin.math.min

class OutlineCircle : ViewOutlineProvider() {

    companion object {
        fun apply(@NonNull view: View) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val minSize = Math.min(view.width, view.height)
                    outline.setRoundRect(0, 0, view.width, view.height, minSize / 2f)
                }
            }
            view.clipToOutline = true
        }
    }

    override fun getOutline(view: View, outline: Outline) {
        val minSize = min(view.width, view.height)
        outline.setRoundRect(0, 0, view.width, view.height, minSize / 2f)
    }
}