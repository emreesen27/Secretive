package com.sn.secretive.extensions

import android.content.Context
import android.view.View
import render.animations.Bounce
import render.animations.Render

inline fun <reified T : View> T.click(crossinline block: (T) -> Unit) = setOnClickListener {
    block(it as T)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visibleWithAnim(context: Context, duration: Long = 1000) {
    this.visibility = View.VISIBLE
    val render = Render(context)
    render.setDuration(duration)
    render.setAnimation(Bounce().In(this))
    render.start()
}

fun View.invisibleWithAnim(context: Context, duration: Long = 1000) {
    this.visibility = View.INVISIBLE
    val render = Render(context)
    render.setDuration(duration)
    render.setAnimation(Bounce().InDown(this))
    render.start()
}
