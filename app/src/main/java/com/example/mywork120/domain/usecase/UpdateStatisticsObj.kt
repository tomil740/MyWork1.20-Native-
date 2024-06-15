package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.StatisticsWeeksData
import com.example.mywork120.domain.repository.DeclareRepository
import java.time.LocalDate


class UpdateStatisticsObj(private val repository: DeclareRepository) {

   suspend  operator fun invoke(){

        val allWeekSum = repository.getAllWeekSum()

           var totalWork : Double = 0.0
           var practicWork : Double = 0.0
           var theoryWork : Double = 0.0
           var totalWeeks : Int = 0
           var avgWeek : Double = 0.0
           var avgDay : Double = 0.0

           for (i in allWeekSum){
               totalWeeks+=1
               totalWork+=i.weekWork
               practicWork+=i.practicWork
               theoryWork+=i.theoryWork
           }

       avgWeek = totalWork/(totalWeeks.toDouble())
       avgDay = totalWork/(totalWeeks*7.0)

       repository.insertStatisticsObj(
           StatisticsWeeksData(
              totalWork,practicWork,theoryWork,totalWeeks,avgWeek.toFloat(),avgDay.toFloat()
           )
       )


    }

}