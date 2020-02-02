package com.smsforwarder.presenter

interface LoadDataView : BaseView {
    fun showLoading(message: String = "")
    fun hideLoading()
    fun showError(throwable: Throwable, retry: (() -> Unit)? = null)
}
