package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.util.Dimnations
import dataAnalyzer.presentation.util.UiText
import dataAnalyzer.presentation.util.toPresent
import deliveryguyanalyzer.composeapp.generated.resources.All_time_but
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.builder_obj_flag
import deliveryguyanalyzer.composeapp.generated.resources.month_data_but
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/*
MainObjectHeaderItem :
an UI function that will get as parameter mainly our sumObjDomain object an apply according to its
data all of the sub UI animation and etc to represent its data
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainObjectHeaderItem(
    mainObjectHeaderItemData: SumObjDomain,
    onMainObjectClick:(String)->Unit,
    onTopArchiveButton:(String)->Unit,
    modifier: Modifier = Modifier,
    ) {

    /*
    declaring an local helper UI states , in order to implement some attributes switch in case
    the matched flag will be find in the string object name
     */
    var objName by remember { mutableStateOf(mainObjectHeaderItemData.objectName)}
    var isBuilder by remember { mutableStateOf(false)}

    //local launchedEffect for our builder case flag ...
    LaunchedEffect(mainObjectHeaderItemData.objectName) {
        val  a= mainObjectHeaderItemData.objectName
        if (a.length > 2 && a.substring(0, 2)
            == UiText.StringResource(Res.string.builder_obj_flag).asString2()
        ) {
            isBuilder = true
            objName = a.substring(2)
        }
        else{
            objName = a
        }
    }

    Box(
        modifier = modifier, contentAlignment = Alignment.TopEnd
    ) {

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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    //the extra section according to the builder state
                    if (isBuilder) {
                        Text(
                            text = mainObjectHeaderItemData.startTime.dayOfWeek.toPresent(),
                            style = MaterialTheme.typography.displaySmall,
                            modifier =
                            Modifier//.alignByBaseline()
                                .clickable { onMainObjectClick(mainObjectHeaderItemData.objectName) }
                                .padding(start = 12.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(Modifier.width(3.dp))
                    }
                    Text(
                        text = objName,
                        style = MaterialTheme.typography.displaySmall,
                        modifier =
                        Modifier//.alignByBaseline()
                            .clickable { onMainObjectClick(mainObjectHeaderItemData.objectName) }//need to set some navigation function ore somthing  }
                            .padding(start = Dimnations.Padding.large),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                /*
                This button will be used as all time archive button on our object item screen , and
                on our builder as archive navigation button ...
                 */
                if (mainObjectHeaderItemData.objectType != SumObjectsType.AllTimeSum) {

                    Spacer(Modifier.width(Dimnations.Spacer.small))

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        border = BorderStroke(3.dp,MaterialTheme.colorScheme.primary),
                        onClick = { onTopArchiveButton("") },
                        modifier = Modifier.requiredWidth(124.dp)

                    ) {
                        Text(
                            if (isBuilder) {
                                stringResource(Res.string.month_data_but)
                            } else {
                                stringResource(Res.string.All_time_but)
                            },
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimnations.Spacer.large))

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
                    stayExpended = if (mainObjectHeaderItemData.objectType == SumObjectsType.WorkSession) {
                        (!isBuilder) } else { false }
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
