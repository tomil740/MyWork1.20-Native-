package com.example.mywork120.presentation.entry

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork120.domain.model.DaySum
import com.example.mywork120.domain.model.TargetsAndStat
import com.example.mywork120.domain.usecase.EntryUseCase
import com.example.mywork120.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EntryViewmodel @Inject constructor(
    private val entryUseCase: EntryUseCase
): ViewModel() {

    var targetAndStatJob:Job? = Job()

    val weekStart = entryUseCase.getSunday.invoke(LocalDate.now())
    val weekId = weekStart.toString()

    private val daySum1 =
        MutableStateFlow(DaySum(weekStart))
    private val daySum2 = MutableStateFlow(DaySum(weekStart.plusDays(1)))
    private val daySum3 = MutableStateFlow(DaySum(weekStart.plusDays(2)))
    private val daySum4 = MutableStateFlow(DaySum(weekStart.plusDays(3)))
    private val daySum5 = MutableStateFlow(DaySum(weekStart.plusDays(4)))
    private val daySum6 = MutableStateFlow(DaySum(weekStart.plusDays(5)))
    private val daySum7 = MutableStateFlow(DaySum(weekStart.plusDays(6)))

    private val weekSum =
        combine(daySum1, daySum2, daySum3, daySum4, daySum5, daySum6, daySum7) { array ->
            arrayOf(daySum1, daySum2, daySum3, daySum4, daySum5, daySum6, daySum7)
            entryUseCase.calculateWeekSum.invoke(daySums1 = array.toList(),weekId)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(300),
            initialValue = entryUseCase.calculateWeekSum.invoke(
                listOf(
                    daySum1.value,
                    daySum2.value,
                    daySum3.value,
                    daySum4.value,
                    daySum5.value,
                    daySum6.value,
                    daySum7.value
                ),weekId
            )
        )

    var uiState by mutableStateOf(EntryUiState(weekSum.value))
        private set

    val intialize = updateTargetsAndStat()


    //in order to send event from the viewmodel to the ui (for example after edit send back the to the list frag(frag action) )
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    init {
        viewModelScope.launch {


            CoroutineScope(Dispatchers.IO).launch {
                val shouldUpdate =
                    entryUseCase.shouldUpdateWeekSum(weekId)

                if (shouldUpdate != weekId) {
                    val sunday =
                        LocalDate.parse(shouldUpdate)//entryUseCase.getSunday(getDateFromWeekId(shouldUpdate.toString()))
                    val t = entryUseCase.getDaySumByDate(sunday).stateIn(viewModelScope).value
                    val b =
                        entryUseCase.getDaySumByDate(sunday.plusDays(1))
                            .stateIn(viewModelScope).value
                    val c =
                        entryUseCase.getDaySumByDate(sunday.plusDays(2))
                            .stateIn(viewModelScope).value
                    val d =
                        entryUseCase.getDaySumByDate(sunday.plusDays(3))
                            .stateIn(viewModelScope).value
                    val e =
                        entryUseCase.getDaySumByDate(sunday.plusDays(4))
                            .stateIn(viewModelScope).value
                    val f =
                        entryUseCase.getDaySumByDate(sunday.plusDays(5))
                            .stateIn(viewModelScope).value
                    val g =
                        entryUseCase.getDaySumByDate(sunday.plusDays(6))
                            .stateIn(viewModelScope).value
                    val oldWeekSum = entryUseCase.calculateWeekSum(
                        listOf(t, b, c, d, e, f, g),
                        sunday.toString()
                    )
                    //need to get here the week target to insert with the object
                    entryUseCase.insertWeekSum(oldWeekSum, 0.0f, 0.0f)

                    entryUseCase.updateStatisticsObj()
                    updateTargetsAndStat()

                }
            }

            launch {
                weekSum.collect {
                    uiState = uiState.copy(weekSum = (it))
                    // Log.i("yy","${uiState.weekSum.weekWork}")
                }
            }

            launch {
                entryUseCase.getDaySumByDate(daySum1.value.date).collect {
                    daySum1.value = it
                    //   Log.i("yy","jjj${daySum1.value.declareLst.get(0).workTime}")

                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum2.value.date).collect {
                    daySum2.value = it
                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum3.value.date).collect {
                    daySum3.value = it
                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum4.value.date).collect {
                    daySum4.value = it
                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum5.value.date).collect {
                    daySum5.value = it
                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum6.value.date).collect {
                    daySum6.value = it
                }
            }
            launch {
                entryUseCase.getDaySumByDate(daySum7.value.date).collect {
                    daySum7.value = it
                }
            }
        }
    }


    fun updateTargetsAndStat() {
        targetAndStatJob?.cancel()
        targetAndStatJob = viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                val a = entryUseCase.getStatAndTargets.invoke()
                withContext(Dispatchers.Main) {
                   uiState = uiState.copy(targetsAndStat = a)
                }
            }
        }
    }
        fun onEntryEvent(event: EntryEvent){
            when (event) {

                EntryEvent.onAll -> TODO()

                EntryEvent.onAnalize -> TODO()

                EntryEvent.onNewDeclaer -> TODO()

                is EntryEvent.onDayTargetChange -> {

                    val a = event.dayTar.filter { it.isDigit() || it.equals('.') }

                    uiState = uiState.copy(targetsAndStat = uiState.targetsAndStat.copy(dayTarget = a.toFloat()))
                }

                is EntryEvent.onWeekTargetChange -> {
                    val a = event.weekTar.filter { it.isDigit() || it.equals('.') }

                    uiState = uiState.copy(targetsAndStat = uiState.targetsAndStat.copy(weekTarget = a.toFloat()))
                }

                is EntryEvent.onSubMitTargets -> {
                    viewModelScope.launch {


                         val a = entryUseCase.onSubmitTargets.invoke(
                               dayTar = event.targetObj.dayTar,
                               weekTar = event.targetObj.weekTar)
                        if (a) {
                            sendUiEvent(UiEvent.ShowSnackbar("secsess"))
                            updateTargetsAndStat()
                        }

                        else {
                            sendUiEvent(UiEvent.ShowSnackbar("values is eligal"))
                        }
                    }

                    uiState = uiState.copy(showSetTargets = false)

                }

                EntryEvent.onShowTargetBuilder -> {
                    uiState = uiState.copy(showSetTargets = !uiState.showSetTargets)
                }

            }
        }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }








}
