package com.smsforwarder.service

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobScheduler.RESULT_SUCCESS
import android.content.ComponentName
import android.content.Context

object SMSJobScheduler {

    @JvmStatic
    fun schedule(context: Context, service: ComponentName, periodic: Long): Int {
        val job = JobInfo.Builder(
            SMSSender.JOB_ID,
            service
        )
        .setPersisted(true)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setPeriodic(periodic)
        .build()

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        return jobScheduler.schedule(job)
    }

    @JvmStatic
    fun getJob(context: Context, service: ComponentName): JobInfo? {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        return jobScheduler.allPendingJobs.firstOrNull { it.service.className == service.className }
    }

    /**
     * Restart the job if the periodic time is different or if there is no prior job scheduled for
     * the specified service.
     */
    @JvmStatic
    fun restartJobIfNeeded(context: Context, service: ComponentName, periodic: Long): Int {
        val job = getJob(context, service)

        if (job == null || job.intervalMillis != periodic) {
            return schedule(context, service, periodic)
        }

        return RESULT_SUCCESS
    }
}
