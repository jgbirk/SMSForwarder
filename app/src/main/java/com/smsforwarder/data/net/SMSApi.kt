package com.smsforwarder.data.net

import com.smsforwarder.data.net.request.SMSRequest
import com.smsforwarder.data.net.response.SMSResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SMSApi {

    @POST("/receive")
    fun sendSMS(@Body request: List<SMSRequest>): Single<SMSResponse>
}
