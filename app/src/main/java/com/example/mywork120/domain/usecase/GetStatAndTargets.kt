package com.example.mywork120.domain.usecase

import android.util.Log
import com.example.mywork120.domain.model.StatisticsWeeksData
import com.example.mywork120.domain.model.Targets
import com.example.mywork120.domain.model.TargetsAndStat
import com.example.mywork120.domain.repository.DeclareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetStatAndTargets(private val repository: DeclareRepository) {

   suspend operator fun invoke(): TargetsAndStat {

        var a = Targets(8f, 46f)
        var b = StatisticsWeeksData(1.0, 1.0, 1.0, 1, 1f, 1f)


        try {
            a = repository.getTargetsObj()
        } catch (e: Exception) {
           // throw
            val a = Exception("the exception is : $e ||proberaly try to run from main thread , anyway could be there is no object in the db " +
                    "(if its the first initializon ) , just make shure this" +
                    "the reason to throw that exception is because you could end up not notice why the data is not updating")
        }
       try {
           b = repository.getStatisticsObj()

       }catch (e:Exception){
           Log.i("exceptions","exception on getStateAndTargets use case , cause this is the first week and you try" +
                   "to calculate the data from the null history data" +
                   "the actual exception should be null poninter and is :$e")

       }

            return TargetsAndStat(
                avgDay = b.avgDay,
                avgWeek = b.avgWeek,
                weekTarget = a.weekTarget,
                dayTarget = a.dayTarget
            )

    }
}

