package com.smsforwarder.data

import android.content.Context
import java.lang.ref.WeakReference

object DataModule {

    private lateinit var context: WeakReference<Context>
    private var initialized = false

    @JvmStatic
    fun initialize(context: Context) {
        if (!initialized) {
            this.initialized = true
            this.context = WeakReference(context)
        }
    }

    @JvmStatic
    fun getContext(): Context? = context.get()
}
