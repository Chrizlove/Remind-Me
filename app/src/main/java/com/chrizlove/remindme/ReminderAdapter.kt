package com.chrizlove.remindme

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(private val context: Context): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    val reminderList = ArrayList<Reminder>()

    inner class ReminderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val reminderTitleAD = itemView?.findViewById<TextView>(R.id.reminderTitleCardView)
        val reminderTimeAD = itemView?.findViewById<TextView>(R.id.reminderTimeCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val viewholder = ReminderViewHolder(LayoutInflater.from(context).inflate(R.layout.reminder_layout, parent, false))
        return viewholder
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val currentReminder = reminderList[position]
        holder?.reminderTitleAD.text = currentReminder.title
        if (currentReminder.reminded == false) {
            holder?.reminderTimeAD.text =
                "Set For - ${currentReminder.hour}:${currentReminder.minute}, ${currentReminder.day}/${currentReminder.month}/${currentReminder.year}"
        }
        else{
            holder?.reminderTimeAD.text = "Reminded"
        }
    }
    fun updateReminders(newReminders: List<Reminder>)
    {
        reminderList.clear()
        reminderList.addAll(newReminders)
        notifyDataSetChanged()
    }

    fun deleteReminder(position: Int)
    {
        reminderList.removeAt(position)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return reminderList.size
    }
    fun getReminder(position: Int): Reminder
    {
        return reminderList[position]
    }
}