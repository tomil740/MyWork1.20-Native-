package com.example.myworkbase.presentation.util


import com.example.mywork120.presentation.entry.EntryUiState
import com.example.mywork120.presentation.entry.util.TargetDataObj

data class EntryStatesAndEvents(
    val uiState:EntryUiState,
    val onSubmit : (TargetDataObj) ->Unit,
    val onDayTarg : (String) -> Unit,
    val onWeekTarg : (String) -> Unit,
    val onShowTargetBuilder : () -> Unit
)
