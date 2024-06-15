package com.example.mywork120.presentation.add_edit_builder

import com.example.mywork120.domain.usecase.OnlegalityCheck
import com.example.mywork120.presentation.util.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class AddEditsStatesAndEvents(
    val date: String,
    val onDateCheck: (String)->Unit,
    val onWorkTimeCheck: (String)->Unit,
    val day: String,
    val workTime: String,
    val isHome:Boolean,
    val onIsHomeChange: (Boolean) -> Unit,
    val isProject: Boolean,
    val onIsProjectChange: (Boolean) -> Unit,
    val errorMessage: String,
    val onSubmit : () -> Unit,
    val initializeItem : (Int) -> Unit,
    val onDelete : () -> Unit,
    val UiEvent : Flow<UiEvent>,
  //  val updateIdField : (Int) -> Unit
)


