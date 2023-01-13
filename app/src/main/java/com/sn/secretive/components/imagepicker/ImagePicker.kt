package com.sn.secretive.components.imagepicker

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.sn.secretive.R
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class ImagePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs),
    DiscreteScrollView.OnItemChangedListener<ImageAdapter.IconsViewHolder> {

    private var itemSelectedListener: ItemSelectedListener? = null
    private var picker: DiscreteScrollView
    private val imageAdapter by lazy {
        ImageAdapter(context)
    }

    interface ItemSelectedListener {
        fun onSelected(imageName: String)
    }

    fun subscribe(selected: ItemSelectedListener) {
        itemSelectedListener = selected
    }

    fun unSubscribe() {
        itemSelectedListener = null
    }

    fun setSelectImage(imageName: String) {
        post {
            picker.smoothScrollToPosition(imageAdapter.getImagePositionWithName(imageName))
        }
    }

    override fun onCurrentItemChanged(
        viewHolder: ImageAdapter.IconsViewHolder?,
        adapterPosition: Int
    ) {
        itemSelectedListener?.onSelected(imageAdapter.getImageNameWithPosition(adapterPosition))
    }

    init {
        inflate(context, R.layout.image_picker_layout, this)
        picker = findViewById(R.id.picker)
        picker.apply {
            adapter = imageAdapter
            picker.setItemTransformer(
                ScaleTransformer.Builder()
                    .setMaxScale(1.05f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER)
                    .setPivotY(Pivot.Y.BOTTOM)
                    .build()
            )
        }.also {
            it.addOnItemChangedListener(this)
        }
    }
}
