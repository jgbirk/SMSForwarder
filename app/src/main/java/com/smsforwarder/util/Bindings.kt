package com.smsforwarder.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("image")
fun ImageView.setImageRes(@DrawableRes drawable: Int) {
    setImageResource(drawable)
}