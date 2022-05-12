package com.sn.secretive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.extensions.click
import kotlin.properties.Delegates
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.alpha
import com.sn.secretive.R
import com.sn.secretive.data.model.IconModel
import com.sn.secretive.databinding.ItemIconBinding
import com.sn.secretive.extensions.getIcon


class IconsAdapter(private val context: Context) :
    RecyclerView.Adapter<IconsAdapter.IconsViewHolder>(),
    AutoUpdatableAdapter {

    private val items = mutableListOf(
        IconModel(R.drawable.ic_facebook, false, "ic_facebook"),
        IconModel(R.drawable.ic_instagram, false, "ic_instagram"),
        IconModel(R.drawable.ic_gmail, false, "ic_gmail"),
        IconModel(R.drawable.ic_twitter, false, "ic_twitter"),
        IconModel(R.drawable.ic_password, false, "ic_password"),
        IconModel(R.drawable.ic_password_green, false, "ic_password_green"),
        IconModel(R.drawable.ic_password_blue, false, "ic_password_blue"),
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

        holder.binding.ivIcon.click {
            notifyItems = update(position)
            onClick?.invoke(notifyItems[position].iconName)
        }

        isSelected(notifyItems[position])
        holder.bind(notifyItems[position])
    }

    override fun getItemCount(): Int = notifyItems.size

    inner class IconsViewHolder(val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IconModel) {
            binding.ivIcon.setImageResource(context.getIcon(item.iconName))
        }
    }

    private fun isSelected(item: IconModel) {
        AppCompatResources.getDrawable(context, context.getIcon(item.iconName))?.alpha =
            if (item.isSelected) 255 else 100
    }

    private fun update(position: Int?): MutableList<IconModel> {
        if (position != null) items.forEachIndexed { i, e -> e.isSelected = i == position }
        return items
    }

    fun resetIcons() {
        items.forEach {
            it.isSelected = true
            isSelected(it)
        }
    }


}