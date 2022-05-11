package com.sn.secretive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.ItemPasswordBinding
import com.sn.secretive.extensions.click
import kotlin.properties.Delegates


class PasswordAdapter :
    RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>(),
    AutoUpdatableAdapter {

    var onClick: ((PasswordItemModel) -> Unit)? = null
    var items: List<PasswordItemModel> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder =
        PasswordViewHolder(
            ItemPasswordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.bind(items[position])

        holder.binding.passItem.click {
            onClick?.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size


    inner class PasswordViewHolder(val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PasswordItemModel) {
            binding.item = item
            binding.ivIcon.setImageResource(item.iconId)
        }
    }


}