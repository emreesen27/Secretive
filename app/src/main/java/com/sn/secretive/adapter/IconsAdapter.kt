package com.sn.secretive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.extensions.click
import kotlin.properties.Delegates
import androidx.appcompat.content.res.AppCompatResources
import com.sn.secretive.R
import com.sn.secretive.data.model.IconModel
import com.sn.secretive.databinding.ItemIconBinding


class IconsAdapter(private val context: Context) :
    RecyclerView.Adapter<IconsAdapter.IconsViewHolder>(),
    AutoUpdatableAdapter {

    var onClick: ((Int) -> Unit)? = null
    private var notifyItems: List<IconModel> by Delegates.observable(update(null)) { _, old, new ->
        autoNotify(old, new) { o, n -> o.isSelected == n.isSelected }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder =
        IconsViewHolder(
            ItemIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {


        holder.binding.ivIcon.click {
            notifyItems = update(position)
            onClick?.invoke(notifyItems[position].id)
        }

        isSelected(notifyItems[position])
        holder.bind(notifyItems[position])
    }

    override fun getItemCount(): Int = notifyItems.size

    inner class IconsViewHolder(val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IconModel) {
            binding.ivIcon.setImageResource(item.id)
        }
    }

    private fun isSelected(item: IconModel) {
        AppCompatResources.getDrawable(context, item.id)?.alpha =
            if (item.isSelected) 255 else 100
    }

    private fun update(position: Int?): MutableList<IconModel> {
        val items = mutableListOf(
            IconModel(R.drawable.ic_facebook, false),
            IconModel(R.drawable.ic_instagram, false),
            IconModel(R.drawable.ic_gmail, false),
            IconModel(R.drawable.ic_twitter, false),
            IconModel(R.drawable.ic_password, false),
            IconModel(R.drawable.ic_password_green, false),
            IconModel(R.drawable.ic_password_blue, false),
        )
        if (position != null) items[position].isSelected = true
        return items
    }


}