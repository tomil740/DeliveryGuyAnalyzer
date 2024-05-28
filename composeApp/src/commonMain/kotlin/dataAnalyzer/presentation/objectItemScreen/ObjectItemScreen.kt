package dataAnalyzer.presentation.objectItemScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dataAnalyzer.domain.models.models.SumObjectInterface
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.summariseDeclareBuilderScreen.SummariseDeclareBuilderScreenClass
import dataAnalyzer.presentation.uiComponents.ArchiveItem
import dataAnalyzer.presentation.uiComponents.MainObjectHeaderItem
import dataAnalyzer.presentation.util.UiText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow

@OptIn(InternalVoyagerApi::class, ExperimentalAdaptiveApi::class, ExperimentalCupertinoApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ObjectItemScreen(initializeObj: SumObjectInterface? =null,builderMes:UiText? = null,
                     objectItemStatesAndEvents: ObjectItemStatesAndEvents, modifier: Modifier = Modifier) {

    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow

    /*
   UI send snackBar message from the builder screen (at the ui level)
   * in addition we we will place an flag to pick between current month to all time according to this argument as well
    */
    if(builderMes!=null) {
        val a = builderMes.asString()
        if(a==""){
            objectItemStatesAndEvents.onAllTimeData()
        }else {
            LaunchedEffect(snackBarHostState) {
                snackBarHostState.showSnackbar(a, duration = SnackbarDuration.Short)
            }
        }
    }
    //check if this is an archive obj
    if (initializeObj != null ){
        objectItemStatesAndEvents.initializeAnObject(initializeObj)
    //if this isnt any of the others options we will initalize the current month data...
    }else if(objectItemStatesAndEvents.uiState.objectValueSum.objectType!=SumObjectsType.AllTimeSum) {
        objectItemStatesAndEvents.onInitializeScreen.invoke()
    }

    //to use the floating expandet item arrow mark , we must implement it in box layout


    //Box(modifier.fillMaxSize()) {
        Scaffold   (
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = {
                        navigator.replaceAll(SummariseDeclareBuilderScreenClass())
                    },
                    modifier = Modifier
                        .offset(y = -20.dp)
                        .size(58.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Filled.Add, "Small floating action button.")
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            containerColor = if (MaterialTheme.colorScheme.background == Color(1.0f ,0.9843137f,0.99607843f,1.0f) || MaterialTheme.colorScheme.background ==Color(0.98039216f, 0.9764706f, 0.99215686f, 1.0f)){
                Color.White}else{ Color.Black}

        ) { paddingVal ->

            Column(
                modifier
                    .heightIn(max = 1000.dp)
                    .verticalScroll(rememberScrollState())
                      .padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LaunchedEffect(snackBarHostState) {
                    objectItemStatesAndEvents.uiState.uiMessage.consumeAsFlow().collectLatest{
                        snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                    }
                }
                MainObjectHeaderItem(
                    //the arcive comparable pick will be on the spesifc matched function
                    mainObjectHeaderItemData = objectItemStatesAndEvents.uiState.objectValueSum,
                    onMainObjectClick = { },
                    onTopArchiveButton = {
                        //without applying new navigation we will stay on the old initalize function again...
                        navigator.replaceAll(ObjectItemScreenClass(builderMes=UiText.DynamicString("")))
                    },
                    modifier = modifier
                )

                Spacer(modifier = Modifier.height(18.dp))



                Column(
                    modifier
                        .heightIn(max = 1000.dp)
                        .padding(paddingVal),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LazyColumn(
                        modifier.padding(paddingVal),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (objectItemStatesAndEvents.uiState.objectValueSum.subObjects != null) {
                            items(objectItemStatesAndEvents.uiState.objectValueSum.subObjects!!) { theObj ->
                                val comparable =
                                    objectItemStatesAndEvents.uiState.objectValueSum.subObjects?.get(
                                        0
                                    ) ?: objectItemStatesAndEvents.uiState.objectValueSum

                                    Spacer(modifier = Modifier.height(24.dp))

                                    ArchiveItem(
                                        /*
                                        objectName = theObj.objectName,
                                        barSizeParam = theObj.totalIncome * 1.3f,
                                        barValComponent1 = theObj.baseIncome,
                                        barValComponent2 = theObj.extraIncome,
                                        subSizeParam = theObj.totalTime * 1.3f,
                                        subValueParam = theObj.totalTime,
                                        perHourVal = theObj.averageIncomePerHour,
                                        perHourComparable = comparable.averageIncomePerHour,
                                        perDeliveryVal = theObj.averageIncomePerDelivery,
                                        perDeliveryComparable = comparable.averageIncomePerDelivery,
                                        perSessionVal = theObj.averageIncomeSubObj,
                                        perSessionComparable = comparable.averageIncomeSubObj,

                                         */
                                        theObj=theObj,
                                        onHeaderClick = {
                                            navigator.push(
                                                ObjectItemScreenClass(
                                                    initializeObj = theObj
                                                )
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth(0.9f)
                                    )

                            }
                        }
                    }

                    AnimatedVisibility(objectItemStatesAndEvents.uiState.objectValueSum.objectType==SumObjectsType.WorkSession){
                        //need to implemnt some soultion to open up constentaly the expended data
                        Column(modifier = Modifier.padding(start = 42.dp, end = 42.dp).fillMaxWidth()) {
                            Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("From :", style = MaterialTheme.typography.displaySmall,color = MaterialTheme.colorScheme.primary)
                                Text("Until :",style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                            }
                            Spacer(Modifier.height(8.dp))

                            val sTime  = objectItemStatesAndEvents.uiState.objectValueSum.startTime.time
                            val eTime  =  objectItemStatesAndEvents.uiState.objectValueSum.endTime.time

                            Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${sTime.hour} : ${sTime.minute}", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                                Icon(Icons.Default.ArrowForward,"",modifier=Modifier.size(42.dp))
                                Text("${eTime.hour} : ${eTime.minute}",style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                            }

                            Spacer(Modifier.height(18.dp))


                            Row(modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                                Text("total delivers :", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                                Spacer(Modifier.width(16.dp))
                                Text("${objectItemStatesAndEvents.uiState.objectValueSum.delivers}", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
                            }

                        }


                    }

                }
            }
        }
  //  }
}


