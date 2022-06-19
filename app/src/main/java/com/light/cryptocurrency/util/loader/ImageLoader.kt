package com.light.cryptocurrency.util.loader

import android.widget.ImageView

interface ImageLoader {

    fun load(url: String): ImageRequest

    interface ImageRequest {

        fun into(view: ImageView)
    }
}