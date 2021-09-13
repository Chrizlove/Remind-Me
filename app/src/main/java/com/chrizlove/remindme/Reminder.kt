package com.chrizlove.remindme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminderTable")
data class Reminder( @ColumnInfo(name="reminderTitle")val title: String,
                    @ColumnInfo(name="reminderDesc")val desc: String,
                    @ColumnInfo(name="reminderMinute")val minute: String,
                    @ColumnInfo(name="reminderHour")val hour: String,
                    @ColumnInfo(name="reminderDay")val day: String,
                    @ColumnInfo(name="reminderMonth")val month: String,
                    @ColumnInfo(name="reminderYear")val year: String,
                    @ColumnInfo(name="timeDuringSet")val time: Int){
    @PrimaryKey(autoGenerate = true) var id=0
}

