package com.example.mywork120.domain.usecase

import java.time.LocalDate

class OnlegalityCheck() {

    operator fun invoke (date:String,time:String):String{

        var result = "Clear"

        try {
            val a = LocalDate.parse(date)
            a
        }catch (e:Exception){
            result = "dateIsEligal"
        }

        try {
            val a = time.toDouble()
            a
        }catch (e:Exception){
            result += " , time is not legal"
        }

        return result
    }

}