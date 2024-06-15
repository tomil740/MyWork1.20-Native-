package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.domain.repository.DeclareRepository

class InsertWeekSum(private val repository: DeclareRepository) {

    suspend operator fun invoke(weekSumModel : WeekSum, dayTarget:Float, weekTarget : Float){
        repository.insertWeekSum(weekSumModel,dayTarget,weekTarget)
    }

}