package com.sn.secretive.components

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView
import com.sn.secretive.R
import com.sn.secretive.adapter.IconsAdapter
import com.sn.secretive.databinding.BottomSheetIconBinding
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.getIcon

class IconPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var iconView: ImageView
    private var itemSelectedListener: ItemSelectedListener? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var descriptionText: String = ""
    private val iconsAdapter by lazy {
        IconsAdapter(context)
    }

    interface ItemSelectedListener {
        fun onSelected(iconName: String)
    }

    init {
        inflate(context, R.layout.icon_picker_layout, this)
        iconView = findViewById(R.id.iv_base_icon)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.IconPicker)
        descriptionText =
            attributes.getString(R.styleable.IconPicker_descriptionText) ?: "Description Text"

        attributes.recycle()

        setDescriptionTextValue(descriptionText)

        iconView.click {
            showIconPicker()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun subscribe(selected: ItemSelectedListener) {
        itemSelectedListener = selected
    }

    fun unSubscribe() {
        itemSelectedListener = null
    }

    fun setImage(iconName: String) {
        iconView.setImageResource(context.getIcon(iconName))
    }

    private fun setDescriptionTextValue(value: String) {
        findViewById<MaterialTextView>(R.id.tv_description).text = value
    }

    private fun showIconPicker() {
        bottomSheetDialog =
            BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        val bindingSheet = DataBindingUtil.inflate<BottomSheetIconBinding>(
            LayoutInflater.from(context),
            R.layout.bottom_sheet_icon,
            null,
            false
        )
        bottomSheetDialog.setContentView(bindingSheet.root)

        bindingSheet.rcvIcon.adapter = iconsAdapter
        bindingSheet.rcvIcon.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        iconsAdapter.onClick = { iconName ->
            iconView.setImageResource(context.getIcon(iconName))
            itemSelectedListener?.onSelected(iconName)
            bottomSheetDialog.dismiss()
        }
        bindingSheet.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.show()
    }
}
