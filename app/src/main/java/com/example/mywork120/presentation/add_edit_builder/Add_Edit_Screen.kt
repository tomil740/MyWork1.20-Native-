package com.example.myworkbase.presentation.add_edit_builder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.mywork120.R
import com.example.mywork120.presentation.add_edit_builder.AddEditsStatesAndEvents
import com.example.mywork120.presentation.navigation.myWorkDestinationRoutes
import com.example.mywork120.presentation.util.UiEvent
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add_Edit_Screen(navToWithOutArg: (String) -> Unit,
     modifier: Modifier, addEditsStatesAndEvents: AddEditsStatesAndEvents,
                    theId:Int= 0
) {

    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        floatingActionButton = {
        },snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
        ) {

        LaunchedEffect(true){

            addEditsStatesAndEvents.initializeItem(theId)


            addEditsStatesAndEvents.UiEvent.collectLatest {

                when (it) {
                    is UiEvent.Navigate -> {
                        navToWithOutArg(it.route)
                    }
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(it.message)
                    }

                    is UiEvent.onNewDeclare -> {
                        //newDeclareNotification(it.whichWeek)
                        navToWithOutArg(myWorkDestinationRoutes.Entry.route)
                    }
                }

            }

        }

        Column(modifier = modifier.fillMaxSize()) {

            //NavMenue({navToWithOutArg(it)}, screen = "Declare", modifier = modifier)

            Spacer(modifier = modifier.size(45.dp))

            Builder_Fields(addEditsStatesAndEvents, modifier)
        }
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Builder_Fields(
        addEditsStatesAndEvents: AddEditsStatesAndEvents,
        modifier: Modifier
    ) {
        Column(modifier = modifier) {

            //Date and day row ...

            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = addEditsStatesAndEvents.day,
                    modifier = modifier.align(Alignment.CenterVertically)
                )

                val c = stringResource(R.string.dateEditField )


                TextField(
                    value = addEditsStatesAndEvents.date,
                    onValueChange = {
                        addEditsStatesAndEvents.onDateCheck(it)
                    },
                    modifier = modifier.width(width = 150.dp).semantics {contentDescription = c }

                )
            }

            //Switches and there headers||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

            //IsHome Switch
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Switch(checked = addEditsStatesAndEvents.isHome, onCheckedChange = {addEditsStatesAndEvents.onIsHomeChange(it)})
                Text(text = "At Home?",modifier.align(Alignment.CenterVertically))
            }

            //IsProject Switch

            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Switch(checked = addEditsStatesAndEvents.isProject, onCheckedChange = {addEditsStatesAndEvents.onIsProjectChange(it)})
                Text(text = "Is Project?",modifier.align(Alignment.CenterVertically))
            }

            Spacer(modifier = modifier.size(60.dp))

            //The WorkTime part

            val b = stringResource(R.string.workTimeEditField )

            TextField(
                value = addEditsStatesAndEvents.workTime,
                onValueChange = { addEditsStatesAndEvents.onWorkTimeCheck(it) },
                modifier = modifier
                    .width(width = 150.dp)
                    .align(Alignment.CenterHorizontally)
                    .semantics { contentDescription = b}
            )

            Spacer(modifier = modifier.size(90.dp))

            val d = stringResource(R.string.errorField )

            Text(text = addEditsStatesAndEvents.errorMessage,
                modifier.align(alignment = Alignment.CenterHorizontally).semantics { contentDescription =  d })

            Spacer(modifier = modifier.size(65.dp))

            //The sum Buttons of the builder

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)) {

                val a = stringResource(R.string.subMitDeclareBut )

                Button(onClick = { addEditsStatesAndEvents.onSubmit()}
                , modifier =  modifier.semantics {contentDescription = a}) {

                    Text(text = "SubMit")

                }

                Button(onClick = {addEditsStatesAndEvents.onDelete()}){//addEditsStatesAndEvents.onDelete()}) {

                    Text(text = "Delete")

                }

            }


        }
    }


