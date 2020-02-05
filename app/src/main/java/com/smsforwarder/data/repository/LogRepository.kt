package com.smsforwarder.data.repository

import com.smsforwarder.data.DataModule
import com.smsforwarder.data.db.LogEntity
import com.smsforwarder.data.db.SMSDatabase
import com.smsforwarder.mapper.LogMapper

class LogRepository {

    private var database = SMSDatabase.create(
        DataModule.getContext()!!,
        false
    )

    fun insert(log: LogEntity) =
        database.logDao().insert(log)

    fun insert(logs: List<LogEntity>) =
        database.logDao().insert(logs)

    fun deleteAll() =
        database.logDao().deleteAll()

    fun getAll() =
        database.logDao().getAll().map {
            LogMapper.transformEntityList(it)
        }

    fun getSize() =
        database.logDao().getSize()
}
