package com.example.mywork120.domain.usecase

import android.util.Log
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.repository.DeclareRepository
import java.time.LocalDate

class OnSubmitDeclare(private val repository: DeclareRepository) {

    suspend operator fun invoke(date:String,atHome:Boolean,workTime:String,isProject:Boolean,theId:Int) {
    //this use case should get the data from the builder and insert it by calling repository function

        Log.i("on","obUseCase  $theId")

        repository.insertDeclare(
            Declare(LocalDate.parse(date), isHome = atHome, workTime = workTime.toFloat(),
                isProject = isProject, declareId = theId
            )
        )


    }


}