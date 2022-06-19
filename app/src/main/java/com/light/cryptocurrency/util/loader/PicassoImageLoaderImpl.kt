package com.light.cryptocurrency.util.loader

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicassoImageLoaderImpl @Inject constructor(
    private val picasso: Picasso
) : ImageLoader {

    override fun load(url: String): ImageLoader.ImageRequest =
        PicassoImageRequest(picasso.load(url))


    companion object {
        class PicassoImageRequest(private val request: RequestCreator) : ImageLoader.ImageRequest {

            override fun into(view: ImageView) {
                request.into(view)
            }
        }
    }
}