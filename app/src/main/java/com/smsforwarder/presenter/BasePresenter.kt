package com.smsforwarder.presenter

interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()
}
