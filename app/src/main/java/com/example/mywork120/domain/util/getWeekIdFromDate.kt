package com.example.mywork120.domain.util

import java.time.LocalDate

fun getWeekIdFromDate(date: LocalDate):String{
    var current = (date.dayOfWeek.value)

    if (current == 7)
        return date.toString()

    return LocalDate.now().minusDays(current.toLong()).toString()
}

