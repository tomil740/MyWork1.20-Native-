package com.example.mywork120.domain.usecase

data class AllUseCase(
    val getWeekId: GetWeekId,
    val getSunday: GetSunday,
    val getStatAndTargets: GetStatAndTargets,
    val calculateWeekSum: CalculateWeekSum,
    val getWeekSumFlow: GetWeekSumFlow,
    val getDaySumByDate: GetDaySumByDate,
)
