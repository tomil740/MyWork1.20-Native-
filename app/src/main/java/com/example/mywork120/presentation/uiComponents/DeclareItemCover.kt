package com.example.mywork120.presentation.uiComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mywork120.domain.model.Declare

//will not be pusbile if there is no item in this week
@Composable
fun DeclareItemCover(modifier: Modifier,declares: List<Declare>,navigateToEditDeclare: (Int)-> Unit, ) {

    LazyColumn(Modifier.fillMaxSize().background(Color.Blue)){

            item {
                Text(text = "day sums Declaries ${declares.get(0).date}", textAlign = TextAlign.Center, color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(declares){
                Log.i("yy","the ${it.declareId}")

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 4.dp).clickable {navigateToEditDeclare(it.declareId)},
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = it.declareId.toString())

                        Text(text = "workTime : ${it.workTime}")

                    }




                }





        }

}