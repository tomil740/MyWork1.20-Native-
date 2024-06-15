package com.example.mywork120.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mywork120.domain.model.Declare
import java.time.LocalDate

@Entity
data class DeclareEntity (
    @ColumnInfo(name = "date")
    var date: LocalDate = LocalDate.now(),

    @ColumnInfo(name = "work_time")
    var workTime:Float = 0.0f,

    @ColumnInfo(name = "is_home")
    var isHome:Boolean = true,

    @ColumnInfo(name = "work_description")
    var isProject:Boolean = true,

    @PrimaryKey(autoGenerate = true)
    var declareId :Int ,

    //will be the relation between daySum to a simple declare
    val daySumId : String = date.toString()
){
    fun toDeclareModelObj():Declare{

        val declare = this

        return  Declare(
            date = declare.date,
            workTime = declare.workTime,
            isProject = declare.isProject,
            isHome = declare.isHome,
            declareId = declare.declareId,
        )


    }
}