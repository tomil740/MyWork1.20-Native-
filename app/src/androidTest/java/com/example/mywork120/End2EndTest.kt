package com.example.mywork120

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mywork120.domain.usecase.AddEditUsecase
import com.example.mywork120.domain.usecase.CalculateWeekSum
import com.example.mywork120.domain.usecase.EntryUseCase
import com.example.mywork120.domain.usecase.GetDaySumByDate
import com.example.mywork120.domain.usecase.GetDeclareById
import com.example.mywork120.domain.usecase.GetStatAndTargets
import com.example.mywork120.domain.usecase.GetSunday
import com.example.mywork120.domain.usecase.GetWeekId
import com.example.mywork120.domain.usecase.InsertWeekSum
import com.example.mywork120.domain.usecase.OnDeleteDeclareById
import com.example.mywork120.domain.usecase.OnSubmitDeclare
import com.example.mywork120.domain.usecase.OnSubmitTargets
import com.example.mywork120.domain.usecase.OnlegalityCheck
import com.example.mywork120.domain.usecase.ShouldUpdateWeekSum
import com.example.mywork120.domain.usecase.UpdateStatisticsObj
import com.example.mywork120.presentation.add_edit_builder.AddEditsStatesAndEvents
import com.example.mywork120.presentation.entry.EntryEvent
import com.example.mywork120.presentation.entry.EntryScreen
import com.example.mywork120.presentation.entry.EntryViewmodel
import com.example.mywork120.presentation.navigation.myWorkDestinationRoutes
import com.example.mywork120.presentation.navigation.screens
import com.example.mywork120.presentation.util.UiText
import com.example.mywork120.repository.FakeRepository
import com.example.mywork120.presentation.add_edit_builder.Add_edit_events
import com.example.myworkbase.presentation.add_edit_builder.Add_Edit_Screen
import com.example.myworkbase.presentation.add_edit_builder.Add_edit_Viewmodel
import com.example.myworkbase.presentation.util.EntryStatesAndEvents
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.mywork120.domain.model.Declare
import java.time.LocalDate
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@HiltAndroidTest
class End2EndTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repository: FakeRepository
    private lateinit var entryUseCase: EntryUseCase
    private lateinit var addEditUsecase: AddEditUsecase
    private lateinit var entryViewmodel: EntryViewmodel
    private lateinit var addEditViewmodel: Add_edit_Viewmodel
    private lateinit var navController: NavHostController


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Before
    fun setup() {

        repository = FakeRepository()
        entryUseCase = EntryUseCase(
            GetSunday(),
            CalculateWeekSum(),
            GetDaySumByDate(repository),
            GetWeekId(),
            ShouldUpdateWeekSum(repository),
            InsertWeekSum(repository),
            UpdateStatisticsObj(repository),
            GetStatAndTargets(repository),
            OnSubmitTargets(repository)
        )
        addEditUsecase = AddEditUsecase(
            OnlegalityCheck(), OnSubmitDeclare(repository), GetDeclareById(repository),
            OnDeleteDeclareById(repository)
        )

        entryViewmodel = EntryViewmodel(entryUseCase)
        addEditViewmodel = Add_edit_Viewmodel(addEditUsecase)

        composeRule.activity.setContent {
            //set the nav controller for this app navigation
            navController = rememberNavController()

            fun NavHostController.navigateSingleTopTo(route: String) {
                if (route == "add_edit") {
                    this.navigate("${myWorkDestinationRoutes.Add_Edit.route}/0") {
                        launchSingleTop = true
                    }
                } else {
                    this.navigate(route) { launchSingleTop = true }
                }
            }

            fun NavHostController.navigateToEditDeclare(declareType: Int) {
                this.navigateSingleTopTo("${myWorkDestinationRoutes.Add_Edit.route}/${declareType}")
            }

            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        screens.forEach { screen ->
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        screen.icon,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                },
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
                        containerColor = MaterialTheme.colorScheme.primary, modifier = Modifier.semantics { contentDescription =
                            composeRule.activity.getString(R.string.floatingAddBut)}
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
                            onSubmit = { vm.onEntryEvent(EntryEvent.onSubMitTargets(it)) },
                            onShowTargetBuilder = { vm.onEntryEvent(EntryEvent.onShowTargetBuilder) })
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

                        val theArg =
                            it.arguments?.getInt(myWorkDestinationRoutes.Add_Edit.DeclareTypeArg)

                        val vm = hiltViewModel<Add_edit_Viewmodel>()
                        val addEditsStatesAndEvents = AddEditsStatesAndEvents(
                            date = vm.uiState.date,
                            onDateCheck = {
                                vm.onAddEditEvent(
                                    event = Add_edit_events.onDateChange(
                                        it
                                    )
                                )
                            },
                            day = vm.uiState.day,
                            workTime = vm.uiState.workTime,
                            onWorkTimeCheck = {
                                vm.onAddEditEvent(
                                    event = Add_edit_events.onWorkTimeChange(it)
                                )
                            },
                            isProject = vm.uiState.isProject,
                            onIsProjectChange = {
                                vm.onAddEditEvent(
                                    Add_edit_events.onIsProjectChange(
                                        it
                                    )
                                )
                            },
                            isHome = vm.uiState.atHome,
                            onIsHomeChange = { vm.onAddEditEvent(Add_edit_events.onIsHomeChange(it)) },
                            errorMessage = vm.uiState.errorMessage,
                            onSubmit = { vm.onAddEditEvent(event = Add_edit_events.onSubmit) },
                            UiEvent = vm.uiEvent,
                            initializeItem = { vm.initializeLearningItem(it) },
                            onDelete = { vm.onAddEditEvent(Add_edit_events.onDelete) }

                        )

                        //check for the arguments from the back stack
                        Add_Edit_Screen(
                            { navController.navigateSingleTopTo(it) },
                            Modifier,
                            addEditsStatesAndEvents, theId = theArg ?: 0
                        )

                    }

                }
            }
        }
    }

    @Test
    fun theEndToEndTest(){


        val declare = Declare(LocalDate.now(),5f, declareId = Random.nextInt())



        composeRule.onNodeWithContentDescription(UiText.StringResource(R.string.floatingAddBut).asString(composeRule.activity)).assertExists()

        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.floatingAddBut)).performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(myWorkDestinationRoutes.Add_Edit.route)
        ).isTrue()

        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.workTimeEditField)).assertExists()
        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.workTimeEditField))
            .performTextInput(declare.workTime.toDouble().toString())

        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.dateEditField))
            .performTextInput(declare.date.toString())

        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.subMitDeclareBut))
            .performClick()


        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.errorField)).assertExists(errorMessageOnFail = "Clear")


        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.dateEditField))
            .performTextInput(declare.date.toString() + "3")


        composeRule.onNodeWithContentDescription(composeRule.activity.getString(R.string.errorField)).assertExists(errorMessageOnFail = "dateIsEligal")


        /*
                assertThat(
                    navController
                        .currentDestination
                        ?.route
                        ?.startsWith(myWorkDestinationRoutes.Entry.route)
                ).isTrue()

         */







    }









}























