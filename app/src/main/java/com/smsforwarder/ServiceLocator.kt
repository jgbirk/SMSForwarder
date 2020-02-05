package com.smsforwarder

import com.smsforwarder.data.repository.LogRepository
import com.smsforwarder.data.repository.SMSRepository

object ServiceLocator {

    lateinit var smsRepository: SMSRepository
        private set

    lateinit var logRepository: LogRepository
        private set

    private var initialized = false

    @JvmStatic
    fun initialize(
        smsRepository: SMSRepository = SMSRepository(),
        logRepository: LogRepository = LogRepository()
    ) {
        if (!initialized) {
            this.initialized = true
            this.smsRepository = smsRepository
            this.logRepository = logRepository
        }
    }
}
