package com.example.mywork120.domain.usecase

import android.util.Log
import com.example.mywork120.domain.model.Targets
import com.example.mywork120.domain.repository.DeclareRepository

class OnSubmitTargets(private val repository: DeclareRepository) {

    suspend operator fun invoke(dayTar:String, weekTar:String):Boolean{
        try {
            val a = dayTar.toFloat()
            val b = weekTar.toFloat()
            if(a <= 0 || b<=0)
                return false
            else {
                repository.insertTargets(Targets(a, b))
            }
        }catch (e:Exception){
            Log.i("rr","the : e:$e")
            return false
        }

        return true
    }

}