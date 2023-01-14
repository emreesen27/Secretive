package com.sn.secretive.components.imagepicker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sn.secretive.R
import com.sn.secretive.adapter.AutoUpdatableAdapter
import com.sn.secretive.data.model.ImageModel
import com.sn.secretive.databinding.ItemImageBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.getIcon
import kotlin.properties.Delegates

class ImageAdapter(private val context: Context) :
    RecyclerView.Adapter<ImageAdapter.IconsViewHolder>(),
    AutoUpdatableAdapter {

    private val items = mutableListOf(
        ImageModel(R.drawable.ic_facebook, "ic_facebook"),
        ImageModel(R.drawable.ic_instagram, "ic_instagram"),
        ImageModel(R.drawable.ic_twitter, "ic_twitter"),
        ImageModel(R.drawable.ic_gmail, "ic_gmail"),
        ImageModel(R.drawable.ic_steam, "ic_steam"),
        ImageModel(R.drawable.ic_pinterest, "ic_pinterest"),
        ImageModel(R.drawable.ic_linkedin, "ic_linkedin"),
        ImageModel(R.drawable.ic_reddit, "ic_reddit"),
        ImageModel(R.drawable.ic_amazon, "ic_amazon"),
        ImageModel(R.drawable.ic_github, "ic_github"),
        ImageModel(R.drawable.ic_password, "ic_password"),
        ImageModel(R.drawable.ic_password_green, "ic_password_green"),
        ImageModel(R.drawable.ic_password_blue, "ic_password_blue"),
    )
    var onClick: ((String) -> Unit)? = null
    private var notifyItems: List<ImageModel> by Delegates.observable(items) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id != n.id }
    }

    fun getImageNameWithPosition(position: Int): String = items[position].iconName

    fun getImagePositionWithName(imageName: String) =
        items.withIndex().first { it.value.iconName == imageName }.index

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder =
        IconsViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        holder.itemView.click {
            onClick?.invoke(notifyItems[position].iconName)
        }
        holder.bind(notifyItems[position])
    }

    override fun getItemCount(): Int = notifyItems.size

    inner class IconsViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageModel) {
            binding.ivIcon.setImageResource(context.getIcon(item.iconName))
        }
    }
}
