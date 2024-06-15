package com.example.mywork120.presentation.add_edit_builder


import kotlinx.coroutines.flow.MutableStateFlow
import java.time.DayOfWeek
import java.time.LocalDate

data class AddEditUiState (

    val date:String  = LocalDate.now().toString(),
    val errorMessage:String = "WorkTimeEligal",
    val day : String = LocalDate.parse(date).dayOfWeek.toString(),
    val atHome:Boolean = false,
    val workTime:String = "",
    val isProject:Boolean = false,
    val declareId : Int = 0
)

