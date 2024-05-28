package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import dataAnalyzer.presentation.core.getOptionsMenu
import dataAnalyzer.presentation.objectItemScreen.ObjectItemScreenClass
import dataAnalyzer.presentation.uiComponents.MainObjectHeaderItem
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.base_hour
import deliveryguyanalyzer.composeapp.generated.resources.delete
import deliveryguyanalyzer.composeapp.generated.resources.deliveries
import deliveryguyanalyzer.composeapp.generated.resources.end_time
import deliveryguyanalyzer.composeapp.generated.resources.extras
import deliveryguyanalyzer.composeapp.generated.resources.picked_date
import deliveryguyanalyzer.composeapp.generated.resources.start_time
import deliveryguyanalyzer.composeapp.generated.resources.submit
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import kotlinx.coroutines.flow.consumeAsFlow
import network.chaintech.ui.datepicker.WheelPicker
import dev.darkokoa.datetimewheelpicker.WheelTimePicker
import dev.darkokoa.datetimewheelpicker.core.WheelPickerDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SummariseBuilderScreen(summariseBuilderStatesAndEvents: SummariseBuilderStatesAndEvents, modifier: Modifier) {
    val snackBarHostState = remember { SnackbarHostState() }
    val navigator = LocalNavigator.current
    var showActionOptions by remember { mutableStateOf(false) }
    //var showDateDialog by remember { mutableStateOf(false) }
    var showBasePickerDialog by remember { mutableStateOf(false) }
    //var showEndTimeDialog by remember { mutableStateOf(false) }

    val theState = summariseBuilderStatesAndEvents.uiState.typeBuilderState
    var optionMenu by remember { mutableStateOf(getOptionsMenu()) }

    var startTimeState by remember { mutableStateOf("${theState.startTime.hour}:${theState.startTime.minute}") }
    var endTimeState by remember { mutableStateOf("${theState.endTime.hour}:${theState.endTime.minute}") }


    LaunchedEffect(theState) {
        startTimeState = "${theState.startTime.hour}:${theState.startTime.minute}"
        endTimeState = "${theState.endTime.hour}:${theState.endTime.minute}"
    }



    Scaffold(
        modifier =modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            AnimatedVisibility(showActionOptions) {
                Column(modifier = Modifier.offset(x = -35.dp, y = -20.dp)) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            summariseBuilderStatesAndEvents.onSubmitDeclare()
                            if (summariseBuilderStatesAndEvents.uiState.typeBuilderState.totalTime > 2f) {
                                //   navigate =true
                            }
                            showActionOptions = !showActionOptions
                        },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            "Small floating action button.",
                            modifier = Modifier.size(32.dp)
                        )

                        Text(text = stringResource(Res.string.submit))

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ExtendedFloatingActionButton(
                        onClick = {
                            navigator?.replaceAll(ObjectItemScreenClass())
                        },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Icon(
                            Icons.Filled.Clear,
                            "Small floating action button.",
                            modifier = Modifier.size(32.dp)
                        )

                        Text(text = stringResource(Res.string.delete))

                    }
                }
            }
            AnimatedVisibility(!showActionOptions) {
                SmallFloatingActionButton(
                    onClick = { showActionOptions = !showActionOptions },
                    modifier = Modifier
                        .size(54.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(Icons.Filled.Edit, "Small floating action button.")
                }
            }
        }
    ) { paddingVal ->

        Box(modifier = Modifier.padding(paddingVal), contentAlignment = Alignment.TopEnd) {

            Column(modifier.verticalScroll(rememberScrollState()).padding(paddingVal)) {

                LaunchedEffect(snackBarHostState) {
                    summariseBuilderStatesAndEvents.uiState.uiMessage.consumeAsFlow().collect {
                        // snackBarHostState.showSnackbar(it, duration = SnackbarDuration.Long)
                        navigator?.replaceAll(ObjectItemScreenClass(builderMes = it ))
                    }
                }

                MainObjectHeaderItem(
                    //navigator = navigator,
                    mainObjectHeaderItemData = summariseBuilderStatesAndEvents.uiState.currentSum,
                    onMainObjectClick = {},
                    onTopArchiveButton = { navigator?.push(ObjectItemScreenClass()) },
                    modifier = modifier
                )

                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier) {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = stringResource(Res.string.picked_date),
                            modifier = Modifier.padding(bottom = 8.dp, end = 12.dp)
                        )
                        WheelDatePicker(
                            rowCount = 2,
                            selectorProperties = WheelPickerDefaults.selectorProperties(border = null, color = MaterialTheme.colorScheme.background),
                            size = DpSize(180.dp,80.dp),
                           // height = 45.dp,
                            //hideHeader = true,
                            onSnappedDate = { summariseBuilderStatesAndEvents.onDate(it.toString()) },
                            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                        )
                    }

                    Column(modifier = Modifier) {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = stringResource(Res.string.base_hour),
                            modifier = Modifier.padding(bottom = 8.dp, end = 48.dp)
                        )

                        OutlinedButton(modifier=Modifier.padding(start = 36.dp)
                                ,onClick = {showBasePickerDialog=true}){
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = "${summariseBuilderStatesAndEvents.uiState.typeBuilderState.baseWage}$",
                            )
                        }
                    }

                }
                /*
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = "Picked Date :"
                        )
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = "${theState.startTime.date.dayOfMonth}/${theState.startTime.date.month}/${theState.startTime.date.year}"
                        )

                         */

                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier.padding(start = 20.dp, end = 25.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                        Column(modifier = Modifier) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(Res.string.start_time),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            WheelTimePicker(
                                rowCount = 1,
                                selectorProperties = WheelPickerDefaults.selectorProperties(color = MaterialTheme.colorScheme.background,
                                    border = null), textColor = MaterialTheme.colorScheme.primary,
                                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                                size = DpSize(100.dp,80.dp),
                                onSnappedTime = {
                                    summariseBuilderStatesAndEvents.onStime(
                                        it.toString()
                                    )
                                },
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }

                   Icon(Icons.Default.KeyboardDoubleArrowRight,"")

                        Column(modifier = Modifier) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(Res.string.end_time),
                                modifier = Modifier.padding(bottom = 4.dp, end = 32.dp)
                            )
                            WheelTimePicker(
                                rowCount = 1,
                                selectorProperties = WheelPickerDefaults.selectorProperties(color = MaterialTheme.colorScheme.background,
                                    border = null), textColor = MaterialTheme.colorScheme.primary,
                                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                                size = DpSize(100.dp,80.dp),
                                onSnappedTime = {
                                    summariseBuilderStatesAndEvents.onEtime(
                                        it.toString()
                                    )
                                },
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }
                    }


                        /*
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = "Start Time :"
                        )
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = "${theState.startTime.time.hour} : ${theState.startTime.minute}"
                        )
                    }
                    Column(Modifier.clickable { showEndTimeDialog = true }) {
                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            text = "End Time :"
                        )
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = "${theState.endTime.time.hour} : ${theState.endTime.minute}"
                        )
                    }
                }
                  Spacer(modifier = Modifier.height(28.dp))
                         */

                        Row(
                            modifier
                                .padding(start = 45.dp, end = 45.dp)
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.extras), style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                val a = (theState.extras / 5).toInt() - 1
                                WheelPicker(
                                    startIndex = if (a > 0) {
                                        (a)
                                    } else {
                                        0
                                    },
                                    count = optionMenu.extraOptions.size,
                                    rowCount = 3,
                                    texts = optionMenu.extraOptions,
                                    contentAlignment = Alignment.Center,
                                    onScrollFinished = {
                                        summariseBuilderStatesAndEvents.onExtra((it * 5).toString())
                                        it
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(Res.string.deliveries),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                WheelPicker(
                                    startIndex = if (theState.delivers - 1 > 0) {
                                        theState.delivers - 1
                                    } else {
                                        0
                                    },
                                    count = optionMenu.deliversOptions.size,
                                    rowCount = 3,
                                    texts = optionMenu.deliversOptions,
                                    contentAlignment = Alignment.Center,
                                    onScrollFinished = {
                                        summariseBuilderStatesAndEvents.onDelivers((it + 1).toString())
                                        it
                                    }
                                )
                            }

                        }

                       // Spacer(modifier = Modifier.height(42.dp))

                        Row(
                            modifier
                                .padding(start = 45.dp, end = 45.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = summariseBuilderStatesAndEvents.uiState.errorMes.asString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                    }

            AnimatedVisibility(showBasePickerDialog){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column (
                        Modifier.clip(MaterialTheme.shapes.extraLarge).fillMaxWidth(0.7f).width(200.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer))
                    {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = stringResource(Res.string.extras), style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,

                            )

                            Icon(Icons.Default.Check,"",
                                modifier=Modifier.clickable {showBasePickerDialog=false}.size(42.dp), tint = MaterialTheme.colorScheme.primary)

                        }
                        val a =summariseBuilderStatesAndEvents.uiState.typeBuilderState.baseWage
                    WheelPicker(
                        startIndex = if(a>0){a}else{1},
                        count = optionMenu.deliversOptions.size,
                        rowCount = 3,
                        texts = optionMenu.deliversOptions,
                        contentAlignment = Alignment.Center,
                        onScrollFinished = {
                            summariseBuilderStatesAndEvents.onBaseWage((it+1).toString())
                            it
                        }
                    )
                }
            }
            }
        }
/*
                    AnimatedVisibility(visible = showStartTimeDialog) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .fillMaxWidth(0.8f)

                            ) {

                                WheelTimePickerDialog(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    showTimePicker = showStartTimeDialog,
                                    title = "Start time picker",
                                    doneLabel = "Done",
                                    titleStyle = MaterialTheme.typography.titleLarge,
                                    doneLabelStyle = MaterialTheme.typography.titleMedium,
                                    startTime = theState.startTime.time,
                                    minTime = LocalTime(0, 0),
                                    maxTime = LocalTime(23, 59),
                                    onDoneClick = {
                                        showStartTimeDialog = false
                                        summariseBuilderStatesAndEvents.onStime(it.toString())
                                    },
                                    height = 120.dp
                                )
                            }
                        }
                    }
                    AnimatedVisibility(visible = showEndTimeDialog) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .fillMaxWidth(0.8f)

                            ) {

                                WheelTimePickerDialog(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    showTimePicker = showEndTimeDialog,
                                    title = "End time picker",
                                    doneLabel = "Done",
                                    titleStyle = MaterialTheme.typography.titleLarge,
                                    doneLabelStyle = MaterialTheme.typography.titleMedium,
                                    startTime = theState.endTime.time,
                                    minTime = LocalTime(0, 0),
                                    maxTime = LocalTime(23, 59),
                                    onDoneClick = {
                                        showEndTimeDialog = false
                                        summariseBuilderStatesAndEvents.onEtime(it.toString())
                                    },
                                    height = 120.dp
                                )
                            }
                        }
                    }
                    AnimatedVisibility(visible = showDateDialog) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .fillMaxWidth(0.8f)
                                    .background(MaterialTheme.colorScheme.secondaryContainer),

                                ) {

                                val currentDate =
                                    Clock.System.now().toLocalDateTime(TimeZone.UTC).date
                                WheelDatePicker(
                                    title = "Declare Date",
                                    doneLabel = "Done",
                                    titleStyle = MaterialTheme.typography.titleLarge,
                                    doneLabelStyle = MaterialTheme.typography.titleMedium,
                                    startDate = theState.startTime.date,
                                    minDate = LocalDate(
                                        year = 2020,
                                        month = Month.FEBRUARY,
                                        dayOfMonth = 1
                                    ),
                                    height = 70.dp,
                                    maxDate = currentDate,
                                    dateTextStyle = MaterialTheme.typography.titleMedium,
                                    dateTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    onDoneClick = {
                                        showDateDialog = false
                                        summariseBuilderStatesAndEvents.onDate(it.toString())
                                    }
                                )
                            }
                        }
                    }

 */

                }
            }


