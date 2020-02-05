package com.smsforwarder.model

class Log(
    val text: String,
    val sender: String,
    val timestamp: String,
    val serverResponse: Int,
    val serverMessage: String
)
