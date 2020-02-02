package com.smsforwarder

import com.smsforwarder.data.repository.SMSRepository

object ServiceLocator {

    lateinit var smsRepository: SMSRepository
        private set

    private var initialized = false

    @JvmStatic
    fun initialize(
        smsRepository: SMSRepository = SMSRepository()
    ) {
        if (!initialized) {
            this.initialized = true
            this.smsRepository = smsRepository
        }
    }
}
