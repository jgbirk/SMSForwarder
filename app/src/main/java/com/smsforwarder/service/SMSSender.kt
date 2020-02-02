package com.smsforwarder.service

import android.app.job.JobParameters
import android.app.job.JobService
import com.smsforwarder.ServiceLocator
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
            .take(1)
            .singleOrError()
            .flatMap {
                ServiceLocator.smsRepository.sendSMS(it)
            }
            .map {
                if (it.status == 200) {
                    ServiceLocator.smsRepository.deleteAll()
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
