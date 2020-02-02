package com.smsforwarder.mapper

import com.smsforwarder.data.db.SMSEntity
import com.smsforwarder.model.SMS

object SMSMapper {

    @JvmStatic
    fun transform(sms: SMS) =
        SMSEntity(text = sms.text, sender = sms.sender, timestamp = sms.timestamp)

    @JvmStatic
    fun transformList(sms: List<SMS>) =
        sms.map { transform(it) }

    @JvmStatic
    fun transform(sms: SMSEntity) =
        SMS(text = sms.text, sender = sms.sender, timestamp = sms.timestamp)

    @JvmStatic
    fun transformEntityList(sms: List<SMSEntity>) =
        sms.map { transform(it) }
}
