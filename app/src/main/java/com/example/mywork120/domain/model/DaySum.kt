package com.example.mywork120.domain.model

import java.time.LocalDate

data class DaySum(
    val date:LocalDate,
    val totalWork:Float = 0.0f,
    val practicWork: Float = 0f,
    val theoryWork : Float=0f,
    val isBaseDay : Boolean = false,
    val declareLst: List<Declare> = listOf()
)
