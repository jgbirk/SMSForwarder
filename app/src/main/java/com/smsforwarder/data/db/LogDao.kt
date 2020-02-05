package com.smsforwarder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(log: LogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(logs: List<LogEntity>)

    @Query("SELECT * FROM log")
    fun getAll(): Flowable<List<LogEntity>>

    @Query("DELETE FROM log")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM log")
    fun getSize(): Int
}
