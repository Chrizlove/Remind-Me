package com.chrizlove.remindme

import androidx.lifecycle.LiveData

class ReminderRepository(private val reminderDAO: ReminderDAO) {
    val reminderdata: LiveData<List<Reminder>> = reminderDAO.getReminderData()

    suspend fun insert(reminder: Reminder)
    {
        reminderDAO.insert(reminder)
    }

    suspend fun delete(reminder: Reminder)
    {
        reminderDAO.delete(reminder)
    }
}