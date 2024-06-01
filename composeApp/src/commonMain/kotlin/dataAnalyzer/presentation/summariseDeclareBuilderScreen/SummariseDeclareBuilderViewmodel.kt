package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dataAnalyzer.domain.models.builder.SummariseBuilderFieldsState
import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import dataAnalyzer.domain.useCase.screenUsecases.SummariseBuilderUseCases
import dataAnalyzer.presentation.util.UiText
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.fail_overlap_insert_mes
import deliveryguyanalyzer.composeapp.generated.resources.fail_time_insert_mes
import deliveryguyanalyzer.composeapp.generated.resources.good_work_session
import deliveryguyanalyzer.composeapp.generated.resources.success_insert_mes
import deliveryguyanalyzer.composeapp.generated.resources.there_is_no_delivers_mes
import deliveryguyanalyzer.composeapp.generated.resources.there_is_no_tips_mes
import deliveryguyanalyzer.composeapp.generated.resources.there_is_not_enough_time_length_mes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

@OptIn(ExperimentalResourceApi::class)
class SummariseDeclareBuilderViewmodel(val summariseBuilderUseCases: SummariseBuilderUseCases):ScreenModel {
    /*
   comparedObj :
   an component of the uiState object , will be an workSession sum (at this screen) , we will get the average of them from our db archive ...
    */
    private val builderValuesStateNotes = MutableStateFlow(SummariseBuilderFieldsState(false,false,false))

    private val uiMessage = Channel<UiText>()


    var submitJob: Job?=null

    private var summariseBuilderState = MutableStateFlow(SummariseBuilderState(
        startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        totalTime =  getTimeDifferent(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
        , baseWage = 35, extras = 55f, delivers = 5
    )
    )

    private var _uiState = MutableStateFlow(SummariseDeclareBuilderUiState(
        totalIncome = (summariseBuilderState.value.totalTime*summariseBuilderState.value.baseWage)+summariseBuilderState.value.extras,
        currentSum = summariseBuilderState.value.toSumObjDomain(),
        uiMessage = uiMessage,
        typeBuilderState = summariseBuilderState.value,
        errorMes = UiText.DynamicString("Clear"),
        dayOfMonthMenu = listOf()
    ))

    val state : StateFlow<SummariseDeclareBuilderUiState> =
        combine(summariseBuilderState, _uiState) {
                typeBuilderState, state ->
            //update the builder state accordingly with the data of it as well
            val c = typeBuilderState.toSumObjDomain()
            SummariseDeclareBuilderUiState(
                typeBuilderState=typeBuilderState,
                currentSum = c,
                totalIncome = c.totalIncome,
                uiMessage = uiMessage,
                errorMes = state.errorMes,
                dayOfMonthMenu = state.dayOfMonthMenu
            )
        }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        screenModelScope.launch {

            launch {
                builderValuesStateNotes.collect{
                    var theMes = ""
                    if (!it.delivers)
                        theMes+= getString(Res.string.there_is_no_delivers_mes)
                    if (!it.extra)
                        theMes+=getString(Res.string.there_is_no_tips_mes)
                    if (!it.totalTime)
                        theMes+=getString(Res.string.there_is_not_enough_time_length_mes)
                    if(theMes == "")
                        theMes = getString(Res.string.good_work_session)

                    _uiState.update { it.copy(errorMes = UiText.DynamicString(theMes)) }
                }
            }
        }

    }

    @OptIn(ExperimentalResourceApi::class)
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
                if (totalTime > 24 || totalTime == -1f) {
                    println("exception if -1f $totalTime ")
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
                if(totalTime > 24 || totalTime == -1f){
                    //onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.
                    //SendUiMessage("The declared time is illegal, the maximum length of an workSession is 23 hours..."))
                }else{
                    summariseBuilderState.update { it.copy(startTime = theResult, totalTime = totalTime) }
                    if(totalTime < 4 || totalTime > 14) {
                        builderValuesStateNotes.update { it.copy(totalTime = false) }
                    }else{
                        builderValuesStateNotes.update { it.copy(totalTime = true) }
                    }
                }
            }

            SummariseDeclareBuilderEvents.OnSubmit -> {

                if(summariseBuilderState.value.totalTime < 2f){
                    onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.SendUiMessage
                        (UiText.StringResource(Res.string.fail_time_insert_mes)))
                }else {
                    submitJob?.cancel()
                   submitJob = screenModelScope.launch {
                        /*
                        the insert use case is an suspend fucntion which means as if I would place here an delay the coroutine will be wanting
                        for it , as with await just that the function will wait period .
                        * accoridngly wwe will update the cahnnle whic is an aSync function as well that will update the listenter on there coroutines...
                         */
                       val theObj = summariseBuilderState.value
                        val result = summariseBuilderUseCases.insertWorkDeclare(theObj)
                       val theDate = summariseBuilderState.value.startTime
                       val a ="${theDate.dayOfMonth}/${theDate.month.name}/${theDate.year}"
                        if(result){
                            withContext(Dispatchers.Main) {
                                onSummariseDeclareBuilderEvent(
                                    SummariseDeclareBuilderEvents.SendUiMessage
                                        (UiText.StringResource(Res.string.success_insert_mes,a))
                                )

                            }
                        }else {
                            withContext(Dispatchers.Main) {
                                onSummariseDeclareBuilderEvent(
                                    SummariseDeclareBuilderEvents.SendUiMessage(
                                        UiText.StringResource(Res.string.fail_overlap_insert_mes,a))
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

            is SummariseDeclareBuilderEvents.OnBaseWage -> {
                summariseBuilderState.update { it.copy(baseWage = event.baseWage) }
            }
        }
    }














}