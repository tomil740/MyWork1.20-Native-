package com.example.mywork120.domain.usecase

import java.time.LocalDate

class GetWeekId() {

    operator fun invoke(date : LocalDate = LocalDate.now()):Int{
        //will get thursday , so the monday to sunday week dosnt make any different for us here
        //and the week will be +1 in order to keep counting from 1 and not 0
        return (date.year+((date.dayOfYear/7)+1)*10000)

    }

}