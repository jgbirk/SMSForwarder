package com.smsforwarder.data.net.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SMSResponse(
    @Json(name = "status_code")
    val status: Int,
    @Json(name = "message")
    val message: String
)
