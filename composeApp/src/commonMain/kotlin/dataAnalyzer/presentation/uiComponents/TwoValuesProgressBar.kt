package dataAnalyzer.presentation.uiComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.util.DefaultData
import dataAnalyzer.presentation.uiComponents.subFunctions.ExpandedDataItem
import dataAnalyzer.presentation.uiComponents.subFunctions.ProgressBar


/*
two values progress bar :
This function will get as parameters all of the needed values to be present on our graph and some helper attributes to define
which version of it to show , between the main header to the sub
* According to the picked obj type we will define from our constant objects the color font adn etc of it...
technical to know :
* the function manges two states of itSelf in order to switch between attributes (main/sub) on demand
 */

@Composable
fun TwoValuesProgressBar(barValComponent1:Float, barValComponent2:Float, sumObjType:SumObjectsType,
                         subBarVal:Float, perHourValue1:Float = 0f, perHourValue2:Float = 0f,
                         perDeliveryValue1:Float = 0f, perDeliveryValue2:Float = 0f,
                         perSessionValue1:Float = 0f, perSessionValue2:Float = 0f,
                         stayExpended:Boolean =false, isSecondary:Boolean=false,
                         modifier: Modifier = Modifier
) {
    /*
    in order to get the matched comparable data to our sum object , and only apply this section once ...
    we will implement an local UI lunched defect that will update our comparable according to the argument
     */
//the comparable data according to our values and obj type...
    var comparableValObj by remember { mutableStateOf(DefaultData().getComparableMainValue(sumObjType,(barValComponent1+barValComponent2)))}
    var comparableSubObj by remember { mutableStateOf(DefaultData().getComparableSubValue(sumObjType,subBarVal))}

    //local states to mange our interactive components
    var isDefaultBar by remember { mutableStateOf(true) }
    var isExpandet by remember { mutableStateOf(false) }

    //the color set according to the function argument ...
    val colors = if(isSecondary){
        DefaultData().getSecondaryColors()}else{
        DefaultData().getMainColors()}

    //Todo need to be define on matched resource file
    val constList = listOf("Total income :","working time :")

    //the data local state holders , in order of switching between data on demand
    var barSize by remember { mutableStateOf(comparableValObj.theVal) }
    var barValue by remember { mutableStateOf(barValComponent1+barValComponent2) }
    var subValue by remember { mutableStateOf(subBarVal) }
    var subSize by remember { mutableStateOf(comparableSubObj.theVal) }

    /*
    listen to any base data change to make sure our local states in sync
    will update the local arguments on every change of the data itSelf
     */
    LaunchedEffect(key1 = sumObjType ,key2 = barValComponent1 ,key3= barValComponent2){
        comparableValObj = DefaultData().getComparableMainValue(sumObjType,(barValComponent1+barValComponent2))
        comparableSubObj = DefaultData().getComparableSubValue(sumObjType,(subBarVal))

        barSize = comparableValObj.theVal
        barValue= barValComponent1+barValComponent2
        subValue = subBarVal
        subSize= comparableSubObj.theVal
        isDefaultBar = true
    }

    /*
    apply the needed changes on demand in order of switching between our two attributes values
    *switched from sub attribute to main and vi versa *
     */
    LaunchedEffect(key1 = isDefaultBar) {
        /*
        we will mange two states to switch between our main data to the sub data attributes values
         */
        if (isDefaultBar){
            barSize = comparableValObj.theVal
            barValue= barValComponent1+barValComponent2

            subValue = subBarVal
            subSize= comparableSubObj.theVal
        }else{
            barSize = comparableSubObj.theVal
            barValue= subBarVal

            subValue = barValComponent1+barValComponent2
            subSize= comparableValObj.theVal
        }
    }
    //local variables in order of tracking our obj size to apply extracted extra object on demand
    val density = LocalDensity.current
    var itemSize by remember { mutableStateOf(0.dp) }

    Box(modifier = modifier
        .fillMaxWidth()) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .offset(y = -16.dp)
            .onGloballyPositioned {
                itemSize = with(density) {
                    it.size.height.toDp()
                }
            }, horizontalAlignment = Alignment.Start) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //as for now I pass this behaviour to the unit display , at future probably will nav to the specific value analytics
                UnitDisplay(
                    amount = (((barValue*100f).toInt()).toFloat()/100f),
                    unitIcon = if (isDefaultBar){Icons.Default.AttachMoney}else{Icons.Default.Timer},
                    amountColor = colors.textColor,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    iconColor =  if(isDefaultBar){colors.mainIconColor}else{colors.subIconColor},
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = true,
                    unitText = if(isDefaultBar){constList[0]}else{constList[1]},
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

            }

            Spacer(modifier = Modifier.height(2.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 42.dp, end = 42.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnitDisplay(
                    amount =  (((subValue*100f).toInt()).toFloat()/100f),
                    unitIcon = if (!isDefaultBar){Icons.Default.AttachMoney}else{Icons.Default.Timer},
                    amountColor = colors.textColor,
                    amountTextStyle = MaterialTheme.typography.titleSmall,
                    iconColor =  if(!isDefaultBar){colors.mainIconColor}else{colors.subIconColor},
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = false,
                    unitText = if(isDefaultBar){constList[1]}else{constList[0]},
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )
/*
the full comparable data header
                UnitDisplay(
                    amount =  (((subSize*100f).toInt()).toFloat()/100f),
                    unitIcon = Icons.Filled.Build,
                    amountColor = MaterialTheme.colorScheme.onPrimary,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onPrimary,
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

 */
            }
            Spacer(modifier = Modifier.height(6.dp))

            /*
            here we will mange the two components bar switched states
            * we will recompose according to the isDefaultBar state and the last local state update as well
              that in order to make sure we not get into race condition and lose data
             */
            AnimatedVisibility(isDefaultBar && barSize==comparableValObj.theVal){
                ProgressBar(
                        weekTarget = barSize, value = barValComponent1, value2 = barValComponent2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .padding(start = 4.dp, end = 4.dp),
                    valueColor = colors.valueColor,
                    barColor = colors.barColor,
                    value2Color = colors.value2Color,
                        onItemClick = {
                            isDefaultBar = !isDefaultBar
                        }
                    )
                }
            AnimatedVisibility(!isDefaultBar && barSize==comparableSubObj.theVal){
                ProgressBar(
                    weekTarget = barSize,
                    value = barValue,
                    value2 = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(start = 4.dp, end = 4.dp),
                    valueColor = colors.valueColor,
                    barColor = colors.barColor,
                    value2Color = colors.value2Color,
                    onItemClick = {
                        isDefaultBar = !isDefaultBar
                    }
                )
           }

            Row (modifier=Modifier.fillMaxWidth(), horizontalArrangement =if(isDefaultBar){Arrangement.SpaceBetween}else{Arrangement.End}) {
                AnimatedVisibility(isDefaultBar) {
                    Row {
                        Spacer(Modifier.width(3.dp))

                        Text(
                            "* Base ${barValComponent1.toInt()}$",
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.valueColor
                        )

                        Spacer(Modifier.width(12.dp))

                        Text(
                            "* Extra ${barValComponent2.toInt()}$",
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.value2Color
                        )
                    }
                }

                UnitDisplay(
                    amount = (((barSize * 100f).toInt()).toFloat() / 100f),
                    unitIcon = if (isDefaultBar){Icons.Default.AttachMoney}else{Icons.Default.Timer},
                    amountColor = if(comparableValObj.isException){MaterialTheme.colorScheme.error}else{colors.textColor},
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    iconColor = colors.textColor,
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }
                )
            }

            ExpandedDataItem(if (stayExpended){true}else{isExpandet},isDefaultParam =false,
                perDeliveryValue1 = perDeliveryValue1,perDeliveryValue2=perDeliveryValue2,
                perHourValue1 = perHourValue1, perHourValue2 = perHourValue2,
                perSessionValue1 = perSessionValue1, perSessionValue2 = perSessionValue2,
                barColor = colors.barColor, valueColor = colors.valueColor, textColor = colors.textColor
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = itemSize.value.dp + 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            IconButton(onClick = { isExpandet = !isExpandet }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}