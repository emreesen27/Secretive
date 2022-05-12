package com.sn.secretive.extensions

import android.content.Context

fun Context.getIcon(name: String): Int {
    return this.resources.getIdentifier(name, "drawable", this.packageName);
}

