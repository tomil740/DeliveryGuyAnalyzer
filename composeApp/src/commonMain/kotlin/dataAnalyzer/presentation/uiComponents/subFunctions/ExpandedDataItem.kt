package dataAnalyzer.presentation.uiComponents.subFunctions

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.util.DefaultData
import dataAnalyzer.presentation.util.Dimnations
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.base_prefix
import deliveryguyanalyzer.composeapp.generated.resources.per_delivery
import deliveryguyanalyzer.composeapp.generated.resources.per_hour
import deliveryguyanalyzer.composeapp.generated.resources.per_session
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

/*
ExpandedDataItem :
arguments : get between two - three attributes values to present with matched colors picks , and two basic states
that defined which version of this function we want to implement
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExpandedDataItem(isExpended:Boolean, isDefaultParam:Boolean=true,
                     perHourValue1:Float , perHourValue2:Float ,
                     perDeliveryValue1:Float , perDeliveryValue2:Float ,
                     perSessionValue1:Float = 0f,perSessionValue2:Float = 0f,
                     barColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
                     valueColor: Color = MaterialTheme.colorScheme.secondaryContainer,
                     textColor:Color =MaterialTheme.colorScheme.onPrimary,) {
/*an declaration of of our three attributes comparable data according to the matched value data and the calculating function
* those states target is to prevent recalling the calculation where nothing related to it is changing
 */
    var perHourComparable by remember { mutableStateOf(DefaultData().getComparablePerHour(perHourValue1))}
    var perDeliveryComparable by remember { mutableStateOf(DefaultData().getComparablePerDelivery(perDeliveryValue1))}
    var perSessionComparable by remember { mutableStateOf(DefaultData().getComparableMainValue(SumObjectsType.WorkSession,perHourValue1))}

    //declare an local state for the switchable object state
    var isDefault by remember { mutableStateOf(isDefaultParam) }

//three coroutine to update the states in a case of related value changing
    LaunchedEffect(key1 = perDeliveryValue1 ,key2 = perDeliveryValue2) {
        perDeliveryComparable = DefaultData().getComparablePerDelivery(perDeliveryValue1+perDeliveryValue2)
    }
    LaunchedEffect(key1 = perHourValue1 ,key2 = perHourValue2) {
        perHourComparable = DefaultData().getComparablePerHour(perHourValue1+perHourValue2)
    }
    LaunchedEffect(key1 = perSessionValue1 ,key2 = perSessionValue2) {
        perSessionComparable = DefaultData().getComparableMainValue(SumObjectsType.WorkSession,(perSessionValue1+perSessionValue2))
    }


    //an state that is pass as argument to define rather expose or not our UI
    AnimatedVisibility(isExpended) {
        //the UI layout , box in order to positioned object on top each other in need
        Box(modifier = Modifier.height(200.dp).clickable { isDefault=!isDefault }){
            //the different data switch , will be enable according to the flag (if there is mauched data...)
            AnimatedContent(targetState = if(perSessionValue1!=0f){isDefault }else{false},
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(3000)
                    ) togetherWith fadeOut(animationSpec = tween(3000))
                },) { targetState ->
                when (targetState) {
                    true -> {
                        Row(Modifier.padding(Dimnations.Padding.large)) {
                            CircleProgressItem(
                                valueHeader = stringResource(Res.string.per_session),
                                barSize = perSessionComparable.theVal,
                                barValue1 = perSessionValue1,
                                barValue2 = perSessionValue2,
                                barColor=barColor,
                                value1Color=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                            )
                        }
                    }

                    false -> {

                        Row(Modifier.padding(Dimnations.Padding.large)) {
                            CircleProgressItem(
                                valueHeader = stringResource(Res.string.per_delivery),
                                barSize = perDeliveryComparable.theVal,
                                barValue1 = perDeliveryValue1,
                                barValue2 = perDeliveryValue2,
                                barColor=barColor,
                                value1Color=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                                    .weight(1f)
                            )

                            Spacer(modifier = Modifier.width(Dimnations.Spacer.huge))

                            CircleProgressItem(
                                valueHeader = stringResource(Res.string.per_hour),
                                barSize = perHourComparable.theVal,
                                barValue1 = perHourValue1,
                                barValue2 = perHourValue2,
                                barColor=barColor,
                                value1Color=valueColor,
                                textColor=textColor,
                                modifier = Modifier
                                    .weight(1f)
                            )

                        }
                    }
                }
            }
        }
    }
}
