package com.smsforwarder.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log

class SMSService : Service() {

    private lateinit var receiver: SMSReceiver

    override fun onCreate() {
        super.onCreate()

        Log.d("sms", "SMSService created!")

        receiver = SMSReceiver()

        registerReceiver(receiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()

        Log.d("sms", "SMSService destroyed!")

        unregisterReceiver(receiver)
    }
}