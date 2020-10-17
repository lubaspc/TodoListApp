package com.lubinpc.todolist.utils

import java.util.*

object CalendarUtils {

    fun getDateFormat(calendar: Calendar):String {
        return "${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                "${calendar.get(Calendar.MONTH) + 1}-"+
                "${calendar.get(Calendar.YEAR)}"
    }

    fun getTimeFormat(calendar: Calendar):String{
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minus = calendar.get(Calendar.MINUTE)
        return "$hours:$minus"
    }

    fun fullFormat(calendar: Calendar) = "${getDateFormat(calendar)} ${getTimeFormat(calendar)}"
}