package com.smsforwarder.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateTimeUtils {

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
        return sdf.format(Date())
    }
}
