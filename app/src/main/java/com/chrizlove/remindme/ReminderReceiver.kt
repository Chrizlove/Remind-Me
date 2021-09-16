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
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, MainActivity::class.java)
            i.putExtra("title",intent?.getStringExtra("title"))
            i.putExtra("desc",intent?.getStringExtra("desc"))
            i.putExtra("minute",intent?.getStringExtra("minute"))
            i.putExtra("hour",intent?.getStringExtra("hour"))
            i.putExtra("day",intent?.getStringExtra("day"))
            i.putExtra("month",intent?.getStringExtra("month"))
            i.putExtra("year",intent?.getStringExtra("year"))
            i.putExtra("time",intent?.getIntExtra("time",0))
            i.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT)

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