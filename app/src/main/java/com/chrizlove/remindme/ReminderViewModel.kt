package com.chrizlove.remindme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application): AndroidViewModel(application) {
    val reminderdata : LiveData<List<Reminder>>
    val reminderRepository: ReminderRepository
    init{
        val dao = ReminderDataBase.getDatabase(application).getReminderDao()
        reminderRepository = ReminderRepository(dao)
        reminderdata = reminderRepository.reminderdata
    }
    fun delete(reminder: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        reminderRepository.delete(reminder)
    }
    fun insert(reminder: Reminder) = viewModelScope.launch(Dispatchers.IO) {
        reminderRepository.insert(reminder)
    }
}