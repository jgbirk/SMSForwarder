package com.smsforwarder.view.main

import com.smsforwarder.presenter.BasePresenter
import com.smsforwarder.presenter.LoadDataView
import com.smsforwarder.view.main.model.SMSViewModel

object MainContract {

    interface View : LoadDataView {
        fun showSMS(sms: List<SMSViewModel>)
        fun showSuccessMessage()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSMS()
        fun sendSMS()
    }
}
