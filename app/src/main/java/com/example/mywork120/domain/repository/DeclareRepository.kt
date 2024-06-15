package com.example.mywork120.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mywork120.data.local.entities.TargetsEntity
import com.example.mywork120.data.local.entities.WeekSumEntity
import com.example.mywork120.data.local.entities.WeekSumStatisticsEntity
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.StatisticsWeeksData
import com.example.mywork120.domain.model.Targets
import com.example.mywork120.domain.model.WeekSum
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DeclareRepository {

   suspend fun insertDeclare(declare: Declare):Boolean

    fun getDeclariesByDate(date : LocalDate) : Flow<List<Declare>>

    suspend fun getLastDeclare():Declare

    suspend fun deleteDeclareById(theId: Int)

    suspend fun getLastWeekSum(): WeekSum

    suspend fun insertWeekSum(weekSum: WeekSum,weekTarget:Float,dayTarget:Float)

    suspend fun insertTargets(targetObj: Targets)

    suspend fun insertStatisticsObj(statisticsObj: StatisticsWeeksData)

    fun getTargetsObj(): Targets

    fun getStatisticsObj(): StatisticsWeeksData

    suspend fun getAllWeekSumFlow():Flow<List<WeekSum>>

    suspend fun getAllWeekSum():List<WeekSum>

    suspend fun getDeclareById ( theId : Int):Declare





 }