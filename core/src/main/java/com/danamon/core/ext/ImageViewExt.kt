package com.danamon.core.ext

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.danamon.core.R

fun ImageView.loadImage(
    url: String,
    @DrawableRes error: Int = R.drawable.ic_person,
) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide
        .with(this.context)
        .load(url)
        .error(error)
        .into(this)
}