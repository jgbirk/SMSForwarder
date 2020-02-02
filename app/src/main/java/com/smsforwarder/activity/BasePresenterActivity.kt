package com.smsforwarder.activity

import android.content.Context
import android.os.Bundle
import com.smsforwarder.presenter.BasePresenter
import com.smsforwarder.presenter.BaseView

@Suppress("UNCHECKED_CAST")
abstract class BasePresenterActivity<in V : BaseView, T : BasePresenter<V>> :
    BaseActivity(), BaseView {

    protected abstract var presenter: T

    override fun getContext(): Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}
