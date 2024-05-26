package dataAnalyzer.presentation.uiComponents.subFunctions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dataAnalyzer.presentation.uiComponents.CircleValues


@Composable
fun CircleProgressItem(
    valueHeader: String,
    barSize : Int,
    barValue1 : Float,
    barValue2 : Float,
    barColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    value1Color: Color = MaterialTheme.colorScheme.secondaryContainer,
    value2Color: Color = MaterialTheme.colorScheme.error,
    textColor:Color =MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier
) {


    val baseValueAnimationState = remember {
        Animatable(0f)
    }
    val extraValueAnimationState = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = barSize,key2 = barValue1) {
        val a = if(barValue1.isInfinite()||barValue1.isNaN()){10f}else{barValue1}
        //In order of catching zero and all sort of unUseable values ...
        baseValueAnimationState.animateTo(
            targetValue =
            (a / barSize)
            ,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }
    LaunchedEffect(key1 = barSize,key2 = barValue2) {
        val a = if(barValue2.isInfinite()||barValue2.isNaN()){10f}else{barValue2}
        //In order of catching zero and all sort of unUseable values ...
        extraValueAnimationState.animateTo(
            targetValue =
            (a / barSize)
            ,
            animationSpec = tween(
                durationMillis = 1350
            )
        )
    }

    var itemSizeState by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.weight(1f)) {

            Box(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .onGloballyPositioned {
                            itemSizeState = with(density) {
                                it.size.height.toDp()
                            }
                        },
                ) {

                    val baseComponent = 270f *baseValueAnimationState.value

                    drawArc(
                        color = barColor,
                        startAngle = -45f,
                        sweepAngle = 270f,
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = 22.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawArc(
                        color = value2Color,
                        startAngle = -45f,
                        sweepAngle = baseComponent+(270f* extraValueAnimationState.value),
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = 22.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )

                    drawArc(
                        color = value1Color,
                        startAngle = -45f,
                        sweepAngle = baseComponent,
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = 22.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemSizeState),
                    contentAlignment = Alignment.Center
                ) {

                    CircleValues(
                        theHeight = itemSizeState,
                        barSize,
                        barValue1+barValue2,
                        textStyle = MaterialTheme.typography.displayMedium,
                        textColor=textColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier) {

            Text(
                text = valueHeader,
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )

        }
        
    }


}