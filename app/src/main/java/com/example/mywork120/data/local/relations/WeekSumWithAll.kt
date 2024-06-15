package com.example.mywork120.data.local.relations

import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.WeekSum


//this calss with help of the relational objects we created will mange all of theme in order to get from the db the model object all together
data class WeekSumWithAll(
    val weekSumWithDaySums: WeekSumWithDaySums,
    val daySumWithDeclaries: List<DaySumWithDeclaries>
){

    fun toModelObj():WeekSum{

        val daySums = mutableListOf<DaySum>()


        for (i in 0..weekSumWithDaySums.DaySumEntites.size){

            var current:DaySumWithDeclaries

            try {
                current = daySumWithDeclaries.get(i)
            }catch (e:Exception){
                continue
            }

            val declariesLst = current.declareEntity.map {
                Declare(
                    it.date,it.workTime,it.isHome,it.isProject,it.declareId
                )
            }

            daySums.add(
                DaySum(
                    current.daySumEntity.date,current.daySumEntity.totalWork,current.daySumEntity.practicWork,current.daySumEntity.theoryWork,
                    current.daySumEntity.isBaseDay, declareLst = declariesLst
                )
            )
        }

        return  WeekSum(
            weekSumWithDaySums.weekSumEntity.weekWork,weekSumWithDaySums.weekSumEntity.practicWork,weekSumWithDaySums.weekSumEntity.theoryWork,
            weekSumWithDaySums.weekSumEntity.totalBaseDays,weekSumWithDaySums.weekSumEntity.totalHomeDays,weekSumWithDaySums.weekSumEntity.startDate,
            weekSumWithDaySums.weekSumEntity.endDate,weekSumWithDaySums.weekSumEntity.weekId,daySums
        )

    }


    }


