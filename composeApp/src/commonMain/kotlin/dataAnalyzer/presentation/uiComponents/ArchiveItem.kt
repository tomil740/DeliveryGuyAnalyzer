package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dataAnalyzer.domain.models.models.SumObjectInterface

@Composable
fun ArchiveItem(theObj:SumObjectInterface,
    /*
    objectName:String, barSizeParam:Float, barValComponent1:Float,barValComponent2:Float,
                 subSizeParam:Float, subValueParam:Float, perHourVal : Float, perHourComparable:Float
                , perDeliveryVal : Float, perDeliveryComparable:Float, perSessionVal : Float, perSessionComparable:Float,

     */
                onHeaderClick : () ->Unit
                , modifier: Modifier=Modifier) {
/*
    var isDefaultBar by remember { mutableStateOf(false) }
    var isExpandet by remember { mutableStateOf(false) }
    var barSize by remember { mutableStateOf(barSizeParam) }
    var barValue by remember { mutableStateOf(barValComponent1+barValComponent2) }
    var subSize by remember { mutableStateOf(subSizeParam) }
    var subValue by remember { mutableStateOf(subValueParam) }

    LaunchedEffect(objectName,onHeaderClick,barValComponent2,barValComponent1) {
         isDefaultBar =true
         isExpandet =false
         barSize =barSizeParam
         barValue =barValComponent1+barValComponent2
         subSize=subSizeParam
         subValue=subValueParam

    }

    var itemSize by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current


    LaunchedEffect(key1 = isDefaultBar) {
        val a = barSize
        val b = barValue
        barSize = subSize
        barValue = subValue
        subValue = b
        subSize = a
    }

 */
    var itemSize by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Box(modifier = modifier
        .fillMaxWidth(), contentAlignment = Alignment.Center) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .onGloballyPositioned {
                    itemSize = with(density) {
                        it.size.height.toDp()
                    }
                },
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                Modifier
                    .clickable { onHeaderClick() }
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = theObj.objectName + " :",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(Modifier.height(18.dp))
            TwoValuesProgressBar(
                barValComponent1 = theObj.baseIncome,
                barValComponent2 = theObj.extraIncome,
                sumObjType = theObj.objectType,
                subBarVal = theObj.totalTime,
                perDeliveryValue1 = theObj.averageIncomePerDelivery1,
                perDeliveryValue2 = theObj.averageIncomePerDelivery2,
                perHourValue1 = theObj.averageIncomePerHour1,
                perHourValue2 = theObj.averageIncomePerHour2,
                perSessionValue1 = theObj.averageIncomeSubObj1,
                perSessionValue2 = theObj.averageIncomeSubObj2,
                //should be optional according to the object type , in order to implemnt the matched data type
                isSecondary = true
            )
            /*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //as for now I pass this behaviour to the unit display , at future probably will nav to the specific value analytics
                UnitDisplay(
                    amount =  (((barValue*100f).toInt()).toFloat()/100f),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = (((barSize*100f).toInt()).toFloat()/100f),
                    unitIcon = Icons.Default.Notifications,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleLarge,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    isMainObj = true,
                    onItemClick = { isDefaultBar = !isDefaultBar }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 42.dp, end = 42.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UnitDisplay(
                    amount =  (((subValue*100f).toInt()).toFloat()/100f),
                    unitIcon = Icons.Default.AccountBox,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.align(Alignment.Bottom),
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )

                UnitDisplay(
                    amount = (((subSize*100f).toInt()).toFloat()/100f),
                    unitIcon = Icons.Filled.Build,
                    amountColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    amountTextStyle = MaterialTheme.typography.titleMedium,
                    unitColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    isMainObj = false,
                    onItemClick = { isDefaultBar = !isDefaultBar }

                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            AnimatedVisibility(barValue == barValComponent1+barValComponent2){
                ProgressBar(
                    weekTarget = barSize, value = barValComponent1, value2 = barValComponent2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(start = 4.dp, end = 4.dp),
                    onItemClick = {
                        isDefaultBar = !isDefaultBar
                    },
                    barColor = MaterialTheme.colorScheme.onSecondary,
                    value2Color = MaterialTheme.colorScheme.primary,
                    valueColor = MaterialTheme.colorScheme.secondary
                )
            }
            AnimatedVisibility(barValue != barValComponent1+barValComponent2) {
                ProgressBar(
                    weekTarget = barSize,
                    value = barValue,
                    value2 = null,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(30.dp)
                        .align(Alignment.CenterHorizontally),
                    onItemClick = {
                        isDefaultBar = !isDefaultBar
                    },
                    barColor = MaterialTheme.colorScheme.onSecondary,
                    valueColor = MaterialTheme.colorScheme.secondary
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            ExpandedDataItem(isExpandet,barColor = MaterialTheme.colorScheme.onSecondary,
                valueColor = MaterialTheme.colorScheme.secondary,textColor=MaterialTheme.colorScheme.onSecondaryContainer
            , perHourValue = perHourVal,perHourComparable=perHourComparable, perDeliveryValue =perDeliveryVal ,
                perDeliveryComparable =  perDeliveryComparable, perSessionValue = perSessionVal, perSessionComparable = perSessionComparable
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(height = itemSize.value.dp + 38.dp),
            contentAlignment = Alignment.BottomCenter
        ) {

            IconButton(onClick = { isExpandet = !isExpandet }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

             */
        }
    }
}