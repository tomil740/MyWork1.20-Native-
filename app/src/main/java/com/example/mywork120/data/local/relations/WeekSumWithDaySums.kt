package com.example.mywork120.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mywork120.data.local.entities.DaySumEntity
import com.example.mywork120.data.local.entities.WeekSumEntity

data class WeekSumWithDaySums(
    //the embedded annotation mark this argument as the 1 object entity
    @Embedded
    val weekSumEntity: WeekSumEntity,
    //the annotation @relation mark in which attribute they connected to each other , in order to set room
    //to search for the match entity in this database
    @Relation(
        parentColumn = "weekId",
        entityColumn = "weekId"
    )
    val DaySumEntites: List<DaySumEntity>
)
