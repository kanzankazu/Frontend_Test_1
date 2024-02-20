package com.danamon.feature.module.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danamon.core.component.recyclerview.base.BaseRecyclerViewFilterAdapter
import com.danamon.core.ext.use
import com.danamon.data.api.user.model.User
import com.danamon.data.implementation.user.mapper.toStringRole
import com.danamon.feature.databinding.ItemActivityMainAdminBinding

class MainAdminAdapter : BaseRecyclerViewFilterAdapter<User>() {

    private val onEditClick: () -> Unit = {}
    private val onRemoveClick: () -> Unit = {}
    private val onEmailClick: () -> Unit = {}

    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainAdminAdapterHolder(ItemActivityMainAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindView(data: User, position: Int, holder: RecyclerView.ViewHolder) {
        when (holder) {
            is MainAdminAdapterHolder -> holder.setContent(data)
        }
    }

    inner class MainAdminAdapterHolder(private val inflate: ItemActivityMainAdminBinding) : RecyclerView.ViewHolder(inflate.root) {
        fun setContent(data: User) = inflate.use {
            mcvItemActivityMainAdmin
            tvItemActivityMainAdminId.text = data.userId.toString()
            tvItemActivityMainAdminRole.text = data.toStringRole()
            tvItemActivityMainAdminName.text = data.username
            tvItemActivityMainAdminEmail.text = data.email
            tvItemActivityMainAdminEmail.setOnClickListener { }
            ivItemActivityMainAdminEdit.setOnClickListener { }
            ivItemActivityMainAdminDelete.setOnClickListener { }
        }
    }
}
