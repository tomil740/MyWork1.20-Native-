package com.example.mywork120.data.repository

import com.example.mywork120.data.local.DeclareDao
import com.example.mywork120.data.local.entities.DeclareEntity
import com.example.mywork120.data.local.entities.TargetsEntity
import com.example.mywork120.data.local.entities.WeekSumEntity
import com.example.mywork120.data.local.entities.WeekSumStatisticsEntity
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.StatisticsWeeksData
import com.example.mywork120.domain.model.Targets
import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.domain.repository.DeclareRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class DeclareRepositoryImpl(private val dao:DeclareDao):DeclareRepository {

    override suspend fun insertDeclare(declare: Declare): Boolean {

        val toSend = DeclareEntity(
            date = declare.date,
            workTime = declare.workTime,
            isProject = declare.isProject,
            isHome = declare.isHome,
            declareId = declare.declareId,
            daySumId = declare.date.toString()
        )

        try {
            dao.insertDeclare(declare = toSend)
        } catch (e: Exception) {
            return false
        }

        return true
    }




    override fun getDeclariesByDate(date: LocalDate): Flow<List<Declare>> {
        return dao.getDeclaresByDate(date).map { it.map { it.toDeclareModelObj() } }
    }

    override suspend fun getLastDeclare(): Declare {
     return  dao.getLastDeclare().toDeclareModelObj()
    }

    override suspend fun getLastWeekSum(): WeekSum {
        return dao.getLastWeekSum().toModelObj()
    }

    override suspend fun insertWeekSum(weekSum: WeekSum, weekTarget: Float, dayTarget: Float) {
        dao.insertWeeksum(
            weekSum,dayTarget,weekTarget
        )
    }

    override suspend fun insertTargets(targetObj: Targets) {
        dao.insertTargets(
            TargetsEntity(
                targetObj.weekTarget,
                targetObj.dayTarget
            )
        )
    }

    override suspend fun insertStatisticsObj(statisticsObj: StatisticsWeeksData) {
       dao.insertStatisticsObj(
           WeekSumStatisticsEntity(
               statisticsObj.totalWork,statisticsObj.practicWork,statisticsObj.theoryWork,
               statisticsObj.totalWeeks,statisticsObj.avgWeek,statisticsObj.avgDay
           )
       )
    }

    override suspend fun deleteDeclareById(theId: Int){
        dao.deleteDeclareById(theId)
    }

    override fun getTargetsObj(): Targets {

        val a =  dao.getTargetsObj()

        return Targets(a.weekTarget,a.dayTarget)

    }

    override fun getStatisticsObj(): StatisticsWeeksData {
        val a =   dao.getStatisticsObj()


         return    StatisticsWeeksData(
               a.totalWork,a.practicWork,a.theoryWork,a.totalWeeks,a.avgWeek,a.avgDay)


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllWeekSumFlow(): Flow<List<WeekSum>> {
        return dao.getAllWeekSumsFlow().flatMapConcat { weekSumList ->

             val result: MutableList<WeekSum> = mutableListOf()

            weekSumList?.forEach {
                val a = dao.getWeekSumWithAll(it.weekId)
                val weekSum = a.weekSumWithDaySums.weekSumEntity.toModelObj()
                val daySums = mutableListOf<DaySum>()
                a.daySumWithDeclaries.forEach{
                    daySums.add(it.daySumEntity.toModelObj().copy(declareLst = it.declareEntity.map { it.toDeclareModelObj() }))
                }

                result.add(weekSum.copy(daySums = daySums))
            }

             flow<List<WeekSum>> {
                 emit(result)
             }
        }
    }

    override suspend fun getAllWeekSum(): List<WeekSum> {
        return dao.getAllWeekSums().map {
            it.toModelObj()
        }
    }

    override suspend fun getDeclareById ( theId : Int):Declare{
       return dao.getDeclareById(theId = theId).toDeclareModelObj()
    }


}
