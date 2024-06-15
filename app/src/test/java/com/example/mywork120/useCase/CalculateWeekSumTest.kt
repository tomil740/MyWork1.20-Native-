package com.example.mywork120.useCase

import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.domain.usecase.CalculateWeekSum
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.math.roundToInt
import kotlin.random.Random

class CalculateWeekSumTest {

    @Before
    fun setup(){
    }


    @Test
    fun `Week Sum object properlly calculate`(){

        val current = LocalDate.now().dayOfWeek.value
        var sunday = LocalDate.now()

        if(current != 7)
            sunday = sunday.minusDays(current.toLong())


        val dates = listOf<LocalDate>(sunday,sunday.plusDays(1),sunday.plusDays(2),sunday.plusDays(3),
            sunday.plusDays(4), sunday.plusDays(5),sunday.plusDays(6))

        val daySumLst = dates .map {
            val totalWork = Random.nextInt(12)+ Random.nextFloat()
            val practic = Random.nextInt(11)+ Random.nextFloat()
                val theory =totalWork-practic

            DaySum(
                 date = it,
                 totalWork = totalWork,
                 practicWork =  practic ,
                 theoryWork= theory,
                 isBaseDay= Random.nextBoolean()
            )

        }

        val result = CalculateWeekSum().invoke(daySumLst,sunday.toString())

        var weekWork: Float = 0f
        var practicWork: Float = 0f
        var theoryWork: Float = 0f
        var totalBaseDays: Int = 0
        var totalHomeDays: Int = 0
        var startDate = LocalDate.now()
        var endDate = LocalDate.now()
        var daySums: List<DaySum> = daySumLst
        var weekId: String = ""


        for (i in daySumLst){
            weekWork+=i.totalWork
            practicWork+=i.practicWork
            theoryWork+=i.theoryWork

            if(i.isBaseDay)
                totalBaseDays+=1

            else if(!i.isBaseDay)
                totalHomeDays+=1

            if(i.date.dayOfWeek.value == 7) {
                startDate = i.date
                weekId = i.date.toString()
            }

            if(i.date.dayOfWeek.value == 6)
                endDate =i.date

        }
        val testRes = WeekSum(weekWork,practicWork,theoryWork,totalBaseDays,totalHomeDays,startDate,endDate,weekId,daySums)


        assertEquals(result,testRes)

    }
}