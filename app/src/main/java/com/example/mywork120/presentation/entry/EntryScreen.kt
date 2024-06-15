package com.example.mywork120.presentation.entry

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.presentation.entry.util.TargetDataObj
import com.example.mywork120.presentation.uiComponents.DaySumCircle
import com.example.mywork120.presentation.uiComponents.DeclareItemCover
import com.example.mywork120.presentation.uiComponents.WeekSumBarCover
import com.example.mywork120.presentation.util.getDayIndex
import com.example.myworkbase.presentation.util.EntryStatesAndEvents


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreen(navigateToEditDeclare: (Int)-> Unit,modifier: Modifier,entryStatesAndEvents: EntryStatesAndEvents) {

    val snackbarHostState = remember { SnackbarHostState() }
    var showDeclaresLst by remember { mutableStateOf(-1) }
    var declares by remember { mutableStateOf(listOf<Declare>())}



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {

        if (!declares.isEmpty()) {
            if (showDeclaresLst != -1) {
                DeclareItemCover(
                    modifier = modifier.fillMaxWidth(),
                    declares = declares,
                    { navigateToEditDeclare(it) }
                )
            } else {
                declares = listOf()

            }
        } else {

            Column(modifier = modifier.fillMaxSize(), Arrangement.SpaceBetween) {

                if(showDeclaresLst != -1){
                    declares = entryStatesAndEvents.uiState.weekSum.daySums.get(showDeclaresLst).declareLst
                }

                WeekSumBarCover(
                    weekSum = entryStatesAndEvents.uiState.weekSum,
                    targetsAndStat = entryStatesAndEvents.uiState.targetsAndStat,
                    onDaySumClick = {showDeclaresLst = it
                    },
                    modifier = modifier
                )

                val today = getDayIndex()

                DaySumCircle(
                    day = entryStatesAndEvents.uiState.weekSum.daySums.get(today).date.dayOfWeek.toString(),
                    value = entryStatesAndEvents.uiState.weekSum.daySums.get(today).totalWork,
                    dayTarget = entryStatesAndEvents.uiState.targetsAndStat.dayTarget,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 34.dp, end = 34.dp, bottom = 100.dp)

                )

            }

            Box(modifier = modifier) {
                Icon(imageVector = Icons.Rounded.Build, contentDescription = "",
                    modifier
                        .size(40.dp)
                        .padding(start = 18.dp, top = 18.dp)
                        .clickable { entryStatesAndEvents.onShowTargetBuilder() })
            }

            if (entryStatesAndEvents.uiState.showSetTargets) {

                var dayTarget by remember { mutableStateOf(entryStatesAndEvents.uiState.targetsAndStat.dayTarget.toString()) }
                var weekTarget by remember { mutableStateOf(entryStatesAndEvents.uiState.targetsAndStat.weekTarget.toString()) }

                Box(modifier = modifier.fillMaxWidth()) {
                    Column(modifier.align(Alignment.Center)) {
                        TextField(
                            value = dayTarget,
                            placeholder = { Text(text = "Day Target ....") },
                            onValueChange = {
                                dayTarget = it.filter { it.isDigit() || it.equals('.') }
                            },
                            modifier = modifier
                                .width(width = 150.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = modifier.height(16.dp))

                        TextField(
                            value = weekTarget,
                            placeholder = { Text(text = "Week Target ....") },
                            onValueChange = {
                                weekTarget = it.filter { it.isDigit() || it.equals('.') }
                            },
                            modifier = modifier
                                .width(width = 150.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = modifier.height(16.dp))

                        Button(onClick = {
                            entryStatesAndEvents.onSubmit(
                                TargetDataObj(
                                    dayTar = weekTarget,
                                    weekTar = dayTarget
                                )
                            )
                        }) {
                            Text(text = "Update Targets :")
                        }
                    }
                }

            }
        }
    }

}

