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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.mongodb.kbson.serialization.EJson


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

    /*
    ToDo:
    need to define two Ui host states to host the picked graph values
    in order to preforme an wwitch on user click ...
     */


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
            Row {
                Text(
                    text = mainObjectHeaderItemData.objectName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier =
                    Modifier.clickable { onMainObjectClick(mainObjectHeaderItemData.objectName) }//need to set some navigation function ore somthing  }
                        // .align(alignment = Alignment.CenterHorizontally)
                        .padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

                if(!isBuilder&&mainObjectHeaderItemData.objectType!=SumObjectsType.AllTimeSum){
                    Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        OutlinedButton(onClick = {onValueArchivePick("")}){
                            Text("All Time data", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .offset(x = -2.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            )
            {
                TwoValuesProgressBar(
                    barVal = mainObjectHeaderItemData.totalIncome,
                    comparableVal = 10000f,//should be constaent to each sumobject type.,
                    subBarVal = mainObjectHeaderItemData.totalTime,
                    subComparableVal = 200f,
                    perDeliveryValue = mainObjectHeaderItemData.averageIncomePerDelivery,
                    perDeliveryComparable = 29f,//the average delivers per ...,
                    perHourComparable = 50f,
                    perHourValue = mainObjectHeaderItemData.averageIncomePerHour,
                    //should be optional according to the object type , in order to implemnt the matched data type
                    perSessionValue = 350f ,//
                    perSessionComparable = 400f,
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