package com.sberkozd.lettervault

import java.text.SimpleDateFormat
import java.util.*

fun String.convertToDateRepresentation(): String {
    val timestamp = this.toLong()
    var date = Date(timestamp)
    var calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.HOUR_OF_DAY,3) // GMT +3
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.GERMAN)
    return formatter.format(calendar.time)
}