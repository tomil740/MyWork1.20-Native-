package com.example.mywork120.domain.model


import java.time.LocalDate

data class Declare(
    var date: LocalDate = LocalDate.now(),

    var workTime:Float = 0.0f,

    var isHome:Boolean = true,

    var isProject:Boolean = true,

    var declareId :Int ,
)
