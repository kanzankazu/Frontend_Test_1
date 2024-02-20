package com.danamon.core.util

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class SpannableClick(
    textView: TextView,
    private val isUnderlined: Boolean = false,
    private val typeFace: Typeface = Typeface.DEFAULT,
    private val onClick: () -> Unit,
) : ClickableSpan() {

    init {
        textView.apply {
            highlightColor = Color.TRANSPARENT
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun onClick(p0: View) {
        onClick()
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.apply {
            isUnderlineText = isUnderlined
            typeface = typeFace
        }
    }

}
