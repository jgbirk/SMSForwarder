package com.smsforwarder.data.repository

import android.util.Base64
import com.smsforwarder.data.DataModule
import com.smsforwarder.data.db.SMSDatabase
import com.smsforwarder.data.db.SMSEntity
import com.smsforwarder.data.net.ApiFactory
import com.smsforwarder.data.net.request.SMSRequest
import com.smsforwarder.mapper.SMSMapper
import com.smsforwarder.model.SMS

class SMSRepository {

    private var database = SMSDatabase.create(
        DataModule.getContext()!!,
        false
    )

    fun insert(sms: SMSEntity) =
        database.smsDao().insert(sms)

    fun insert(sms: List<SMSEntity>) =
        database.smsDao().insert(sms)

    fun deleteAll() =
        database.smsDao().deleteAll()

    fun getAll() =
        database.smsDao().getAll().map {
            SMSMapper.transformEntityList(it)
        }

    fun getSize() =
        database.smsDao().getSize()

    fun sendSMS(sms: List<SMS>) =
        ApiFactory.smsApi.sendSMS(
            sms.map {
                SMSRequest(
                    text = Base64.encodeToString(it.text.toByteArray(Charsets.UTF_8), Base64.DEFAULT),
                    timestamp = it.timestamp,
                    sender = it.sender
                )
            }
        )
}
