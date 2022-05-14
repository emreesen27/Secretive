package com.sn.secretive.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.sn.secretive.R

class DetailView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {


    private var titleTextView: MaterialTextView
    private var valueTextView: MaterialTextView

    private var title: String
        get() = titleTextView.text.toString()
        set(v) {
            titleTextView.text = v
        }

    var value: String
        get() = valueTextView.text.toString()
        set(v) {
            valueTextView.text = v
        }


    init {
        inflate(context, R.layout.detail_view_layout, this)

        titleTextView = findViewById(R.id.tv_title)
        valueTextView = findViewById(R.id.tv_value)

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