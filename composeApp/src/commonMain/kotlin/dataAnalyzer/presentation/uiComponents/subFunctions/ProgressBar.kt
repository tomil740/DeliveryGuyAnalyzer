package dataAnalyzer.presentation.uiComponents.subFunctions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
/*
ProgressBar :
an helper UI function that need to present an switchable two attributes values animatable bar
 */
@Composable
fun ProgressBar(
    weekTarget : Int,
    value : Float,
    value2 : Float?,
    barColor : Color = MaterialTheme.colorScheme.onPrimaryContainer,
    valueColor : Color = MaterialTheme.colorScheme.primaryContainer,
    value2Color : Color = MaterialTheme.colorScheme.surface,
    onItemClick: ()->Unit,
    modifier: Modifier
) {


    //declaring the need two states for animating each attribute value
    val valueAnimationState = remember {
        Animatable(0f)
    }
    val value2AnimationState = remember {
        Animatable(0f)
    }


//an local UI coroutine for validate and launch our first attribute animatable
        LaunchedEffect(key1=value2,key2 = weekTarget) {
            if(value2!=null) {

            value2AnimationState.animateTo(
                targetValue = (value2 / weekTarget),
                animationSpec = tween(
                    durationMillis = 1000
                )
            )

        }
    }
    //an local UI coroutine for validate and launch our second attribute animatable
    LaunchedEffect(key1 = value,key2 = weekTarget) {

        valueAnimationState.animateTo(
            targetValue = value / weekTarget,
                    animationSpec = tween(
                    durationMillis = 1000
                    )
        )
    }

    //the UI of our bar with the matched callBack to switch between the two attributes presentation
    //*the switch will be from the caller function by switching the argument and launching our coroutine*
    Canvas(
        modifier = modifier.clickable { onItemClick() }
    ) {
       val baseComponentWidth = (valueAnimationState.value)*size.width

        drawRoundRect(
            color = barColor,
            size = size,
            cornerRadius = CornerRadius(100f)
        )
        if(value2!=null){
            drawRoundRect(
                color = value2Color,
                size = Size(
                    width = baseComponentWidth+(value2AnimationState.value)*size.width,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        }
        drawRoundRect(
            color = valueColor,
            size = Size(
                width = baseComponentWidth,
                height = size.height
            ),
            cornerRadius = CornerRadius(100f)
        )

    }





}