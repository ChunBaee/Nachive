package com.chuno.nachive.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @BindingAdapter("setImageFromDevice")
    @JvmStatic
    fun setImageFromDevice(view : ImageView, url : String) {
        Glide.with(view.context).load(url).into(view)
    }
}