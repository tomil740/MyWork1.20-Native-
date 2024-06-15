package com.example.mywork120.presentation.util

import java.time.LocalDate

fun getDayIndex():Int{
    val  a = LocalDate.now().dayOfWeek.value

    if(a == 7){
        return 0
    }

    return a

}