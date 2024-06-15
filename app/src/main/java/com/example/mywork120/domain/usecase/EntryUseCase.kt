package com.example.mywork120.domain.usecase

data class EntryUseCase(
    val getSunday: GetSunday,
    val calculateWeekSum: CalculateWeekSum,
    val getDaySumByDate: GetDaySumByDate,
    val getWeekId: GetWeekId,
    val shouldUpdateWeekSum:ShouldUpdateWeekSum,
    val insertWeekSum: InsertWeekSum,
    val updateStatisticsObj:UpdateStatisticsObj,
    val getStatAndTargets:GetStatAndTargets,
    val onSubmitTargets:OnSubmitTargets
)
