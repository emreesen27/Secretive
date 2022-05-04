package com.sn.secretive.extensions

import android.view.View

inline fun <reified T : View> T.click(crossinline block: (T) -> Unit) = setOnClickListener {
    block(it as T)
}

