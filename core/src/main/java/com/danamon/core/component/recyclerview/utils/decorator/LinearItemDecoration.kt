package com.danamon.core.component.recyclerview.utils.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration(private val spacing: Int, private var orientation: LinearOrientationEnum, private val isAll: Boolean) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (orientation == LinearOrientationEnum.HORIZONTAL) {
            with(outRect) {
                if (position == 0) {
                    left = spacing
                }
                top = if (isAll) spacing else 0
                bottom = if (isAll) spacing else 0
                right = spacing
            }
        } else {
            with(outRect) {
                if (position == 0) {
                    top = spacing
                }
                left = if (isAll) spacing else 0
                right = if (isAll) spacing else 0
                bottom = spacing
            }
        }
    }
}