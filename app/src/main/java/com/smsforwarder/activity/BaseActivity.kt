package com.smsforwarder.activity

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val disposables by lazy { CompositeDisposable() }

    override fun onDestroy() {
        disposables.dispose()

        super.onDestroy()
    }
}
