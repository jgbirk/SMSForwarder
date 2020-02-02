package com.smsforwarder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface SMSDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sms: SMSEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sms: List<SMSEntity>)

    @Query("SELECT * FROM sms")
    fun getAll(): Flowable<List<SMSEntity>>

    @Query("DELETE FROM sms")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM sms")
    fun getSize(): Int
}
