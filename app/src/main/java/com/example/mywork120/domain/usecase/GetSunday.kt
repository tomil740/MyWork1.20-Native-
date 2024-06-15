package com.example.mywork120.domain.usecase

import java.time.LocalDate

class GetSunday() {

    operator fun invoke(date:LocalDate):LocalDate{
        var current = (date.dayOfWeek.value)

        if (current == 7)
            return LocalDate.now()

        return LocalDate.now().minusDays(current.toLong())
    }

}