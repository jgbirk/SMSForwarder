package com.smsforwarder.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import com.smsforwarder.ServiceLocator
import com.smsforwarder.mapper.SMSMapper
import com.smsforwarder.model.SMS
import com.smsforwarder.util.DateTimeUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SMSReceiver : BroadcastReceiver() {

    private var disposable: Disposable? = null

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("sms", "Intent received: ${intent.action}")

        val bundle = intent.extras

        val format = bundle!!.getString("format")
        val pdus = bundle["pdus"] as Array<*>?

        if (pdus != null) {
            val isVersionM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

            for (i in pdus.indices) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer
                    SmsMessage.createFromPdu(pdus[i] as ByteArray, format)?.also {
                        if (filterSMS(context, it.originatingAddress ?: "")) {
                            saveSMS(it)
                        }
                    }
                } else {
                    // If Android version L or older
                    SmsMessage.createFromPdu(pdus[i] as ByteArray)?.also {
                        if (filterSMS(context, it.originatingAddress ?: "")) {
                            saveSMS(it)
                        }
                    }
                }
            }
        }
    }

    private fun filterSMS(context: Context, sender: String): Boolean {
        val numbers = context.getSharedPreferences("sms_pref", Context.MODE_PRIVATE)
            ?.getString("senders_number", null)?.split(",")

        return if (numbers == null || numbers.isEmpty()) {
            true
        } else {
            numbers.find { it == sender } != null
        }
    }

    private fun saveSMS(sms: SmsMessage) {
        disposable?.dispose()

        disposable = Single.fromCallable {
            ServiceLocator.smsRepository.insert(
                SMSMapper.transform(
                    SMS(
                        sms.originatingAddress ?: "Unknown",
                        sms.messageBody,
                        DateTimeUtils.getCurrentTime()
                    )
                )
            )
        }
        .subscribeOn(Schedulers.io())
        .subscribe(
            {
                Log.d("sms", "SMS saved!")

                sendSMS()
            },
            {
                Log.d("sms", "Error saving SMS: ${it.message}")
            }
        )
    }

    private fun sendSMS() {
        disposable?.dispose()

        disposable = ServiceLocator.smsRepository.getAll()
            .take(1)
            .singleOrError()
            .flatMap {
                ServiceLocator.smsRepository.sendSMS(it)
            }
            .map {
                if (it.status == 200) {
                    ServiceLocator.smsRepository.deleteAll()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("sms", "SMS sent!")
                },
                {
                    Log.d("sms", "Error sending SMS: ${it.message}")
                }
            )
    }
}
