package com.smsforwarder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = ([
        SMSEntity::class
    ]),
    version = 1,
    exportSchema = false)
abstract class SMSDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "sms.db"
        private var database: SMSDatabase? = null

        @Synchronized
        fun create(context: Context, useInMemory: Boolean, allowMainThreadQueries: Boolean = false): SMSDatabase {

            if (database == null) {
                val databaseBuilder = if (useInMemory) {
                    Room.inMemoryDatabaseBuilder(context, SMSDatabase::class.java).apply {
                        if (allowMainThreadQueries) {
                            allowMainThreadQueries()
                        }
                    }
                } else {
                    Room.databaseBuilder(context, SMSDatabase::class.java,
                        DB_NAME
                    ).apply {
                        if (allowMainThreadQueries) {
                            allowMainThreadQueries()
                        }
                    }
                }

                database = databaseBuilder.fallbackToDestructiveMigration().build()
            }

            return database!!
        }
    }

    abstract fun smsDao(): SMSDao
}
