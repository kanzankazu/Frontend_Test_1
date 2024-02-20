package com.danamon.feature.module.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danamon.core.component.recyclerview.base.BaseRecyclerViewFilterAdapter
import com.danamon.core.ext.loadImage
import com.danamon.core.ext.use
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.feature.databinding.ItemActivityMainUserBinding

class MainUserAdapter : BaseRecyclerViewFilterAdapter<JsonPlaceHolderPhoto>() {
    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainUserAdapterHolder(ItemActivityMainUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindView(data: JsonPlaceHolderPhoto, position: Int, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is MainUserAdapterHolder -> holder.setContent(data)
        }
    }

    inner class MainUserAdapterHolder(private val inflate: ItemActivityMainUserBinding) : RecyclerView.ViewHolder(inflate.root) {
        fun setContent(data: JsonPlaceHolderPhoto) = inflate.use {
            ivItemActivityMainUser.loadImage(data.thumbnailUrl)
            tvItemActivityMainUserId.text = data.id.toString()
            tvItemActivityMainUserTitle.text = data.title
            tvItemActivityMainUserTitle.isSelected = true
        }
    }
}
