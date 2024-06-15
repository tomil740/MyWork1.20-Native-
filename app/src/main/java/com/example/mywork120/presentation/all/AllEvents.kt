package com.example.mywork120.presentation.all

sealed class AllEvents {
    class OnNavToEntry(val route: String) : AllEvents()
    class OnNavToBuild(val route: String) : AllEvents()
    class OnWeekSumClick(val weekId1: Int) : AllEvents()
    class onDaySumClick(val theDayId: String) : AllEvents()
}
