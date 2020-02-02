package com.smsforwarder.fragment

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    protected val disposables by lazy { CompositeDisposable() }

    override fun onDestroy() {
        disposables.dispose()

        super.onDestroy()
    }
}
