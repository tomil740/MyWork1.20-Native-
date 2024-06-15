package com.example.mywork120.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mywork120.data.local.convertor.convert
import com.example.mywork120.data.local.entities.DaySumEntity
import com.example.mywork120.data.local.entities.DeclareEntity
import com.example.mywork120.data.local.entities.TargetsEntity
import com.example.mywork120.data.local.entities.WeekSumEntity
import com.example.mywork120.data.local.entities.WeekSumStatisticsEntity


@Database(
    entities = [
        DeclareEntity::class,
        WeekSumEntity::class,
        DaySumEntity::class,
        TargetsEntity::class,
        WeekSumStatisticsEntity::class
    ],
    version = 5
)
@TypeConverters(convert::class)
abstract class  DeclaerDatabase: RoomDatabase() {

    abstract val declareDao: DeclareDao
}