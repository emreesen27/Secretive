package com.sn.secretive.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sn.secretive.R
import com.sn.secretive.extensions.click
import com.sn.secretive.extensions.hideKeyboard
import com.sn.secretive.extensions.showKeyboard

class DetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var editableStatus: Boolean = false

    private var titleInputLayout: TextInputLayout
    private var valueEditText: TextInputEditText
    private var editBtn: ImageButton

    private var title: String
        get() = titleInputLayout.hint.toString()
        set(v) {
            titleInputLayout.hint = v
        }

    var value: String
        get() = valueEditText.text.toString()
        set(v) {
            valueEditText.setText(v)
        }

    init {
        inflate(context, R.layout.detail_view_layout, this)

        valueEditText = findViewById(R.id.et_value)
        titleInputLayout = findViewById(R.id.til_title)
        editBtn = findViewById(R.id.ib_edit)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DetailView)
        title = attributes.getString(R.styleable.DetailView_titleText) ?: "Title"
        value = attributes.getString(R.styleable.DetailView_valueText) ?: "Value"

        editBtn.click {
            editableStatus = editableStatus != true
            setValueTexViewEnabled(editableStatus)
            setEditButtonImage(editableStatus)
            when (editableStatus) {
                true -> valueEditText.showKeyboard()
                else -> valueEditText.hideKeyboard()
            }
        }

        attributes.recycle()
    }

    private fun setValueTexViewEnabled(enabled: Boolean) {
        titleInputLayout.isEnabled = enabled
    }

    private fun setEditButtonImage(enabled: Boolean) {
        editBtn.setImageResource(if (enabled) R.drawable.ic_edit_blue else R.drawable.ic_edit)
    }
}

@BindingAdapter("valueText")
fun DetailView.valueTextView(v: String) {
    value = v
}
