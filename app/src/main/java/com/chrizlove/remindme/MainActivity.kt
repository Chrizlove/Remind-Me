package com.chrizlove.remindme

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chrizlove.remindme.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var pendingIntent: PendingIntent
    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var reminderViewModel: ReminderViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var reminderManager: AlarmManager
    var day=0
    var hour=0
    var minute=0
    var year=0
    var month=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //code for changing intent to add new reminder
        addReminderButton.setOnClickListener{
            val intent = Intent(this,ReminderAddActivity::class.java)
            startActivity(intent)
        }

        //recycler view is setted up
        setUpReminderRecyclerView()

        //reminderViewModel set up
         reminderViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ReminderViewModel::class.java)

        reminderViewModel.reminderdata.observe(this, Observer {

            //change visibility of no reminders yet textview
            if(reminderViewModel.reminderdata.value?.isEmpty() == true)
            {
                noReminder.visibility = View.VISIBLE
            }
            else{
                noReminder.visibility = View.INVISIBLE
            }

            //updating the reminder database if the time for reminder has passed
            updateReminderDataBase()

            //updating the recyclerview on any change
        reminderAdapter.updateReminders(it)
        })

        //swipe to delete functionality
        swipeRightToDelete()
    }

    private fun updateReminderDataBase() {
        val reminderlist = arrayListOf<Reminder>()
        reminderViewModel.reminderdata.value?.let { reminderlist.addAll(it) }
        for(reminder in reminderlist)
        {

            if(reminder.timeInMillis.toLong()<Calendar.getInstance().timeInMillis)
            {
                reminder.reminded = true
            }
        }
    }

    private fun setUpReminderRecyclerView() {
        reminderAdapter = ReminderAdapter(this,)
        val layoutManager =  LinearLayoutManager(this)
        remindersRecyclerView.layoutManager= layoutManager
        remindersRecyclerView.adapter = reminderAdapter
    }

     fun swipeRightToDelete()
    {
        val item= object: SwipeToDelete(this,0,ItemTouchHelper.RIGHT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //cancel the reminder notification
                cancelReminder(reminderAdapter.getReminder(viewHolder.adapterPosition).time)
                //remove reminder from room db
                reminderViewModel.delete(reminderAdapter.getReminder(viewHolder.adapterPosition))
                //remove reminder from recyclerview
                reminderAdapter.deleteReminder(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(remindersRecyclerView)
    }

    private fun cancelReminder(rqcode: Int) {
        reminderManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val  intent = Intent(this, ReminderReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,rqcode,intent,0)
        reminderManager.cancel(pendingIntent)
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

}