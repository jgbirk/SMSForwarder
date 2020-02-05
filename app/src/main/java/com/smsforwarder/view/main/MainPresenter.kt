package com.smsforwarder.view.main

import com.smsforwarder.ServiceLocator
import com.smsforwarder.data.db.LogEntity
import com.smsforwarder.data.repository.SMSRepository
import com.smsforwarder.presenter.BasePresenterImpl
import com.smsforwarder.view.main.model.SMSViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val smsRepository: SMSRepository = ServiceLocator.smsRepository
) : BasePresenterImpl<MainContract.View>(), MainContract.Presenter {

    override fun loadSMS() {
        disposables.add(
            smsRepository.getAll().map {
                it.map { sms ->
                    SMSViewModel(sms.text, sms.sender, sms.timestamp)
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
                    view?.showSMS(it)
                },
                {
                    view?.showError(it) { loadSMS() }
                }
            )
        )
    }

    override fun sendSMS() {
        disposables.add(
            smsRepository.getAll()
                .filter {
                    it.isNotEmpty()
                }
                .take(1)
                .singleOrError()
                .flatMap { sms ->
                    smsRepository.sendSMS(sms).map {
                        it to sms
                    }
                }
                .flatMap {
                    val response = it.first

                    if (response.status == 200) {
                        Single.fromCallable {
                            ServiceLocator.logRepository.insert(
                                it.second.map {
                                    LogEntity(
                                        text = it.text,
                                        sender = it.sender,
                                        timestamp = it.timestamp,
                                        serverResponse = response.status,
                                        serverMessage = response.message
                                    )
                                }
                            )
                        }.flatMap {
                            Single.fromCallable {
                                ServiceLocator.smsRepository.deleteAll()
                            }
                        }
                    } else {
                        Single.fromCallable {
                            ServiceLocator.logRepository.insert(
                                it.second.map {
                                    LogEntity(
                                        text = it.text,
                                        sender = it.sender,
                                        timestamp = it.timestamp,
                                        serverResponse = response.status,
                                        serverMessage = response.message
                                    )
                                }
                            )
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        view?.showSuccessMessage()
                    },
                    {
                        view?.showError(it) { sendSMS() }

                        it.printStackTrace()
                    }
                )
        )
    }
}
