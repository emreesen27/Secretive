package com.sn.secretive.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sn.secretive.R

class DetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var titleInputLayout: TextInputLayout
    private var valueEditText: TextInputEditText

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

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DetailView)
        title = attributes.getString(R.styleable.DetailView_titleText) ?: "Title"
        value = attributes.getString(R.styleable.DetailView_valueText) ?: "Value"

        attributes.recycle()
    }
}

@BindingAdapter("valueText")
fun DetailView.valueTextView(v: String) {
    value = v
}
