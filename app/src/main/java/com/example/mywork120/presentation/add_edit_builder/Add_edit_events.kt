package com.example.mywork120.presentation.add_edit_builder

sealed class Add_edit_events {
    object onSubmit: Add_edit_events()
    object onDelete: Add_edit_events()
    data class onDateChange(val date:String): Add_edit_events()
    data class onWorkTimeChange(val workTime:String): Add_edit_events()
    data class onIsHomeChange(val isHome:Boolean): Add_edit_events()
    data class onIsProjectChange(val isProject:Boolean): Add_edit_events()

}