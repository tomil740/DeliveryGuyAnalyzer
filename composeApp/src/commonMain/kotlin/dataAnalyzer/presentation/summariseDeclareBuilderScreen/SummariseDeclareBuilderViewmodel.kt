package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dataAnalyzer.domain.models.builder.SummariseBuilderFieldesState
import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import dataAnalyzer.domain.useCase.SummariseBuilderUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class SummariseDeclareBuilderViewmodel(val summariseBuilderUseCases: SummariseBuilderUseCases):ScreenModel {
    /*
   comparedObj :
   an component of the uiState object , will be an workSession sum (at this screen) , we will get the average of them from our db archive ...
    */
    private val builderValuesStateNotes = MutableStateFlow(SummariseBuilderFieldesState(false,false,false))

    private val uiMessage = Channel<String>()

    private var summariseBuilderState = MutableStateFlow(SummariseBuilderState(
        startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        totalTime =  getTimeDifferent(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
        , baseWage = 35f, extras = 55f, delivers = 5
    )
    )

    private var _uiState = MutableStateFlow(SummariseDeclareBuilderUiState(
        totalIncome = (summariseBuilderState.value.totalTime*summariseBuilderState.value.baseWage)+summariseBuilderState.value.extras,
        currentSum = summariseBuilderState.value.toWorkSessionSum(),
        uiMessage = uiMessage,
        typeBuilderState = summariseBuilderState.value,
        errorMes = "Clear",
    ))

    val state : StateFlow<SummariseDeclareBuilderUiState> =
        combine(summariseBuilderState, _uiState) {
                typeBuilderState, state ->
            //update the builder state accordingly with the data of it as well
            val c = typeBuilderState.toWorkSessionSum()
            SummariseDeclareBuilderUiState(
                typeBuilderState=typeBuilderState,
                currentSum = c,
                totalIncome = c.totalIncome,
                uiMessage = uiMessage,
                errorMes = state.errorMes
            )
        }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        screenModelScope.launch {

            launch {
                builderValuesStateNotes.collect{
                    var theMes = ""
                    if (!it.delivers)
                        theMes+="There is no delivers ?"
                    if (!it.extra)
                        theMes+="There is no tips ?"
                    if (!it.totalTime)
                        theMes+="This is unusual work Session time length"
                    if(theMes == "")
                        theMes = "Nice work session!"

                    _uiState.update { it.copy(errorMes = theMes) }
                }
            }
        }

    }

    fun onSummariseDeclareBuilderEvent(event:SummariseDeclareBuilderEvents){
        when(event){
            is SummariseDeclareBuilderEvents.OnDate -> {
                val a = LocalDate.parse(event.date)
                summariseBuilderState.update { it.copy( startTime = LocalDateTime(date = a, time = it.startTime.time)) }
                onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnEndTime(summariseBuilderState.value.endTime.time.toString()))
            }
            is SummariseDeclareBuilderEvents.OnDelivers ->{
                val a= event.deliversVal.toInt()
                summariseBuilderState.update { it.copy(delivers = a) }
                if(a!=1) {
                    builderValuesStateNotes.update { it.copy(delivers = true) }
                }else{
                    builderValuesStateNotes.update { it.copy(delivers = false) }
                }
            }
            is SummariseDeclareBuilderEvents.OnEndTime -> {
                val a = LocalTime.parse(event.eTime)
                var theResult =
                    LocalDateTime(date = summariseBuilderState.value.startTime.date, time = a)
                if (summariseBuilderState.value.startTime.time > a)
                    theResult =
                        LocalDateTime(date = theResult.date.plus(DatePeriod(days = 1)), time = a)
                val totalTime = try {
                    getTimeDifferent(
                        endTime = theResult.time,
                        startTime = summariseBuilderState.value.startTime.time
                    )
                } catch (e: Exception) {
                    -1f
                }
                if (totalTime > 23 || totalTime == -1f) {
                    onSummariseDeclareBuilderEvent(
                        SummariseDeclareBuilderEvents.SendUiMessage("The declared time is illegal, the maximum length of an workSession is 23 hours...")
                    )
                } else {
                    summariseBuilderState.update { it.copy(endTime = theResult, totalTime = totalTime) }
                    if (totalTime < 4 || totalTime > 14) {
                        builderValuesStateNotes.update { it.copy(totalTime = false) }
                    } else {
                        builderValuesStateNotes.update { it.copy(totalTime = true) }
                    }
                }
            }
            is SummariseDeclareBuilderEvents.OnExtra -> {
                val a = event.extraVal.toFloat()
                summariseBuilderState.update { it.copy(extras =a) }
                if(a !=0f) {
                    builderValuesStateNotes.update { it.copy(extra = true) }
                }else {
                    builderValuesStateNotes.update { it.copy(extra = false) }
                }
            }
            is SummariseDeclareBuilderEvents.OnStartTime -> {
                val a = LocalTime.parse(event.sTime)
                var theResult=LocalDateTime(date = summariseBuilderState.value.startTime.date, time = a)
                val totalTime = try { getTimeDifferent(startTime = theResult.time, endTime = summariseBuilderState.value.endTime.time)
                }catch (e:Exception){ -1f}
                if(totalTime > 23 || totalTime == -1f){
                    onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.
                    SendUiMessage("The declared time is illegal, the maximum length of an workSession is 23 hours..."))
                }else{
                    summariseBuilderState.update { it.copy(startTime = theResult, totalTime = totalTime) }
                    if(totalTime < 4 || totalTime > 14) {
                        builderValuesStateNotes.update { it.copy(totalTime = false) }
                    }else{
                        builderValuesStateNotes.update { it.copy(totalTime = true) }
                    }
                }
            }
            SummariseDeclareBuilderEvents.OnSubmitDeclare -> {
                if(summariseBuilderState.value.totalTime < 2f){
                    onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.SendUiMessage
                        ("Fail to insert ,your work declare must be in minimum of 2 hours long in order to be insert"))
                }else {
                    screenModelScope.launch {
                        val result = summariseBuilderUseCases.insertWorkDeclare(summariseBuilderState.value.toWorkDeclareDto())
                        if(result){
                            withContext(Dispatchers.Main) {
                                onSummariseDeclareBuilderEvent(
                                    SummariseDeclareBuilderEvents.SendUiMessage(
                                        "new declare has been successfully added"
                                    )
                                )
                            }
                        }else {
                            withContext(Dispatchers.Main) {
                                onSummariseDeclareBuilderEvent(
                                    SummariseDeclareBuilderEvents.SendUiMessage(
                                        "the new declare has been fail to insert , please try again (the builder data will be saved...)"
                                    )
                                )
                            }
                        }
                    }
                }

            }
            is SummariseDeclareBuilderEvents.SendUiMessage -> {
                screenModelScope.launch {
                    uiMessage.send(event.mess)
                }
            }
        }
    }














}