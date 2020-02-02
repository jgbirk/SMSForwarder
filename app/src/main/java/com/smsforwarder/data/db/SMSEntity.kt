package com.smsforwarder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms")
class SMSEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val sender: String,
    val timestamp: String
)
