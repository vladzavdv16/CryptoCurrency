package com.light.cryptocurrency.widget

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlin.math.min

class OutlineCircle: ViewOutlineProvider() {

    companion object {
        fun apply(view: View) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val minSize = min(view.width, view.height)
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