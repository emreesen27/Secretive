package com.sn.secretive.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun Context.getIcon(name: String): Int {
    return this.resources.getIdentifier(name, "drawable", this.packageName);
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Secretive Pass", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.showToast(msg: String, drt: Int ) {
    Toast.makeText(this, msg, drt).show()
}
