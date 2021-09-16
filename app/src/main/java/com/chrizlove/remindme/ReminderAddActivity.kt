package com.chrizlove.remindme

import android.app.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_reminder_add.*
import java.util.*

class ReminderAddActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var timecurrent: Int = 0
    private lateinit var calender: Calendar
    private lateinit var reminderManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    var day=0
    var hour=0
    var minute=0
    var year=0
    var month=0

    var savedDay=0
    var savedHour=0
    var savedMinute=0
    var savedYear=0
    var savedMonth=0
    private lateinit var reminderViewModel: ReminderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_add)
            saveReminderButton.setOnClickListener {

                if(newReminderTitle.text.isNotEmpty()) {
                getDateTime()
                }
                else{
                    Toast.makeText(this,"A Title is required", Toast.LENGTH_SHORT).show()
                }
        }

        reminderViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ReminderViewModel::class.java)
    }

    private fun getDateTime() {
        getDateTimeCalendar()
        DatePickerDialog(this, this,year,month,day).show()
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDay=p3
        savedMonth=p2
        savedYear=p1
        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour=p1
        savedMinute=p2
            //taking the current time of making reminder
        timecurrent = Calendar.getInstance().timeInMillis.toInt()

        reminderViewModel.insert(Reminder(newReminderTitle.text.toString(),newReminderDesc.text.toString(),savedMinute.toString(),savedHour.toString(),savedDay.toString(),savedMonth.toString(),savedYear.toString(),timecurrent))

        //creating a time variable calendar for the time user inputted
        calender = Calendar.getInstance()
        calender[Calendar.MINUTE] = savedMinute
        calender[Calendar.HOUR]= savedHour
        calender[Calendar.DAY_OF_MONTH] = savedDay
        calender[Calendar.MONTH]= savedMonth
        calender[Calendar.YEAR]= savedYear

        //creating notification
        createNotificationChannel()
        setReminder(calender.timeInMillis)

        //changing intent
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun setReminder(time: Long) {
        reminderManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val  intent = Intent(this, ReminderReceiver::class.java)
        intent.putExtra("title",newReminderTitle.text.toString())
        intent.putExtra("desc",newReminderDesc.text.toString())
        intent.putExtra("minute",savedMinute.toString())
        intent.putExtra("hour",savedHour.toString())
        intent.putExtra("day",savedDay.toString())
        intent.putExtra("month",savedMonth.toString())
        intent.putExtra("year",savedYear.toString())
        intent.putExtra("time",timecurrent)
        pendingIntent = PendingIntent.getBroadcast(this,timecurrent,intent,0)
        reminderManager.setExact(
            AlarmManager.RTC_WAKEUP,time,pendingIntent
        )
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name: CharSequence = "ChrizloveReminderChannel"
            val description = "Channel for Reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Chrizlove",name,importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}