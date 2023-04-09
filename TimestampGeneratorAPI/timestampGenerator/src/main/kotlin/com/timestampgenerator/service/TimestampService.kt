package com.timestampgenerator.service

import com.timestampgenerator.domain.Timestamp
import com.timestampgenerator.domain.TimestampError
import org.springframework.stereotype.Service
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@Service
class TimestampService {
    var dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    val defaultDateFormats = arrayOf(
        "dd-MM-yyyy",
        "d-MM-yyyy",
        "dd-M-yyyy",
        "d-M-yyyy",
    )

    fun getCurrentTimestamp(): Timestamp {
        val zonedDateTime = ZonedDateTime.now()
        val instant = zonedDateTime.toInstant()

        return Timestamp(zonedDateTime.format(this.dateFormatter), instant.toEpochMilli())
    }

    fun getFixedTimestamp(dateFormat: String): Any {
        return dateFormat.toLongOrNull()?.let { parseUnix(it) }
            ?: dateValidator(dateFormat)?.let { parseFixedDate(it) }
            ?: TimestampError("Invalid date")
    }

    private fun parseUnix(unixDate: Long): Timestamp {
        val utcDate = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(unixDate),
            ZoneId.systemDefault(),
        ).format(dateFormatter)

        return Timestamp(utcDate, unixDate)
    }

    private fun parseFixedDate(localDate: LocalDate): Timestamp {
        val localDateTime = LocalDateTime.of(localDate, LocalTime.MIDNIGHT)
        val zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault())

        return Timestamp(zonedDateTime.format(dateFormatter).toString(), zonedDateTime.toInstant().toEpochMilli())
    }

    private fun dateValidator(dateFormat: String): LocalDate? {
        for (format in defaultDateFormats) {
            try {
                val formatter = DateTimeFormatter.ofPattern(format)
                return LocalDate.parse(dateFormat, formatter)
            } catch (e: DateTimeParseException) {
                println(e.message)
            }
        }

        return null
    }
}