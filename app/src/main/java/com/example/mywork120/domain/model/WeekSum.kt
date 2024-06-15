package com.example.mywork120.domain.model

import java.time.LocalDate

data class WeekSum(
    val weekWork:Float=0f,
    val practicWork:Float = 0f,
    val theoryWork:Float    =0f,
    val totalBaseDays:Int,
    val totalHomeDays:Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val weekId : String,
    val daySums : List<DaySum>
)
