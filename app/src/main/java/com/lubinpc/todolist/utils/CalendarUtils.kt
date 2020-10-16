package com.lubinpc.todolist.utils

import java.util.*

object CalendarUtils {

    fun getDateFormat(calendar: Calendar):String {
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                "${calendar.get(Calendar.MONTH)}-"+
                "${calendar.get(Calendar.YEAR)}"
    }

    fun getTimeFormat(calendar: Calendar):String{
        val hours = calendar.get(Calendar.HOUR)
        val minus = calendar.get(Calendar.MINUTE)
        val timeString = StringBuilder()
        timeString.append(if (hours < 10) "0$hours" else hours)
        timeString.append(":")
        timeString.append(if (minus < 10) "0$minus" else minus)
        timeString.append(" ${calendar.getDisplayName(Calendar.AM_PM,Calendar.LONG,
            Locale.getDefault())}")
        return timeString.toString()
    }

    fun fullFormat(calendar: Calendar) = "${getDateFormat(calendar)} ${getTimeFormat(calendar)}"
}