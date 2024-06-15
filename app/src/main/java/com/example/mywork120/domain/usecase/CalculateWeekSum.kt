package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.WeekSum
import java.time.LocalDate

class CalculateWeekSum() {

    operator fun invoke(daySums1 : List<DaySum>,weekId:String):WeekSum{

        var workSum = 0.0f
        var practicWork:Float = 0.0f
        var theoryWork:Float =0.0f
        var totalBaseDays:Int =0
        var totalHomeDays:Int = 0
        var startDate: LocalDate = LocalDate.now()
        var endDate: LocalDate = LocalDate.now()
        val daySums : List<DaySum> = daySums1


        for (i in daySums1){

            workSum+=i.totalWork
            practicWork+=i.practicWork
            theoryWork+=i.theoryWork
            if(i.isBaseDay)
                totalBaseDays+=1
            else{
                totalHomeDays+=1
            }

            if (i.date.dayOfWeek.value == 7)
                startDate = i.date

            else if(i.date.dayOfWeek.value == 6)
                endDate = i.date

        }


        return WeekSum(workSum,practicWork, theoryWork, totalBaseDays, totalHomeDays, startDate, endDate,weekId, daySums)

    }

}