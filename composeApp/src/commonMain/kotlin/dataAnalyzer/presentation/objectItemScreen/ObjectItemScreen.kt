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
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.summariseDeclareBuilderScreen.SummariseDeclareBuilderScreenClass
import dataAnalyzer.presentation.uiComponents.ArchiveItem
import dataAnalyzer.presentation.uiComponents.MainObjectHeaderItem
import dataAnalyzer.presentation.util.Dimnations
import dataAnalyzer.presentation.util.UiText
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.end_time
import deliveryguyanalyzer.composeapp.generated.resources.end_time_prefix
import deliveryguyanalyzer.composeapp.generated.resources.start_time
import deliveryguyanalyzer.composeapp.generated.resources.start_time_prefix
import deliveryguyanalyzer.composeapp.generated.resources.total_deliveries_prefix
import deliveryguyanalyzer.composeapp.generated.resources.total_time_prefix
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

/*
ObjectItemScreen :
This is the UI function , includes all of the screen ui implemntation
parameters :
*initializeObj of type sumObjDomain that will be used on navigation to an specific object
*builderMes of type UiText again will be used to pass an snackbar message between screens
*objectItemStatesAndEvents an special object according to the clean architecture that includes all the data related stuff from the vm
 this approch keep the code cleaner and performing better
*modifier as usual to define general Ui frame attributes to the all function...
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun ObjectItemScreen(initializeObj: SumObjDomain? =null, builderMes:UiText? = null,
                     objectItemStatesAndEvents: ObjectItemStatesAndEvents, modifier: Modifier = Modifier) {
    //local host state for our snackbar according to the compose architecture
    val snackBarHostState = remember { SnackbarHostState() }
    //the voygar library navigator object , an lazy object that is matched to work on composables without problems
    val navigator = LocalNavigator.currentOrThrow

    /*
    the UI initalizer part of the builderMes argument includes two options (if there is an argument) :
    * regular message to present from the other screen
    * flag to show different data then our default
    */
    if (builderMes != null) {
        val a = builderMes.asString()
        if (a == "") {
            objectItemStatesAndEvents.onAllTimeData()
        } else {
            LaunchedEffect(snackBarHostState) {
                snackBarHostState.showSnackbar(a, duration = SnackbarDuration.Short)
            }
        }
    }
    /*
     the UI initalizer part of the initializeObj argument, will be stright forward if there is an argument we will implement it
     */
    if (initializeObj != null) {
        objectItemStatesAndEvents.initializeAnObject(initializeObj)
        //if there is no UI argument object data we will initialize the default current month data , while check an "edge case" of the all time data
        //that is not initialize by those options and we want to show
    } else if (objectItemStatesAndEvents.uiState.objectValueSum.objectType != SumObjectsType.AllTimeSum) {
        objectItemStatesAndEvents.onInitializeScreen.invoke()
    }

/*
the scaffold function includes the actual UI itself , according to the matched compose functions architecture
 */
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = {
                    //an UI local behovour to navigate onclick...
                    navigator.replaceAll(SummariseDeclareBuilderScreenClass())
                },
                modifier = Modifier
                    //todo need to test the use of offset in different hardware (can cause some problems)
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
        //todo should be adjust , this part ment to match the colors for more native look on both platforms
        containerColor = if (MaterialTheme.colorScheme.background == Color(
                1.0f,
                0.9843137f,
                0.99607843f,
                1.0f
            ) || MaterialTheme.colorScheme.background == Color(
                0.98039216f,
                0.9764706f,
                0.99215686f,
                1.0f
            )
        ) {
            Color.White
        } else {
            Color.Black
        }

    ) { paddingVal ->

        Column(
            modifier
                /*todo preaty sure there is no need to this part , accoridng to the lazy list that supply the matched behovoru
                //.heightIn(max = 1000.dp)
                .verticalScroll(rememberScrollState())
                 */
                .padding(paddingVal), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //UI coroutine that listen to our UI state channel and will show sanckBar on every emit of the matched channel
            LaunchedEffect(snackBarHostState) {
                objectItemStatesAndEvents.uiState.uiMessage.consumeAsFlow().collectLatest {
                    snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                }
            }

            //position on the top of the screen , our mainObjectHeader ui function
            MainObjectHeaderItem(
                //the arcive comparable pick will be on the spesifc matched function
                mainObjectHeaderItemData = objectItemStatesAndEvents.uiState.objectValueSum,
                onMainObjectClick = { },
                onTopArchiveButton = {
                    //without applying new navigation we will stay on the old initalize function again...
                    navigator.replaceAll(
                        ObjectItemScreenClass(
                            builderMes = UiText.DynamicString(
                                ""
                            )
                        )
                    )
                },
                modifier = modifier
            )

            Spacer(modifier = Modifier.height(Dimnations.Spacer.medium))

            Column(
                modifier,
                /*todo preaty sure there is no need to this part , accoridng to the lazy list that supply the matched behovoru
                //.heightIn(max = 1000.dp)
                .verticalScroll(rememberScrollState())
                    //.heightIn(max = 1000.dp)
                   // .padding(paddingVal),
                 */
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*  the archive list , will be pulled from our sumObj state (hosted at the viewModel),
                    when the if there is no data by definition(on work session object...) we will put null as a flag and here we will
                    check for it , otherways we will run it in items lazy list that will mange everything we should be worry about
                 */
                LazyColumn(
                    modifier.padding(paddingVal),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (objectItemStatesAndEvents.uiState.objectValueSum.subObjects != null) {
                        items(objectItemStatesAndEvents.uiState.objectValueSum.subObjects) { theObj ->

                            Spacer(modifier = Modifier.height(Dimnations.Spacer.large))

                            ArchiveItem(
                                theObj = theObj,
                                onHeaderClick = {
                                    navigator.push(ObjectItemScreenClass(initializeObj = theObj))
                                                },
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )

                        }
                    }
                }
                /*
                this part will be appearing according to the presented object state , if matched we will add below at the cuolumn an
                extra detalis to an speisfic declare
                 */
                AnimatedVisibility(objectItemStatesAndEvents.uiState.objectValueSum.objectType == SumObjectsType.WorkSession) {
                    //need to implemnt some soultion to open up constentaly the expended data
                    Column(
                        modifier = Modifier.padding(start = 42.dp, end = 42.dp).fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                stringResource(Res.string.start_time_prefix),
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                stringResource(Res.string.end_time_prefix),
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.height(Dimnations.Spacer.small))

                        val sTime =
                            objectItemStatesAndEvents.uiState.objectValueSum.startTime.time
                        val eTime =
                            objectItemStatesAndEvents.uiState.objectValueSum.endTime.time

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${sTime.hour} : ${sTime.minute}",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                Icons.Default.ArrowForward,
                                "",
                                modifier = Modifier.size(Dimnations.Spacer.large)
                            )
                            Text(
                                "${eTime.hour} : ${eTime.minute}",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(Modifier.height(Dimnations.Spacer.medium))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                stringResource(Res.string.total_deliveries_prefix),
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(Dimnations.Spacer.medium))
                            Text(
                                "${objectItemStatesAndEvents.uiState.objectValueSum.delivers}",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                    }


                }

            }
        }
    }
}


