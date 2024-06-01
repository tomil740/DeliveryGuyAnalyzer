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
import dataAnalyzer.domain.models.models.SumObjDomain

@Composable
fun ArchiveItem(theObj:SumObjDomain,
                onHeaderClick : () ->Unit
                , modifier: Modifier=Modifier) {

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

        }
    }
}