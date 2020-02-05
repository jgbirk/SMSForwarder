package com.smsforwarder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log")
class LogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val sender: String,
    val timestamp: String,
    val serverResponse: Int = 0,
    val serverMessage: String = ""
)
