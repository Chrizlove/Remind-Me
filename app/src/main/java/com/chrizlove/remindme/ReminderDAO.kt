package com.chrizlove.remindme

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminderTable")
    fun getReminderData() : LiveData<List<Reminder>>
}