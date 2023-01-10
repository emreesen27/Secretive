package com.sn.secretive.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}