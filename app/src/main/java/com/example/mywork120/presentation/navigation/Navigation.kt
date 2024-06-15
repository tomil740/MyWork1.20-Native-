package com.example.mywork120.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mywork120.presentation.add_edit_builder.AddEditsStatesAndEvents
import com.example.mywork120.presentation.all.AllStatesAndEvents
import com.example.mywork120.presentation.all.AllViewmodel
import com.example.mywork120.presentation.all.All_Screen
import com.example.mywork120.presentation.entry.EntryEvent
import com.example.mywork120.presentation.entry.EntryScreen
import com.example.mywork120.presentation.entry.EntryViewmodel
import com.example.mywork120.presentation.add_edit_builder.Add_edit_events
import com.example.myworkbase.presentation.add_edit_builder.Add_Edit_Screen
import com.example.myworkbase.presentation.add_edit_builder.Add_edit_Viewmodel
import com.example.myworkbase.presentation.util.EntryStatesAndEvents

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {

    //set the nav controller for this app navigation
    val navController = rememberNavController()

    fun NavHostController.navigateSingleTopTo(route: String) {
        if (route == "add_edit") {
            this.navigate("${myWorkDestinationRoutes.Add_Edit.route}/0") { launchSingleTop = true }
        } else {
            this.navigate(route) { launchSingleTop = true }
        }
    }

    fun NavHostController.navigateToEditDeclare(declareType: Int) {
        this.navigateSingleTopTo("${myWorkDestinationRoutes.Add_Edit.route}/${declareType}")
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = null, tint = Color.White) },
                        label = { Text(screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigateSingleTopTo(screen.route)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigateSingleTopTo(myWorkDestinationRoutes.Add_Edit.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Declare")
            }
        }
    ) {


        //set the navHost which will be the current route to present to the user
        NavHost(
            navController = navController,
            startDestination = myWorkDestinationRoutes.Entry.route
        ) {

            //will includes all of the screen in this nav graph

            composable(route = myWorkDestinationRoutes.Entry.route) {
                val vm = hiltViewModel<EntryViewmodel>()
                val entryStatesAndEvents = EntryStatesAndEvents(
                    uiState = vm.uiState,
                    onDayTarg = { vm.onEntryEvent(EntryEvent.onDayTargetChange(it)) },
                    onWeekTarg = { vm.onEntryEvent(EntryEvent.onWeekTargetChange(it)) },
                    onSubmit = {vm.onEntryEvent(EntryEvent.onSubMitTargets(it))},
                    onShowTargetBuilder = { vm.onEntryEvent(EntryEvent.onShowTargetBuilder)})
                //the matched compose function for the screen
                EntryScreen(
                    navigateToEditDeclare = { navController.navigateToEditDeclare(it) },
                    Modifier,
                    entryStatesAndEvents
                )
            }

            composable(
                route = myWorkDestinationRoutes.Add_Edit.routeWithArg,
                arguments = myWorkDestinationRoutes.Add_Edit.arguments
            ) {

                val theArg = it.arguments?.getInt(myWorkDestinationRoutes.Add_Edit.DeclareTypeArg)

                val vm = hiltViewModel<Add_edit_Viewmodel>()
                val addEditsStatesAndEvents = AddEditsStatesAndEvents(
                    date = vm.uiState.date,
                    onDateCheck = { vm.onAddEditEvent(event = Add_edit_events.onDateChange(it)) },
                    day = vm.uiState.day,
                    workTime = vm.uiState.workTime,
                    onWorkTimeCheck = {
                        vm.onAddEditEvent(
                            event = Add_edit_events.onWorkTimeChange(it)
                        )
                    },
                    isProject = vm.uiState.isProject,
                    onIsProjectChange = { vm.onAddEditEvent(Add_edit_events.onIsProjectChange(it)) },
                    isHome = vm.uiState.atHome,
                    onIsHomeChange = { vm.onAddEditEvent(Add_edit_events.onIsHomeChange(it)) },
                    errorMessage = vm.uiState.errorMessage,
                    onSubmit = { vm.onAddEditEvent(event = Add_edit_events.onSubmit) },
                    UiEvent = vm.uiEvent,
                    initializeItem = {vm.initializeLearningItem(it)},
                    onDelete = {vm.onAddEditEvent(Add_edit_events.onDelete)}

                )

                //check for the arguments from the back stack
                Add_Edit_Screen(
                    { navController.navigateSingleTopTo(it) },
                    Modifier,
                    addEditsStatesAndEvents, theId = theArg ?: 0
                )

            }
            composable(
                route = myWorkDestinationRoutes.All.route,
            )
            {
                val vm = hiltViewModel<AllViewmodel>()
                val allStatesAndEvents =
                    AllStatesAndEvents(vm.uiState)


                    All_Screen(
                        navigateToEditDeclare = { navController.navigateToEditDeclare(it) },
                        modifier = Modifier,
                        allStateAndEvents = allStatesAndEvents,
                    )
                }

        }
    }
}



val screens = listOf(
     myWorkDestinationRoutes.All,
     myWorkDestinationRoutes.Add_Edit,
    myWorkDestinationRoutes.Entry
)

private interface myWorkDestination{
    val route:String
    val icon : ImageVector
}

sealed class myWorkDestinationRoutes:myWorkDestination {
    object Entry : myWorkDestinationRoutes() {
        override val route = "entry"
        override val icon = Icons.Filled.Menu
    }

    object All : myWorkDestinationRoutes(){
        override val route = "all"
        val deepLinkRoue = "mywork://all/{id}"
        const val AllTypeArg = "id"
        val arguments = listOf(navArgument(myWorkDestinationRoutes.All.AllTypeArg){
            type = NavType.IntType
            defaultValue = -1
        }
        )
        override val icon = Icons.Filled.DateRange

    }

    object Add_Edit : myWorkDestinationRoutes() {
        override val route: String = "add_edit"
        //the route to send as a frame
        val routeWithArg: String = "add_edit/{theId}"

        //the key to the args values
        const val DeclareTypeArg = "theId"

        //the arguments frame, to be set in the compose builder (in the nav graph) to set which arguments should he get
        val arguments = listOf(
            navArgument(DeclareTypeArg) { type = NavType.IntType }
        )
        override val icon = Icons.Filled.Add
    }

}

