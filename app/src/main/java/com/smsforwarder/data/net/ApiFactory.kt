package com.smsforwarder.data.net

import android.content.Context
import com.smsforwarder.data.DataModule

object ApiFactory {

    private val baseUrl by lazy {
        DataModule.getContext()?.getSharedPreferences("sms_pref", Context.MODE_PRIVATE)
            ?.getString("server_url", "https://budgetalarms.woodview.be")
            ?: "https://budgetalarms.woodview.be"
    }

    val smsApi: SMSApi =
        RetrofitHelper.retrofit(baseUrl).create(SMSApi::class.java)
}
