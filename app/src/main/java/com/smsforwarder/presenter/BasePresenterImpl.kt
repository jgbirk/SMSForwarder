package com.smsforwarder.presenter

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected val disposables by lazy { CompositeDisposable() }
    protected var view: V? = null

    @CallSuper
    override fun attachView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detachView() {
        disposables.dispose()
        view = null
    }

    fun add(disposable: Disposable) {
        disposables.add(disposable)
    }
}
