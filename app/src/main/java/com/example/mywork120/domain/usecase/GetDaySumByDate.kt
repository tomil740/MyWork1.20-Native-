package com.example.mywork120.domain.usecase

import android.util.Log
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.repository.DeclareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class GetDaySumByDate(private val repository: DeclareRepository) {

    suspend operator fun invoke(date:LocalDate): Flow<DaySum> {


         return repository.getDeclariesByDate(date).flatMapConcat {

             val date1:LocalDate = date
             var totalWork:Float = 0.0f
             var practicWork: Float = 0.0f
             var theoryWork : Float = 0.0f
             var isBaseDay : Boolean = false
             val declareLst =  it

            it.forEach {

                totalWork += it.workTime
                if (it.isProject)
                    practicWork+=it.workTime
                else{
                    theoryWork+=it.workTime
                }
                if (it.isHome)
                    isBaseDay = false

                else{
                    isBaseDay = true
                }

            }

             flow<DaySum> {  emit(DaySum(date1,totalWork,practicWork,theoryWork,isBaseDay,declareLst))}


         }


    }


}