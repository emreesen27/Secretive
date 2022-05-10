package com.sn.secretive.ui.password

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.data.model.AutoUpdatableAdapter
import com.sn.secretive.data.model.PasswordItemModel
import com.sn.secretive.databinding.ItemPasswordBinding
import kotlin.properties.Delegates


class PasswordAdapter(private val context: Context) :
    RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>(),
    AutoUpdatableAdapter {

    var items: List<PasswordItemModel> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder =
        PasswordViewHolder(
            ItemPasswordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class PasswordViewHolder(val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PasswordItemModel) {
            binding.item = item
            AppCompatResources.getDrawable(context, item.iconId)?.alpha = 255
            binding.ivIcon.setImageResource(item.iconId)
        }
    }


}