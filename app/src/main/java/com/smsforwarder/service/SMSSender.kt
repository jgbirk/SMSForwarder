package com.smsforwarder.service

import android.app.job.JobParameters
import android.app.job.JobService
import com.smsforwarder.ServiceLocator
import com.smsforwarder.data.db.LogEntity
import com.smsforwarder.mapper.LogMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SMSSender : JobService() {

    companion object {
        const val JOB_ID = 25000
    }

    private var disposable: Disposable? = null

    override fun onStopJob(params: JobParameters?): Boolean {
        jobFinished(params, false)

        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        var running = true

        sendSMS {
            if (it) {
                jobFinished(params, false)
            } else {
                running = false
            }
        }

        return running
    }

    private fun sendSMS(onFinished: (Boolean) -> Unit) {
        disposable = ServiceLocator.smsRepository.getAll()
            .filter { it.isNotEmpty() }
            .take(1)
            .singleOrError()
            .flatMap { sms ->
                ServiceLocator.smsRepository.sendSMS(sms).map {
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
                    onFinished(true)
                },
                {
                    onFinished(false)
                }
            )
    }
}
