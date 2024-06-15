package com.example.mywork120.repository

import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.StatisticsWeeksData
import com.example.mywork120.domain.model.Targets
import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.domain.repository.DeclareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate

class FakeRepository:DeclareRepository {


    var cureentWeekDeclaries = mutableListOf<Declare>(
        Declare(
            LocalDate.now().minusDays(3),
            5f,false,false,1
        )
    )

    private val getdeclariesDataFlow =
        MutableSharedFlow<List<Declare>>(replay = 1)


    override suspend fun insertDeclare(declare: Declare): Boolean {
        try {
            cureentWeekDeclaries.add(declare)
            getdeclariesDataFlow.emit(cureentWeekDeclaries)
        }catch (e:Exception){
            return false
        }

        return true
    }

    override fun getDeclariesByDate(date: LocalDate): Flow<List<Declare>> {
        return getdeclariesDataFlow
    }

    override suspend fun getLastDeclare(): Declare {
        return cureentWeekDeclaries.last()
    }

    override suspend fun deleteDeclareById(theId: Int) {
        val a= cureentWeekDeclaries.filter { it.declareId == theId }
        cureentWeekDeclaries.remove(a.get(0))
        getdeclariesDataFlow.emit(cureentWeekDeclaries)
    }

    override suspend fun getLastWeekSum(): WeekSum {
        TODO("Not yet implemented")
    }

    override suspend fun insertWeekSum(weekSum: WeekSum, weekTarget: Float, dayTarget: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun insertTargets(targetObj: Targets) {
        TODO("Not yet implemented")
    }

    override suspend fun insertStatisticsObj(statisticsObj: StatisticsWeeksData) {
        TODO("Not yet implemented")
    }

    override fun getTargetsObj(): Targets {
       return Targets(5f,5f)
    }

    override fun getStatisticsObj(): StatisticsWeeksData {
      return  StatisticsWeeksData(5.5,5.5,5.5,5,5f,5f)
    }

    override suspend fun getAllWeekSumFlow(): Flow<List<WeekSum>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWeekSum(): List<WeekSum> {
        TODO("Not yet implemented")
    }

    override suspend fun getDeclareById(theId: Int): Declare {
        TODO("Not yet implemented")
    }
}