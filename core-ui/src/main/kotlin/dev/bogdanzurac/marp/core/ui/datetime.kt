package dev.bogdanzurac.marp.core.ui

import android.content.Context
import android.text.format.DateFormat
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

enum class DateTimeAttribute(val format: String) {
    /**
     * `d MMMM yyyy HH:mm` pattern
     */
    DATE_TIME_SHORT("d MMMM HH:mm"),

    /**
     * `d MMM yyyy` pattern
     */
    DAY_MONTH_YEAR("d MMM yyyy"),
}

fun Instant.toLocalDateTime(): LocalDateTime = toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.format(context: Context, attribute: DateTimeAttribute): String {
    val is24HourFormat = DateFormat.is24HourFormat(context)
    val hoursFormat = if (is24HourFormat) "HH" else "hh"
    val locale: Locale = Locale.getDefault()
    val format = attribute.format.replace("HH", hoursFormat)
    val pattern = DateFormat.getBestDateTimePattern(locale, format)
    return toJavaLocalDateTime()
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDate.toLocalDateTime(): LocalDateTime =
    toJavaLocalDate().atStartOfDay().toKotlinLocalDateTime()