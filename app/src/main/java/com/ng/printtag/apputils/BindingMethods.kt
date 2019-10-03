package com.ng.printtag.apputils

import android.graphics.drawable.Drawable
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.ng.printtag.R

class BindingMethods {
    companion object {

        /** Use to set server image or local image with glide
         *  @param imageView ImageView : image view to set the image
         *  @param url String : url in base64 and converted into drawable
         *  @param placeholder : Drawable : set drawable image till server url image not load
         */
        @BindingAdapter(value = ["circleImage", "placeholder"], requireAll = false)
        @JvmStatic
        fun setCircleImage(imageView: ImageView, url: String?, placeholder: Drawable) {
            if (!url.isNullOrEmpty()) {
                when {
                    url.contains("http") -> {
                        Glide.with(imageView.context).load(url).apply(
                            RequestOptions.circleCropTransform().placeholder(placeholder).error(placeholder)
                        ).into(imageView)
                    }
                    else -> {
                        Glide.with(imageView.context).load(url).thumbnail(0.1f).apply(
                            RequestOptions.circleCropTransform().signature(ObjectKey(System.currentTimeMillis())).placeholder(
                                placeholder
                            ).error(placeholder).skipMemoryCache(true)
                        ).into(imageView)
                    }

                }
            } else {
                imageView.setImageResource(R.mipmap.ic_default_photo)
            }
        }

        @BindingAdapter(value = ["squareImage", "placeholder"], requireAll = false)
        @JvmStatic
        fun setSquareImage(imageView: ImageView, url: String?, placeholder: Drawable) {
            if (!url.isNullOrEmpty()) {
                when {
                    url.contains("http") ->
                        Glide.with(imageView.context).load(url).apply(
                            RequestOptions().error(placeholder).placeholder(placeholder).error(placeholder)
                        ).into(imageView)
                    else ->
                        Glide.with(imageView.context).load(url).thumbnail(0.1f).apply(
                            RequestOptions().signature(ObjectKey(System.currentTimeMillis())).placeholder(placeholder).error(
                                placeholder
                            ).skipMemoryCache(true)
                        ).into(imageView)
                }
            } else {
                imageView.setImageDrawable(placeholder)
            }
        }

        @BindingAdapter(value = ["imageUrl", "progressbar", "placeholder", "isCircle"], requireAll = false)
        @JvmStatic
        fun setServerImage(
            imageView: ImageView,
            url: String?,
            progressBar: ProgressBar,
            placeholder: Drawable,
            isCircle: Boolean
        ) {
            if (!url.isNullOrEmpty()) {
                val requestObject: RequestOptions = if (isCircle)
                    RequestOptions.circleCropTransform().error(placeholder).placeholder(placeholder).diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )
                else {
                    RequestOptions().error(placeholder).placeholder(placeholder).diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )
                }
                when {
                    url.startsWith("http") -> {
//                        progressBar.visibility = VISIBLE
                        Glide.with(imageView.context).load(url).apply(
                            requestObject

                        ).listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = GONE
                                return false
                            }

                        }).into(imageView)
                    }
                    else -> {
                        progressBar.visibility = GONE
                        Glide.with(imageView.context).load(url).thumbnail(0.1f).apply(
                            requestObject.signature(ObjectKey(System.currentTimeMillis())).placeholder(placeholder).error(
                                placeholder
                            ).skipMemoryCache(true)
                        ).into(imageView)
                    }
                }
            } else {
                progressBar.visibility = GONE
                imageView.setImageDrawable(placeholder)
            }
        }

        @BindingAdapter(value = ["lookImageUrl", "progressbar", "placeholder", "textView"], requireAll = false)
        @JvmStatic
        fun setLookImage(
            imageView: ImageView,
            url: String?,
            progressBar: ProgressBar,
            placeholder: Drawable,
            refreshImageView: AppCompatImageView
        ) {
            if (!url.isNullOrEmpty()) {
                val requestObject: RequestOptions =
                    RequestOptions().error(placeholder).diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )

                when {
                    url.startsWith("http") -> {
                        Glide.with(imageView.context).load(url).apply(
                            requestObject
                        ).listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = GONE
                                refreshImageView.visibility = VISIBLE
                                refreshImageView.isClickable = true
                                (refreshImageView.parent as RelativeLayout).invalidate()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = GONE
                                refreshImageView.visibility = GONE
                                refreshImageView.isClickable = false
                                return false
                            }

                        }).into(imageView)
                    }
                    else -> {
                        progressBar.visibility = GONE
                        Glide.with(imageView.context).load(url).thumbnail(0.1f).apply(
                            requestObject.signature(ObjectKey(System.currentTimeMillis())).placeholder(placeholder).error(
                                placeholder
                            ).skipMemoryCache(true)
                        ).into(imageView)
                    }
                }
            } else {
                progressBar.visibility = GONE
                imageView.setImageDrawable(placeholder)
            }
        }
    }
}