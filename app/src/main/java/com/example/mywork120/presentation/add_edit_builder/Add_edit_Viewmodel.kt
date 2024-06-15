package com.example.myworkbase.presentation.add_edit_builder

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.usecase.AddEditUsecase
import com.example.mywork120.presentation.add_edit_builder.AddEditUiState
import com.example.mywork120.presentation.navigation.myWorkDestinationRoutes
import com.example.mywork120.presentation.util.UiEvent
import com.example.mywork120.presentation.add_edit_builder.Add_edit_events
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class Add_edit_Viewmodel @Inject constructor(
    private val addEditUsecase: AddEditUsecase
): ViewModel() {

    var uiState by mutableStateOf(AddEditUiState())
        private set


    //in order to send event from the viewmodel to the ui (for example after edit send back the to the list frag(frag action) )
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var initializeJob: Job? = null



    //this function will check and intialize the flows state if there is existing Todo object here or set deffault values to the new object to be created
    fun initializeLearningItem(theId:Int) {
        initializeJob?.cancel()
        //check if we open an todo or new one if its we load it to our ui states
        initializeJob = viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                if (theId != 0) {

                    var theObj: Declare = Declare(declareId = 0)

                    try {
                        theObj = addEditUsecase.getDeclareById.invoke(theId = theId)
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.i("error", "error e:$e")
                            sendUiEvent(UiEvent.ShowSnackbar("error while try to initialize object , this is new declare"))
                        }
                    }
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(
                            date = theObj.date.toString(),
                            atHome = theObj.isHome,
                            workTime = theObj.workTime.toString(),
                            isProject = theObj.isProject,
                            declareId = theObj.declareId
                        )

                    }
                    } else {
                        sendUiEvent(UiEvent.ShowSnackbar("This Is New Declare"))
                    }

            }
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddEditEvent(event: Add_edit_events) {
        when (event) {
            Add_edit_events.onDelete -> {

                viewModelScope.launch {
                    if (uiState.declareId != (0)) {
                        withContext(Dispatchers.IO) {
                            addEditUsecase.onDeleteDeclareById(uiState.declareId)
                        }
                    } else {
                        sendUiEvent(UiEvent.ShowSnackbar("there is no item to delete..."))
                    }
                    //in order to prevent from the nav to heapned while we try to send an toast an that wiil
                    //cause an error
                sendUiEvent(UiEvent.Navigate(myWorkDestinationRoutes.Entry.route))
                }

            }

            Add_edit_events.onSubmit -> {
                if (addEditUsecase.onlegalityCheck(uiState.date,uiState.workTime) != "Clear") {
                    sendUiEvent(UiEvent.ShowSnackbar("fix errors before try to send again"))

                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        addEditUsecase.onSubmitDeclare(
                            uiState.date,
                            uiState.atHome,
                            uiState.workTime,
                            uiState.isProject,
                            theId = uiState.declareId

                        )
                        sendUiEvent(UiEvent.ShowSnackbar("work"))
                        //to prevent crash we will send it in main scope and wait before
                        delay(200)
                        withContext(Dispatchers.Main) {
                            sendUiEvent(UiEvent.Navigate(myWorkDestinationRoutes.Entry.route))
                        }

                    }
                }
            }

            is Add_edit_events.onDateChange -> {
                val day = try {
                    LocalDate.parse(event.date).dayOfWeek.toString()
                }catch (e:Exception){"Eligal Date"}
                val mes = addEditUsecase.onlegalityCheck(event.date,uiState.workTime)
                uiState = uiState.copy(date = event.date, day = day, errorMessage = mes)
            }


            is Add_edit_events.onIsHomeChange -> {
                uiState = uiState.copy(atHome = event.isHome)
            }

            is Add_edit_events.onIsProjectChange -> {
                uiState = uiState.copy(isProject = event.isProject)
            }

            is Add_edit_events.onWorkTimeChange -> {
                val mes = addEditUsecase.onlegalityCheck(uiState.date,event.workTime)
                uiState = uiState.copy(workTime = event.workTime, errorMessage = mes)
            }

        }
    }

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }



}