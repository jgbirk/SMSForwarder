package com.smsforwarder.data.net.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SMSRequest(
    @Json(name = "text")
    val text: String,
    @Json(name = "occurred_at")
    val timestamp: String,
    @Json(name = "from_number")
    val sender: String
)
