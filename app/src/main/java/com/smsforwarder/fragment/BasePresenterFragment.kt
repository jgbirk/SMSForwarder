package com.smsforwarder.fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import com.smsforwarder.presenter.BasePresenter
import com.smsforwarder.presenter.BaseView

@Suppress("UNCHECKED_CAST")
abstract class BasePresenterFragment<in V : BaseView, T : BasePresenter<V>> : BaseFragment(), BaseView {

    protected abstract var presenter: T

    override fun getContext(): Context = super.getContext()!!

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attachView(this as V)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}
