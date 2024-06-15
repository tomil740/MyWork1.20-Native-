package com.example.mywork120.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mywork120.data.local.entities.DaySumEntity
import com.example.mywork120.data.local.entities.DeclareEntity
import com.example.mywork120.data.local.entities.TargetsEntity
import com.example.mywork120.data.local.entities.WeekSumEntity
import com.example.mywork120.data.local.entities.WeekSumStatisticsEntity
import com.example.mywork120.data.local.relations.DaySumWithDeclaries
import com.example.mywork120.data.local.relations.WeekSumWithAll
import com.example.mywork120.data.local.relations.WeekSumWithDaySums
import com.example.mywork120.domain.model.WeekSum
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface DeclareDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTargets(targetObj: TargetsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatisticsObj(weekSumStatisticsEntity: WeekSumStatisticsEntity)

    @Query("SELECT * FROM TargetsEntity WHERE theId = 1")
     fun getTargetsObj():TargetsEntity

    @Query("DELETE FROM DeclareEntity WHERE declareId = :theId")
    suspend fun deleteDeclareById(theId:Int)

    @Query("SELECT * FROM DeclareEntity WHERE declareId = :theId ")
    fun getDeclareById(theId:Int):DeclareEntity

    @Query("SELECT * FROM WeekSumStatisticsEntity WHERE theId = 1")
    fun getStatisticsObj():WeekSumStatisticsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeclare(declare: DeclareEntity)

    @Query("SELECT * FROM DeclareEntity WHERE date = :date ")
    fun getDeclaresByDate(date:LocalDate): Flow<List<DeclareEntity>>

    @Query("SELECT * FROM WeekSumEntity WHERE weekId = :weekId ")
    fun getWeekSumWithDaySums(weekId:String): WeekSumWithDaySums

    @Query("SELECT * FROM DaySumEntity WHERE daySumId = :daySumId ")
    fun getDaySumWithDeclaries(daySumId:String): DaySumWithDeclaries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeekSumEntity(weekSumEntity: WeekSumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaySum(daySumEntity: List<DaySumEntity>)

    @Transaction
    fun getWeekSumWithAll(weekId:String):WeekSumWithAll{

        val a =
            getWeekSumWithDaySums(weekId)

        val b = mutableListOf<DaySumWithDeclaries>()

        //we will map all of the meanings match to this word and initalize for each one the object
        a.DaySumEntites.forEach {
            b.add(getDaySumWithDeclaries(it.daySumId))
        }

        return(WeekSumWithAll(a,b))

    }

    @Transaction
    suspend fun insertWeeksum(weekSumModel : WeekSum, dayTarget:Float,weekTarget : Float){

        insertDaySum(weekSumModel.daySums.map { DaySumEntity(
            it.date,
            it.totalWork,
            it.practicWork,
            it.theoryWork,
            dayTarget,
            it.isBaseDay,
            daySumId = it.date.toString(),
            weekId = weekSumModel.weekId
            )
        })

        insertWeekSumEntity(WeekSumEntity(
            weekSumModel.weekWork,weekTarget,weekSumModel.practicWork,weekSumModel.theoryWork,weekSumModel.totalBaseDays,weekSumModel.totalHomeDays,
            weekSumModel.startDate,weekSumModel.endDate, weekSumModel.weekId
        ))


    }

    @Query("SELECT * FROM WeekSumEntity ORDER BY startDate DESC")
    fun getAllWeekSumsFlow(): Flow<List<WeekSumEntity>?>

    @Query("SELECT * FROM WeekSumEntity ORDER BY startDate DESC")
    fun getAllWeekSums(): List<WeekSumEntity>

    @Query("SELECT * FROM DeclareEntity ORDER BY declareId DESC LIMIT 1" )
    suspend fun getLastDeclare():DeclareEntity

    @Query("SELECT * FROM WeekSumEntity ORDER BY weekId DESC LIMIT 1" )
    suspend fun getLastWeekSum():WeekSumEntity


}