package com.chrizlove.remindme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = arrayOf(Reminder::class), version = 1, exportSchema = false)
abstract class ReminderDataBase : RoomDatabase() {

    abstract fun getReminderDao(): ReminderDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ReminderDataBase? = null

        fun getDatabase(context: Context): ReminderDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDataBase::class.java,
                    "reminder_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}