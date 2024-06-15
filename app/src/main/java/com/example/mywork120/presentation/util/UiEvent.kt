package com.example.mywork120.presentation.util

//here we will set the events option for our view model to send to the ui
sealed class UiEvent{
    //for nav event to send to the ui
    data class Navigate(val route: String):UiEvent()
    //for show snack bar after some action and add an action to it
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ):UiEvent()

    data class onNewDeclare(val whichWeek:Int):UiEvent()

}
