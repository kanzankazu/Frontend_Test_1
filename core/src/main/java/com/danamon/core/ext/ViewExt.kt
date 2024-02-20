package com.danamon.core.ext

import android.view.View
import android.view.animation.AnimationUtils
import com.danamon.core.util.OnSingleClickListener

fun View.visible(anim: Int = 0) {
    if (!isVisible()) {
        if (anim != 0 && isGone()) {
            val loadAnimation = AnimationUtils.loadAnimation(this.context, anim)
            startAnimation(loadAnimation)
        }
        //TransitionManager.beginDelayedTransition(parent as ViewGroup)
        visibility = View.VISIBLE
    }
}

fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun View.invisible(anim: Int = 0) {
    if (!isInvisible()) {
        if (anim != 0 && isVisible()) {
            val loadAnimation = AnimationUtils.loadAnimation(this.context, anim)
            startAnimation(loadAnimation)
        }
        //TransitionManager.beginDelayedTransition(parent as ViewGroup)
        visibility = View.INVISIBLE
    }
}

fun View.isInvisible(): Boolean = this.visibility == View.INVISIBLE

fun View.gone(anim: Int = 0) {
    if (!isGone()) {
        if (anim != 0 && isVisible()) {
            val loadAnimation = AnimationUtils.loadAnimation(this.context, anim)
            startAnimation(loadAnimation)
        }
        //TransitionManager.beginDelayedTransition(parent as ViewGroup)
        visibility = View.GONE
    }
}

fun View.isGone(): Boolean = this.visibility == View.GONE

fun View.visibleView(b: Boolean, isInvisible: Boolean = false): Boolean {
    return if (b) {
        visible()
        true
    } else {
        if (isInvisible) invisible() else gone()
        false
    }
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}