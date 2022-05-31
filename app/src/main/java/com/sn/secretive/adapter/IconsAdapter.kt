package com.sn.secretive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.extensions.click
import kotlin.properties.Delegates
import com.sn.secretive.R
import com.sn.secretive.data.model.IconModel
import com.sn.secretive.databinding.ItemIconBinding
import com.sn.secretive.extensions.getIcon


class IconsAdapter(private val context: Context) :
    RecyclerView.Adapter<IconsAdapter.IconsViewHolder>(),
    AutoUpdatableAdapter {

    private val items = mutableListOf(
        IconModel(R.drawable.ic_facebook, false, "ic_facebook", "Facebook"),
        IconModel(R.drawable.ic_instagram, false, "ic_instagram", "Instagram"),
        IconModel(R.drawable.ic_gmail, false, "ic_gmail", "Gmail"),
        IconModel(R.drawable.ic_twitter, false, "ic_twitter", "Twitter"),
        IconModel(R.drawable.ic_password, false, "ic_password", "Password gray"),
        IconModel(R.drawable.ic_password_green, false, "ic_password_green", "Password green"),
        IconModel(R.drawable.ic_password_blue, false, "ic_password_blue", "Password blue"),
    )
    var onClick: ((String) -> Unit)? = null
    private var notifyItems: List<IconModel> by Delegates.observable(items) { _, old, new ->
        autoNotify(old, new) { o, n -> o.isSelected != n.isSelected }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder =
        IconsViewHolder(
            ItemIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {

        holder.binding.container.click {
            onClick?.invoke(notifyItems[position].iconName)
        }
        holder.bind(notifyItems[position])
    }

    override fun getItemCount(): Int = notifyItems.size

    inner class IconsViewHolder(val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IconModel) {
            binding.ivIcon.setImageResource(context.getIcon(item.iconName))
            binding.tvIconName.text = item.name
        }
    }

}