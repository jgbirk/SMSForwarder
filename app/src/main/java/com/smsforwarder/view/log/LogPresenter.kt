package com.smsforwarder.view.log

import com.smsforwarder.ServiceLocator
import com.smsforwarder.data.repository.LogRepository
import com.smsforwarder.presenter.BasePresenterImpl
import com.smsforwarder.view.log.model.LogViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LogPresenter(
    private val logRepository: LogRepository = ServiceLocator.logRepository
) : BasePresenterImpl<LogContract.View>(), LogContract.Presenter {

    override fun loadLogs() {
        disposables.add(
            logRepository.getAll().map {
                it.map { log ->
                    LogViewModel(log.text, log.sender, log.timestamp, log.serverResponse, log.serverMessage)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext {
                view?.showLoading()
            }
            .subscribe(
                {
                    view?.hideLoading()
                    view?.showLogs(it)
                },
                {
                    view?.showError(it) { loadLogs() }
                }
            )
        )
    }

    override fun clearLogs() {
        disposables.add(
            Single.fromCallable {
                logRepository.deleteAll()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view?.showLogs(emptyList())
                },
                {
                    view?.showError(it) { clearLogs() }

                    it.printStackTrace()
                }
            )
        )
    }
}
