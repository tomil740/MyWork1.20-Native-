package com.example.mywork120.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.WeekSum
import java.time.LocalDate

@Entity
data class DaySumEntity(
    val date: LocalDate,
    val totalWork:Float,
    val dayTarget : Float,
    val practicWork: Float,
    val theoryWork : Float,
    val isBaseDay : Boolean,
    val weekId : String,
    @PrimaryKey(autoGenerate = false)
    val daySumId : String
)
{
    fun toModelObj (): DaySum {
        return DaySum(
            this.date,
            this.totalWork,
            this.theoryWork,
            this.practicWork,
            this.isBaseDay,
            listOf()
        )
    }
}