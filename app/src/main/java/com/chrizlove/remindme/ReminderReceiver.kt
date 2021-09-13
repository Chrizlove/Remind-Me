package com.chrizlove.remindme

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider

class ReminderReceiver : BroadcastReceiver() {
    private lateinit var reminderViewModel: ReminderViewModel
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, MainActivity::class.java)
        val intentTitle = intent?.getStringExtra("title")
            i.putExtra("titlei",intentTitle)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,i,0)

        val notificationBuilder = NotificationCompat.Builder(context!!,"Chrizlove")
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(intent.getStringExtra("title"))
            .setContentText(intent.getStringExtra("desc"))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)


        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,notificationBuilder.build())


        //reminderViewModel.delete(
              //  Reminder(intent.getStringExtra("title")!!,intent.getStringExtra("desc")!!,
             //   intent.getStringExtra("minute")!!,intent.getStringExtra("hour")!!,intent.getStringExtra("day")!!,
             //   intent.getStringExtra("month")!!, intent.getStringExtra("year")!!
             //
    }

}