package com.example.mywork120.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TargetsEntity(
    val weekTarget:Float = 44.0f,
    val dayTarget : Float = 9f,
    @PrimaryKey(autoGenerate = false)
     val theId:Int = 1
)
