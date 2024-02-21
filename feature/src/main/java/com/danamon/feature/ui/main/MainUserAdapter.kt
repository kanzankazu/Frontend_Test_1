package com.danamon.feature.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danamon.core.component.recyclerview.base.BaseRecyclerViewFilterAdapter
import com.danamon.core.ext.loadImage
import com.danamon.core.ext.use
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.feature.databinding.ItemActivityMainLoadingBinding
import com.danamon.feature.databinding.ItemActivityMainUserBinding

class MainUserAdapter : BaseRecyclerViewFilterAdapter<JsonPlaceHolderPhoto>() {

    var onItemClick: (String) -> Unit = {}

    companion object {
        const val DATA = 0
        const val LOADING = 1
    }

    override fun onMultipleViewType(position: Int, data: JsonPlaceHolderPhoto): Int {
        return when {
            !data.loading -> DATA
            else -> LOADING
        }
    }

    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA -> MainUserAdapterHolder(ItemActivityMainUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> MainUserLoadingAdapterHolder(ItemActivityMainLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindView(data: JsonPlaceHolderPhoto, position: Int, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is MainUserAdapterHolder -> holder.setContent(data)
        }
    }

    inner class MainUserAdapterHolder(private val inflate: ItemActivityMainUserBinding) : RecyclerView.ViewHolder(inflate.root) {
        @SuppressLint("SetTextI18n")
        fun setContent(data: JsonPlaceHolderPhoto) = inflate.use {
            root.setOnClickListener { onItemClick.invoke(data.url) }
            ivItemActivityMainUser.loadImage(data.thumbnailUrl)
            tvItemActivityMainUserId.text = "${data.id},${data.albumId}"
            tvItemActivityMainUserTitle.text = data.title
            //tvItemActivityMainUserTitle.isSelected = true
        }
    }

    inner class MainUserLoadingAdapterHolder(inflate: ItemActivityMainLoadingBinding) : RecyclerView.ViewHolder(inflate.root)
}
