package com.example.mywork120.presentation.uiComponents

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mywork120.R
import com.example.mywork120.domain.model.TargetsAndStat
import com.example.mywork120.domain.model.WeekSum
import java.math.RoundingMode

@Composable
fun WeekSumBarCover(
    weekSum : WeekSum,
    targetsAndStat: TargetsAndStat,
    onDaySumClick : (Int)->Unit ={},
    modifier: Modifier) {

    var weekGoal by remember { mutableStateOf(targetsAndStat.weekTarget )}
    var weekText by remember { mutableStateOf(R.string.week_sum_target_text) }
    var dayGoal by remember { mutableStateOf(targetsAndStat.dayTarget) }

    LaunchedEffect(key1 = targetsAndStat){
        if(weekText == R.string.week_sum_target_text) {
            weekGoal = targetsAndStat.weekTarget
            dayGoal = targetsAndStat.dayTarget
        }
        else{
            weekText = R.string.week_sum_target_text
            weekGoal = targetsAndStat.avgWeek
            dayGoal = targetsAndStat.avgDay
        }

    }




    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
    ) {

        var isExpanded by remember { mutableStateOf(false) }

            Text(text = "week : ${weekSum.startDate} -> ${weekSum.endDate}",
                modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(start = 40.dp, end = 40.dp)
                    .clickable { isExpanded = !isExpanded }, color = Color.White)


        if (!isExpanded )
            Spacer(modifier = modifier.height(18.dp))

        Spacer(modifier = modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UnitDisplay(
                amount = weekSum.weekWork.toBigDecimal().setScale(2, RoundingMode.UP).toFloat(),
                unit = "hours",
                amountColor = MaterialTheme.colorScheme.onPrimary,
                amountTextSize = 40.sp,
                unitColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Column(modifier.clickable {
                if(weekText == R.string.week_sum_target_text) {
                    weekText = R.string.week_sum_average_text
                    weekGoal = targetsAndStat.avgWeek
                    dayGoal = targetsAndStat.avgDay
                }
                else{
                    weekText = R.string.week_sum_target_text
                    weekGoal = targetsAndStat.weekTarget
                    dayGoal = targetsAndStat.dayTarget
                }
            }) {

                Text(
                    text = stringResource(weekText),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                UnitDisplay(
                    amount = weekGoal,
                    unit =  stringResource(R.string.workTimeUnit),
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))


        WeekSumBar(
                weekTarget = weekGoal, weekSum.weekWork, Modifier
                .fillMaxWidth()
                .height(30.dp)
            )

        Spacer(modifier = Modifier.height(4.dp))
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .verticalScroll(rememberScrollState())) {



                Row(modifier.fillMaxWidth()) {

                    DaySumCircle(day = weekSum.daySums.get(0).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(0).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(0) })

                    DaySumCircle(day = weekSum.daySums.get(1).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(1).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(1) })

                    DaySumCircle(day = weekSum.daySums.get(2).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(2).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(2) })


                }

                Spacer(modifier = modifier.size(8.dp))


                Row(modifier.fillMaxWidth()) {

                    DaySumCircle(day = weekSum.daySums.get(3).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(3).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(3) })

                    DaySumCircle(day = weekSum.daySums.get(4).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(4).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(5) })

                    DaySumCircle(day = weekSum.daySums.get(5).date.dayOfWeek.toString(), dayTarget = dayGoal, value = weekSum.daySums.get(5).totalWork, modifier = modifier
                        .weight(1f)
                        .clickable { onDaySumClick(5) })


                }
                Spacer(modifier = modifier.size(8.dp))

                Row  {

                    DaySumCircle(day = weekSum.daySums.get(6).date.dayOfWeek.toString(),
                        dayTarget = dayGoal,
                        value = weekSum.daySums.get(6).totalWork,
                        modifier = modifier
                            .weight(1f)
                            .padding(end = 225.dp, top = 4.dp)
                            .clickable { onDaySumClick(6) })
                }

            }

        }



    }


}