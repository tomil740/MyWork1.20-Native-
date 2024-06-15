package com.example.mywork120.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeekSumStatisticsEntity(
    val totalWork : Double,
    val practicWork : Double,
    val theoryWork : Double,
    val totalWeeks : Int,
    val avgWeek : Float,
    val avgDay : Float,
    @PrimaryKey(autoGenerate = false)
    val theId:Int = 1
)