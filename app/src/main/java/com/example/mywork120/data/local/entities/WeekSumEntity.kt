package com.example.mywork120.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.WeekSum
import java.time.LocalDate

@Entity
data class WeekSumEntity(
    val weekWork:Float,
    val weekTarget : Float,
    val practicWork:Float,
    val theoryWork:Float,
    val totalBaseDays:Int,
    val totalHomeDays:Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    @PrimaryKey(autoGenerate = false)
    val weekId : String
){
    fun toModelObj ():WeekSum{
        return WeekSum(
             this.weekWork,
            this.practicWork,
            this.theoryWork,
            this.totalBaseDays,
            this.totalHomeDays,
            this.startDate,
            this.endDate,
            this.weekId ,
            listOf()
        )
    }
}
