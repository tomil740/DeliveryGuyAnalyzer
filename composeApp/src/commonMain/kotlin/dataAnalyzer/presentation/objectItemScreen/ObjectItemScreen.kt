package dataAnalyzer.presentation.objectItemScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dataAnalyzer.domain.models.models.SumObjectInterface
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.summariseDeclareBuilderScreen.SummariseDeclareBuilderScreenClass
import dataAnalyzer.presentation.uiComponents.ArchiveItem
import dataAnalyzer.presentation.uiComponents.MainObjectHeaderItem
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinx.coroutines.flow.consumeAsFlow

@OptIn(InternalVoyagerApi::class, ExperimentalAdaptiveApi::class, ExperimentalCupertinoApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ObjectItemScreen(initializeObj: SumObjectInterface? =null,
                     objectItemStatesAndEvents: ObjectItemStatesAndEvents, modifier: Modifier = Modifier) {

    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.currentOrThrow

    var shouldInit by remember { mutableStateOf(true) }

    if (initializeObj != null && shouldInit) {
        objectItemStatesAndEvents.initializeAnObject(initializeObj)
    }else{
        objectItemStatesAndEvents.onValueArchiveTopMenu.invoke("")
    }
    if (objectItemStatesAndEvents.uiState.objectValueSum.objectType == SumObjectsType.AllTimeSum)
        shouldInit = true

    //to use the floating expandet item arrow mark , we must implement it in box layout


    //Box(modifier.fillMaxSize()) {
        Scaffold   (
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = {
                              navigator.push(SummariseDeclareBuilderScreenClass())
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
        ) { paddingVal ->

            Column(
                modifier
                    .heightIn(max = 1000.dp)
                    .verticalScroll(rememberScrollState())
                      .padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LaunchedEffect(objectItemStatesAndEvents.uiState.uiMessage) {
                    objectItemStatesAndEvents.uiState.uiMessage.consumeAsFlow().collect {
                        snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Long)
                    }
                }
                MainObjectHeaderItem(
                    //the arcive comparable pick will be on the spesifc matched function
                    mainObjectHeaderItemData = objectItemStatesAndEvents.uiState.objectValueSum,
                    navigator = navigator,
                    navToPlatformContext = "",
                    onMainObjectClick = { },
                    onValueArchivePick = {
                        //without applying new navigation we will stay on the old initalize function again...
                        navigator.push(ObjectItemScreenClass())
                        objectItemStatesAndEvents.onValueArchiveTopMenu(it)
                    },
                    onComparableGeneralStatPick = {},
                    onComparableUserStatPick = {},
                    onValueUserStatPick = {},
                    onValueGeneralStatPick = {},
                    navToPlatformBuilder = {},
                    isBuilder = false,
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
                                        objectName = theObj.objectName,
                                        barSizeParam = theObj.totalIncome * 1.3f,
                                        barValueParam = theObj.totalIncome,
                                        subSizeParam = theObj.totalTime * 1.3f,
                                        subValueParam = theObj.totalTime,
                                        perHourVal = theObj.averageIncomePerHour,
                                        perHourComparable = comparable.averageIncomePerHour,
                                        perDeliveryVal = theObj.averageIncomePerDelivery,
                                        perDeliveryComparable = comparable.averageIncomePerDelivery,
                                        perSessionVal = theObj.averageIncomeSubObj,
                                        perSessionComparable = comparable.averageIncomeSubObj,
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
                }
            }
        }
  //  }
}


