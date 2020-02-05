package com.smsforwarder.view.log.model

import androidx.annotation.DrawableRes
import com.smsforwarder.R
import com.smsforwarder.adapter.RowViewModel

class LogViewModel(
    val text: String,
    val sender: String,
    val timestamp: String,
    val serverResponse: Int,
    val serverMessage: String
) : RowViewModel(R.layout.list_item_log) {

    fun getTitle() = if (serverResponse == 0) text else "$text - $serverResponse - $serverMessage"

    @DrawableRes
    fun getIcon(): Int = if (serverResponse == 0) R.drawable.ic_sms else R.drawable.ic_sync
}
