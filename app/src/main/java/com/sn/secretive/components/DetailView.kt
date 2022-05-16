package com.sn.secretive.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.sn.secretive.R
import com.sn.secretive.extensions.click

class DetailView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {


    private var titleTextView: MaterialTextView
    private var valueTextView: MaterialTextView
    var onClick: ((View) -> Unit)? = null

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

        findViewById<ConstraintLayout>(R.id.container).click {
            onClick?.invoke(it)
        }

        attributes.recycle()

    }

}

@BindingAdapter("valueText")
fun DetailView.valueTextView(v: String) {
    value = v
}