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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import dataAnalyzer.domain.models.models.SumObjectInterface
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun MainObjectHeaderItem(
    mainObjectHeaderItemData: SumObjectInterface,
    isBuilderWorkSession:Boolean = false,
    onValueArchivePick:(String)->Unit,
    onValueWorkingPlatform:(String)->Unit={},
    onComparableGeneralStatPick:(String)->Unit={},
    onComparableUserStatPick:(String)->Unit={},
    onValueGeneralStatPick:(String)->Unit={},
    onValueUserStatPick:(String)->Unit={},
    onMainObjectClick:(String)->Unit,
    isBuilder:Boolean = false,
    isPlatform:Boolean=false,
    value1Color : Color = Color.Blue,
    value2Color : Color = Color.Red,
    value3Color : Color = Color.Yellow,
    navToPlatformBuilder:()->Unit={},
    navToPlatformContext:String = "",
    navigator: Navigator?,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier
        , contentAlignment = Alignment.TopEnd) {

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
        ) {
            Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Text(
                    text = mainObjectHeaderItemData.objectName,
                    style = MaterialTheme.typography.displaySmall,
                    modifier =
                    Modifier.clickable { onMainObjectClick(mainObjectHeaderItemData.objectName) }//need to set some navigation function ore somthing  }
                        // .align(alignment = Alignment.CenterHorizontally)
                        .padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                if (!isBuilder && mainObjectHeaderItemData.objectType != SumObjectsType.AllTimeSum) {

                    Spacer(Modifier.width(22.dp))

                    OutlinedButton(colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                        onClick = { onValueArchivePick("") }) {
                        Text(
                            "All Time",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(21.dp))

            Row(
                modifier = Modifier
                    .offset(x = -2.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            )
            {
                TwoValuesProgressBar(
                    barValComponent1 = mainObjectHeaderItemData.baseIncome,
                    barValComponent2 = mainObjectHeaderItemData.extraIncome,
                    subBarVal = mainObjectHeaderItemData.totalTime,
                    sumObjType = mainObjectHeaderItemData.objectType,
                    perDeliveryValue1 = mainObjectHeaderItemData.averageIncomePerDelivery1,
                    perDeliveryValue2 = mainObjectHeaderItemData.averageIncomePerDelivery2,
                    perHourValue1 = mainObjectHeaderItemData.averageIncomePerHour1,
                    perHourValue2 = mainObjectHeaderItemData.averageIncomePerHour2,
                    perSessionValue1 = mainObjectHeaderItemData.averageIncomeSubObj1,
                    perSessionValue2 = mainObjectHeaderItemData.averageIncomeSubObj2,
                    stayExpended = if (mainObjectHeaderItemData.objectType == SumObjectsType.WorkSession && mainObjectHeaderItemData.objectName!="Builder Data"){true}else{false}
                )
            }

        }

    }
}





@Composable
@Preview
fun thePrev() {
    //MainObjectHeaderItem(navToPlatformBuilder = {},navigator = null)

}