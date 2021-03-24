package com.friendroids.moneymana.utils.extensions

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 *  используем для работы с датами
 *    setCurrentDateTimeToString() - получает текущую дату и возвращает строку вида
 *     "21.03.2021 23:11"
 *
 *    setDateTimeStringToLong() - из строки вида "21.03.2021 23:11" формирует значение даты
 *    в формате Long для работы с базой данных
 *
 *   setDateTimeStringFromLong() - из переменной Long возвращает строку вида
 *     "21.03.2021 23:11"
 * */
class DateTimeConverter {
    fun setCurrentDateTimeToString(): String =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val currentDate = Date()
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(currentDate)
            } else {
                val formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
                LocalDateTime.now(ZoneId.systemDefault()).format(formatDateTime)
            }

    fun setDateTimeStringToLong(dateTime: String): Long =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val dateFormat: Date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).parse(dateTime)!!
                dateFormat.time.div(1000)
            } else {
                val format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
                LocalDateTime.parse(dateTime, format).toEpochSecond(ZoneOffset.UTC)
            }

    fun setDateTimeStringFromLong(dateTime: Long): String =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val dateTimeLong = Date(dateTime * 1000)
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(dateTimeLong)
            } else {
                val formatDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())
                LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC).format(formatDateTime)
            }
}