package com.example.mywork120.presentation.uiComponents

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun DaySumCircle(
    day: String,
    dayTarget : Float,
    value : Float,
    modifier:Modifier
) {
    val barColor : Color = Color.Gray
    val valueColor : Color = Color.Green

    val valueAnimationState = remember {
        Animatable(0f)
    }


    LaunchedEffect(key1 = dayTarget) {
        valueAnimationState.animateTo(
            targetValue = (value.toFloat()/dayTarget.toFloat()),
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }

    Box(
        modifier = modifier.padding(start = 3.dp, end = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        ) {
            drawArc(
                color =  barColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
                drawArc(
                    color = valueColor,
                    startAngle = 0f,
                    sweepAngle = 360f * valueAnimationState.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = 8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day,
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light
            )
            UnitDisplay(
                amount = value,
                unit = "hours /",//stringResource(id = R.string.grams),
                amountColor = Color.Black,
                unitColor = Color.Black
            )
            UnitDisplay(
                amount = dayTarget,
                unit = "hours",//stringResource(id = R.string.grams),
                amountColor = Color.Black,
                unitColor = Color.Black
            )
        }
    }



}