package com.smsforwarder.view.main.model

import com.smsforwarder.R
import com.smsforwarder.adapter.RowViewModel

class SMSViewModel(
    val text: String,
    val sender: String,
    val timestamp: String
) : RowViewModel(R.layout.list_item_sms)