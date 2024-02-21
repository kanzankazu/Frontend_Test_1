package com.danamon.core.ext

import androidx.core.widget.NestedScrollView

fun NestedScrollView.loadMoreListener(onScroll: (dx: Int, dy: Int) -> Unit = { _, _ -> }, onBottomListener: (nsv: NestedScrollView) -> Unit) {
    this.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, scrollX, scrollY, _, oldScrollY ->
        onScroll(scrollX, scrollY)
        if (this.getChildAt(this.childCount - 1) != null) {
            if (scrollY >= this.getChildAt(this.childCount - 1).measuredHeight - this.measuredHeight && scrollY > oldScrollY) {
                onBottomListener(this)
            }
        }
    })
}