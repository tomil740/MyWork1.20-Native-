package com.example.mywork120.presentation.all

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.presentation.uiComponents.DeclareItemCover
import com.example.mywork120.presentation.uiComponents.WeekSumBarCover
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun All_Screen(navigateToEditDeclare: (Int)-> Unit,modifier: Modifier,allStateAndEvents: AllStatesAndEvents) {

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
                    { navigateToEditDeclare(it)}
                )
            }
        } else {

            LazyColumn(modifier = modifier.fillMaxSize()) {

                item {
                    if (showDeclaresLst != -1) {
                        declares =
                            allStateAndEvents.allUiState.currentWeekSum.daySums.get(showDeclaresLst).declareLst


                    } else {
                        declares = listOf()
                    }


                    WeekSumBarCover(
                        weekSum = allStateAndEvents.allUiState.currentWeekSum,
                        targetsAndStat = allStateAndEvents.allUiState.targetsAndStat,
                        modifier = modifier.fillMaxWidth(),
                        onDaySumClick = { showDeclaresLst = it },
                    )
                }

                if (!allStateAndEvents.allUiState.archiveWeeksSum.isEmpty()) {

                    items(allStateAndEvents.allUiState.archiveWeeksSum) {

                        Spacer(modifier = modifier.height(16.dp))

                        WeekSumBarCover(
                            weekSum = it,
                            targetsAndStat = allStateAndEvents.allUiState.targetsAndStat,
                            onDaySumClick = { showDeclaresLst = it },
                            modifier = modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 50.dp,
                                        topStart = 50.dp
                                    )
                                )
                        )


                    }
                }


            }
        }
    }
}
