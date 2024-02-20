package com.danamon.core.component.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.danamon.core.R
import com.danamon.core.component.recyclerview.utils.extension.RecyclerViewLayoutType
import com.danamon.core.component.recyclerview.utils.extension.setRecyclerView
import com.danamon.core.component.recyclerview.utils.listener.SnapOnScrollListener

class KanzanRecyclerviewComponent @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {
    private var _animRes: Int = R.anim.layout_animation_fall_down

    init {
        attrs?.let { extractAttributes(it) }
    }

    fun setRecyclerLinearVertical(
        recyclerAdapter: Adapter<ViewHolder>,
        spacingItemDecoration: Int,
        isAll: Boolean = true,
        @AnimRes animRes: Int = _animRes,
    ) {
        _animRes = animRes
        setRecyclerView(
            recyclerAdapter,
            RecyclerViewLayoutType.RECYCLER_VIEW_LIN_VERTICAL,
            spacingItemDecoration = spacingItemDecoration,
            isNestedScrollingEnabledParam = false,
            isAll = isAll,
            animRes = _animRes,
        )
    }

    fun setRecyclerLinearHorizontal(
        recyclerAdapter: Adapter<ViewHolder>,
        spacingItemDecoration: Int,
        isAll: Boolean = true,
        @AnimRes animRes: Int = _animRes,
    ) {
        _animRes = animRes
        setRecyclerView(
            recyclerAdapter, RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL,
            spacingItemDecoration = spacingItemDecoration,
            isNestedScrollingEnabledParam = false,
            isAll = isAll,
            animRes = _animRes,
        )
    }

    fun setRecyclerLinearHorizontalSnap(
        recyclerAdapter: Adapter<ViewHolder>,
        spacingItemDecoration: Int,
        isOffset: Boolean,
        isAll: Boolean = true,
        @AnimRes animRes: Int = _animRes,
        minScaleDistanceFactor: Float = 1f,
        scaleDownBy: Float = 0f,
        snapHelperNew: SnapHelper = LinearSnapHelper(),
        defaultPosition: Int = -1,
        listener: (position: Int) -> Unit,
    ) {
        _animRes = animRes
        //setItemViewCacheSize(4)
        setRecyclerView(
            recyclerAdapter,
            if (isOffset) RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL_SPAN_OFFSET else
                RecyclerViewLayoutType.RECYCLER_VIEW_LIN_HORIZONTAL,
            spacingItemDecoration = spacingItemDecoration,
            isNestedScrollingEnabledParam = false,
            isAll = isAll,
            animRes = _animRes,
            minScaleDistanceFactor = minScaleDistanceFactor,
            scaleDownBy = scaleDownBy,
            position = defaultPosition,
            snapHelper = snapHelperNew,
            onSnapPositionChangeListener = object : SnapOnScrollListener.OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    listener.invoke(position)
                }
            },
        )
    }

    fun setRecyclerGrid(
        recyclerAdapter: Adapter<ViewHolder>,
        spanCountGrid: Int,
        spacingItemDecoration: Int,
        @AnimRes animRes: Int = _animRes,
    ) {
        _animRes = animRes
        setRecyclerView(
            recyclerAdapter,
            RecyclerViewLayoutType.RECYCLER_VIEW_GRID_VERTICAL,
            spanCountGrid = spanCountGrid,
            spacingItemDecoration = spacingItemDecoration,
            isNestedScrollingEnabledParam = false,
            animRes = animRes,
        )
    }

    fun animation(viewHolder: ViewHolder, @AnimRes idAnim: Int) {
        AnimationUtils.loadAnimation(viewHolder.itemView.context, idAnim)
    }

    private fun extractAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.KanzanRecyclerviewComponent)
        _animRes = typedArray.getResourceId(R.styleable.KanzanRecyclerviewComponent_KRVCAnim, _animRes)
        typedArray.recycle()
    }
}
