package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.repository.DeclareRepository
import java.time.LocalDate

class ShouldUpdateWeekSum(private val repository: DeclareRepository) {

    suspend operator fun invoke (weekId : String):String{

        var lastDecWeekId = try {
            repository.getLastDeclare().date
        }catch (e:Exception){
            //in case there is nothing in the db , no need to update anything
            return weekId
        }

        if (lastDecWeekId.dayOfWeek.value != 7)
            lastDecWeekId= lastDecWeekId.minusDays(lastDecWeekId.dayOfWeek.value.toLong())


        val lastDeclareWeekId = lastDecWeekId.toString()

        if(lastDeclareWeekId == weekId)
            return weekId

        val lastWeekSumId = try {
            repository.getLastWeekSum().weekId
        }catch (e:Exception){
            //if there is no weekSums object in the db , equals to date is not the same...
            return lastDeclareWeekId
        }

         if(lastDeclareWeekId == lastWeekSumId)
            return weekId

        else{
            return lastDeclareWeekId
        }


    }


}