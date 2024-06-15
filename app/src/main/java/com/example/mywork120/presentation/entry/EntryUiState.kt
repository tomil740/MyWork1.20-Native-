package com.example.mywork120.presentation.entry

import com.example.mywork120.domain.model.TargetsAndStat
import com.example.mywork120.domain.model.WeekSum

data class EntryUiState(
    val weekSum : WeekSum,
    val targetsAndStat: TargetsAndStat = TargetsAndStat(9f,9f,9f,9f),
    val showSetTargets : Boolean = false
)
