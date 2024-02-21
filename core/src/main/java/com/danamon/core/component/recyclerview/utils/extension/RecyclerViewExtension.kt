package com.danamon.core.component.recyclerview.utils.extension

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.danamon.core.R
import com.danamon.core.component.recyclerview.utils.decorator.GridItemDecoration
import com.danamon.core.component.recyclerview.utils.decorator.LinearHorizontalOffsetDecoration
import com.danamon.core.component.recyclerview.utils.decorator.LinearHorizontalSpacingDecoration
import com.danamon.core.component.recyclerview.utils.decorator.LinearItemDecoration
import com.danamon.core.component.recyclerview.utils.decorator.LinearOrientationEnum
import com.danamon.core.component.recyclerview.utils.decorator.ProminentHorizontalLayoutManager
import com.danamon.core.component.recyclerview.utils.decorator.SwipeRightLeftDecoration
import com.danamon.core.component.recyclerview.utils.listener.SnapOnScrollListener

/**
 * @param layoutManagerIndex
 * 0=Linear_vertical
 * 1=Linear_horizontal
 * 2=Grid_vertical
 * 3=Grid_horizontal
 * 4=StaggeredGrid_vertical
 * 5=StaggeredGrid_horizontal
 */
fun RecyclerView.setRecyclerView(
    adapterParam: RecyclerView.Adapter<*>,
    layoutManagerIndex: RecyclerViewLayoutType = RecyclerViewLayoutType.RECYCLER_VIEW_LIN_VERTICAL,
    spanCountGrid: Int = 2,
    spacingItemDecoration: Int = 8,
    isNestedScrollingEnabledParam: Boolean = false,
    isAll: Boolean = true,
    onScrollListener: RecyclerView.OnScrollListener? = null,
    @AnimRes animRes: Int = R.anim.layout_animation_fall_down,
    minScaleDistanceFactor: Float = 1f,
    scaleDownBy: Float = 0f,
    position: Int = -1,
    snapHelper: SnapHelper? = null,
    onSnapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener = object : SnapOnScrollListener.OnSnapPositionChangeListener {
        override fun onSnapPositionChange(position: Int) = Unit
    },
): RecyclerView.LayoutManager? {
    isNestedScrollingEnabled = isNestedScrollingEnabledParam
    overScrollMode = View.OVER_SCROLL_NEVER
    adapter = adapterParam
    setLayoutManagerRecyclerview(layoutManagerIndex, spanCountGrid, minScaleDistanceFactor, scaleDownBy)
    setItemDecoration(layoutManagerIndex, spacingItemDecoration, spanCountGrid, isAll)
    setSnapHelperRecyclerview(snapHelper, onSnapPositionChangeListener)
    setStartPositionRecyclerview(layoutManager, snapHelper, position)
    setAnimationRecyclerview(context, animRes)

    onScrollListener?.let { addOnScrollListener(it) }

    return layoutManager
}

private fun RecyclerView.setLayoutManagerRecyclerview(layoutManagerIndex: RecyclerViewLayoutType, spanCountGrid: Int, minScaleDistanceFactor: Float, scaleDownBy: Float) {
    layoutManager = when (layoutManagerIndex) {
        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_VERTICAL -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL -> LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        RecyclerViewLayoutType.RECYCLER_VIEW_GRID_VERTICAL -> GridLayoutManager(context, spanCountGrid, GridLayoutManager.VERTICAL, false)
        RecyclerViewLayoutType.RECYCLER_VIEW_GRID_HORIZONTAL -> GridLayoutManager(context, spanCountGrid, GridLayoutManager.HORIZONTAL, false)
        RecyclerViewLayoutType.RECYCLER_VIEW_STAG_VERTICAL -> StaggeredGridLayoutManager(spanCountGrid, StaggeredGridLayoutManager.VERTICAL)
        RecyclerViewLayoutType.RECYCLER_VIEW_STAG_HORIZONTAL -> StaggeredGridLayoutManager(spanCountGrid, StaggeredGridLayoutManager.HORIZONTAL)
        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL_SPAN -> ProminentHorizontalLayoutManager(context, minScaleDistanceFactor, scaleDownBy)
        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL_SPAN_OFFSET -> ProminentHorizontalLayoutManager(context, minScaleDistanceFactor, scaleDownBy)
    }
}

private fun RecyclerView.setItemDecoration(layoutManagerIndex: RecyclerViewLayoutType, spacingItemDecoration: Int, spanCountGrid: Int, isAll: Boolean) {
    when (layoutManagerIndex) {
        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_VERTICAL -> {
            addItemDecoration(LinearItemDecoration(spacingItemDecoration.dpToPx(), LinearOrientationEnum.VERTICAL, isAll))
        }

        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL, RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL_SPAN -> {
            addItemDecoration(LinearItemDecoration(spacingItemDecoration.dpToPx(), LinearOrientationEnum.HORIZONTAL, isAll))
        }

        RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL_SPAN_OFFSET -> {
            addItemDecoration(LinearHorizontalSpacingDecoration(spacingItemDecoration.dpToPx()))
            addItemDecoration(LinearHorizontalOffsetDecoration())
        }

        else -> {
            addItemDecoration(GridItemDecoration(spanCountGrid, spacingItemDecoration.dpToPx(), true))
        }
    }
}

private fun RecyclerView.setSnapHelperRecyclerview(snapHelper: SnapHelper?, onSnapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener) {
    if (snapHelper != null) {
        snapHelper.attachToRecyclerView(this)
        setSnapHelperListener(snapHelper, onSnapPositionChangeListener)
    }
}

private fun RecyclerView.setAnimationRecyclerview(context: Context, animRes: Int) {
    if (animRes != -1) {
        val layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, animRes)
        layoutAnimation = layoutAnimationController
    }
}

private fun RecyclerView.setStartPositionRecyclerview(layoutManager: RecyclerView.LayoutManager?, snapHelper: SnapHelper?, position: Int) {
    if (layoutManager != null &&
        layoutManager is ProminentHorizontalLayoutManager &&
        snapHelper != null &&
        snapHelper is LinearSnapHelper &&
        position != -1
    ) {
        layoutManager.scrollToPosition(position)

        doOnPreDraw {
            val targetView = layoutManager.findViewByPosition(position) ?: return@doOnPreDraw
            val distanceToFinalSnap = snapHelper.calculateDistanceToFinalSnap(layoutManager, targetView) ?: return@doOnPreDraw

            layoutManager.scrollToPositionWithOffset(position, -distanceToFinalSnap[0])
        }
    }
}

fun RecyclerView.setSnapHelper(snapHelper: SnapHelper) = snapHelper.attachToRecyclerView(this)

fun RecyclerView.setSnapHelperWithListener(snapHelper: SnapHelper, onSnapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener, behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener = SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

fun RecyclerView.setSnapHelperListener(snapHelper: SnapHelper, onSnapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener, behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL) {
    val snapOnScrollListener = SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

fun RecyclerView.getSnapPosition(snapHelper: SnapHelper): Int {
    val layoutManager = layoutManager ?: return RecyclerView.NO_POSITION
    val findSnapView = snapHelper.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(findSnapView)
}

fun RecyclerView.loadMore(isLoadMore: Boolean, currentPage: Int, lastPage: Int, onBottomListener: (mCurrentPage: Int, mIsLoadMore: Boolean) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!isLoadMore && (lastPage != currentPage)) {
                val mLayoutManager = this@loadMore.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = mLayoutManager.childCount
                val pastVisibleItems: Int = mLayoutManager.findFirstVisibleItemPosition()
                val totalItemCount: Int = mLayoutManager.itemCount

                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    Log.d("Lihat KanzanKazu", "onScrolled ReviewHistoryFragment ${pastVisibleItems + visibleItemCount >= totalItemCount}")
                    Log.d("Lihat KanzanKazu", "onScrollStateChanged ${pastVisibleItems + visibleItemCount >= totalItemCount}")

                    var mCurrentPage = currentPage
                    val mIsLoadMore = true
                    mCurrentPage += 1

                    onBottomListener(mCurrentPage, mIsLoadMore)
                }
            }
        }
    })
}

fun RecyclerView.swipeRightLeft(drawObject: SwipeRightLeftDecoration.DrawObject, listener: SwipeRightLeftDecoration.Listener) {
    SwipeRightLeftDecoration(this, drawObject, listener)
}

interface LoadMoreListener {
    fun onBottomListener(currentPage: Int, isLoadMore: Boolean)
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int = recyclerView.getSnapPosition(this)

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun RecyclerView.smoothScrollToCenteredPosition(position: Int) {
    val smoothScroller = object : LinearSmoothScroller(context) {
        override fun calculateDxToMakeVisible(view: View?, snapPreference: Int): Int {
            val dxToStart = super.calculateDxToMakeVisible(view, SNAP_TO_START)
            val dxToEnd = super.calculateDxToMakeVisible(view, SNAP_TO_END)

            return (dxToStart + dxToEnd) / 2
        }
    }

    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}
