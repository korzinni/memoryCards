package ru.id_east.gm.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun getNewsDate(date: Date?): String? {
        if (date == null)
            return null
        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return format.format(date)
    }

    @JvmStatic
    fun getCommentDate(date: Date?): String? {
        if (date == null)
            return null
        val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return format.format(date)
    }

    @JvmStatic
    fun getToday(pattern: String): String? {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }
}