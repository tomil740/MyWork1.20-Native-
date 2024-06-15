package com.example.mywork120.presentation.entry

import com.example.mywork120.presentation.entry.util.TargetDataObj

sealed class EntryEvent {
    object onAnalize:EntryEvent()
    object onAll:EntryEvent()
    object onNewDeclaer:EntryEvent()
    data class onDayTargetChange(val dayTar:String) : EntryEvent()
    data class onWeekTargetChange(val weekTar : String) : EntryEvent()
    data class onSubMitTargets(val targetObj : TargetDataObj) : EntryEvent()
    object onShowTargetBuilder : EntryEvent()
}