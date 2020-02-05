package com.smsforwarder.mapper

import com.smsforwarder.data.db.LogEntity
import com.smsforwarder.model.Log

object LogMapper {

    @JvmStatic
    fun transform(log: Log) =
        LogEntity(text = log.text, sender = log.sender, timestamp = log.timestamp, serverResponse = log.serverResponse, serverMessage = log.serverMessage)

    @JvmStatic
    fun transformList(logs: List<Log>) =
        logs.map { transform(it) }

    @JvmStatic
    fun transform(log: LogEntity) =
        Log(text = log.text, sender = log.sender, timestamp = log.timestamp, serverResponse = log.serverResponse, serverMessage = log.serverMessage)

    @JvmStatic
    fun transformEntityList(logs: List<LogEntity>) =
        logs.map { transform(it) }
}
