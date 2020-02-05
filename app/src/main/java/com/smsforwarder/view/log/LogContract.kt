package com.smsforwarder.view.log

import com.smsforwarder.presenter.BasePresenter
import com.smsforwarder.presenter.LoadDataView
import com.smsforwarder.view.log.model.LogViewModel

object LogContract {

    interface View : LoadDataView {
        fun showLogs(logs: List<LogViewModel>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadLogs()
        fun clearLogs()
    }
}
