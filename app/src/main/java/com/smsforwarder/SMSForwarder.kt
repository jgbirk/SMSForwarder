package com.smsforwarder

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.IntentFilter
import com.facebook.stetho.Stetho
import com.smsforwarder.data.DataModule
import com.smsforwarder.service.SMSJobScheduler
import com.smsforwarder.service.SMSReceiver
import com.smsforwarder.service.SMSSender
import java.util.concurrent.TimeUnit

class SMSForwarder : Application() {

    override fun onCreate() {
        super.onCreate()

        DataModule.initialize(this)

        ServiceLocator.initialize()

        Stetho.initializeWithDefaults(this)

        registerReceiver(SMSReceiver(), IntentFilter("android.provider.Telephony.SMS_RECEIVED"))

        val preferences = getSharedPreferences("sms_pref", Context.MODE_PRIVATE)

        SMSJobScheduler.restartJobIfNeeded(this, ComponentName(this, SMSSender::class.java.name), TimeUnit.HOURS.toMillis(preferences.getLong("auto_sync_interval", 1)))
    }
}
