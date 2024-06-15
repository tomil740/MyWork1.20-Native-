package com.example.mywork120.presentation.all

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.TargetsAndStat
import com.example.mywork120.domain.usecase.AllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllViewmodel@Inject constructor(
    private val allUseCase: AllUseCase
): ViewModel() {

    val weekStart = allUseCase.getSunday.invoke(LocalDate.now())

    var targetAndStatJob:Job? = Job()

    private val daySum1 =
        MutableStateFlow(DaySum(weekStart, 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum2 =
        MutableStateFlow(DaySum(weekStart.plusDays(1), 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum3 =
        MutableStateFlow(DaySum(weekStart.plusDays(2), 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum4 =
        MutableStateFlow(DaySum(weekStart.plusDays(3), 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum5 =
        MutableStateFlow(DaySum(weekStart.plusDays(4), 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum6 =
        MutableStateFlow(DaySum(weekStart.plusDays(5), 0.0f, 0.0f, 0.0f, false, listOf()))
    private val daySum7 =
        MutableStateFlow(DaySum(weekStart.plusDays(6), 0.0f, 0.0f, 0.0f, false, listOf()))

    private val weekSum =
        combine(daySum1, daySum2, daySum3, daySum4, daySum5, daySum6, daySum7) { array ->
            arrayOf(daySum1, daySum2, daySum3, daySum4, daySum5, daySum6, daySum7)
            allUseCase.calculateWeekSum.invoke(daySums1 = array.toList(),weekStart.toString())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(300),
            initialValue = allUseCase.calculateWeekSum.invoke(
                listOf(
                    daySum1.value,
                    daySum2.value,
                    daySum3.value,
                    daySum4.value,
                    daySum5.value,
                    daySum6.value,
                    daySum7.value
                ),weekStart.toString()
            )
        )

    var uiState by mutableStateOf(AllUiState(currentWeekSum = weekSum.value,listOf()))
        private set

    val a = updateTargetsAndStat()

    init {

        viewModelScope.launch {


            launch {
                weekSum.collect {
                    uiState = uiState.copy(currentWeekSum = it)
                }
            }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    allUseCase.getWeekSumFlow.invoke().collectLatest {
                        withContext(Dispatchers.Main) {
                            uiState = uiState.copy(archiveWeeksSum = it)
                        }
                    }
                } catch (e: Exception) {//}
                }
            }


                launch {
                    allUseCase.getDaySumByDate(daySum1.value.date).collect {
                        daySum1.value = it

                    }
                }
                launch {
                    allUseCase.getDaySumByDate(daySum2.value.date).collect {
                        daySum2.value = it

                    }
                }


                launch {
                    allUseCase.getDaySumByDate(daySum3.value.date).collect {
                        daySum3.value = it
                    }
                }
                launch {
                    allUseCase.getDaySumByDate(daySum4.value.date).collect {
                        daySum4.value = it
                    }
                }
                launch {
                    allUseCase.getDaySumByDate(daySum5.value.date).collect {
                        daySum5.value = it
                    }
                }
                launch {
                    allUseCase.getDaySumByDate(daySum6.value.date).collect {
                        daySum6.value = it
                    }
                }
                launch {
                    allUseCase.getDaySumByDate(daySum7.value.date).collect {
                        daySum7.value = it
                    }
                }

            }
        }
    fun updateTargetsAndStat() {
        targetAndStatJob?.cancel()
        targetAndStatJob = viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val a = allUseCase.getStatAndTargets.invoke()
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(targetsAndStat = a)
                }
            }
        }
    }


    fun onAllEvent(events: AllEvents){

        when(events){
            is AllEvents.OnNavToBuild -> TODO()
            is AllEvents.OnNavToEntry -> TODO()
            is AllEvents.OnWeekSumClick -> TODO()
            is AllEvents.onDaySumClick -> TODO()

        }

    }



}