package com.chuno.nachive.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object BindingAdapter {

    @BindingAdapter("setImageFromDevice")
    @JvmStatic
    fun setImageFromDevice(view : ImageView, url : String) {
        Glide.with(view.context).load(url).into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(
                a_resource: Drawable,
                a_transition: Transition<in Drawable>?
            ) {
                view.background = a_resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
    }
}