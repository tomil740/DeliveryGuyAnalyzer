package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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

/*
SummariseDeclareBuilderViewmodel :
according to the clean architecture and the MVVM we will apply all of the business logic that are demand to
connect our screen UI to his data layer
* arguments : the matched use case class to define the needed functions (according to the clean architecture pattern )
 */
@OptIn(ExperimentalResourceApi::class)
class SummariseDeclareBuilderViewmodel(private val summariseBuilderUseCases: SummariseBuilderUseCases):ScreenModel {

   //the feed back for our builder use input fields , an helpful note according to unusual data inputs to the user
    private val builderValuesStateNotes = MutableStateFlow(SummariseBuilderFieldsState())

    //the two channel type state that will be implemented in our screen UI state in order to get sncak bar messages from the UI
    private val uiMessage = Channel<UiText>()
    private val navigateMessage = Channel<UiText>()

    //the main use case of submit an declare coroutine job , in order of tracking our coroutine
    //and make sure we sync it with our vm life cycle
    private var submitJob: Job? = null

    /*
    //    all of the builder fileds will be an independent state flow :
    //    * that in order to update it frequnetly without coping the all UI state
    //    mostly for comfort and cleaner code preferences
    */
    private var summariseBuilderState = MutableStateFlow(SummariseBuilderState(
        startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        totalTime =  getTimeDifferent(startTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, endTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
        , baseWage = 35, extras = 55f, delivers = 5
    )
    )
    //the UI screen state , at the private version that will update in this vm and will be observe
    //at the public UI state
    private var _uiState = MutableStateFlow(SummariseDeclareBuilderUiState(
        totalIncome = (summariseBuilderState.value.totalTime*summariseBuilderState.value.baseWage)+summariseBuilderState.value.extras,
        currentSum = summariseBuilderState.value.toSumObjDomain(),
        uiMessage = uiMessage,
        typeBuilderState = summariseBuilderState.value,
        errorMes = UiText.DynamicString("Clear"),
        dayOfMonthMenu = listOf(),
        navigateMessage = navigateMessage
    ))

    //the public ui state we will expose to our UI , will be observing all the necessary flow with our combine
    //method that will keep it up to date all the time (and will update it in one thread without race conditions)
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
                dayOfMonthMenu = state.dayOfMonthMenu,
                navigateMessage=navigateMessage
            )
        }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        //in basic coroutine scope , with then vm life cycle
        screenModelScope.launch {
            //pull our latest base wage obj from the db, and update the UI state accordingly
            launch {
                summariseBuilderState.update {
                    it.copy(baseWage = summariseBuilderUseCases.getBaseWage.invoke())
                }
            }
            //start listing to the note according to builder data , and update the state accordingly
            launch {
                builderValuesStateNotes.collect{
                    //could be an function but pretty basic ...
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
    //according to the clean architecture we will implement one function to dail with all of our screen events
    @OptIn(ExperimentalResourceApi::class)
    fun onSummariseDeclareBuilderEvent(event:SummariseDeclareBuilderEvents){
        when(event){
            //an matched library at the UI will make sure the data is allwase valid , only updating the state accordingly
            is SummariseDeclareBuilderEvents.OnDate -> {
                val a = LocalDate.parse(event.date)
                summariseBuilderState.update { it.copy(startTime = LocalDateTime(date = a, time = it.startTime.time)) }
                onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnEndTime(summariseBuilderState.value.endTime.time.toString()))
            }
            //update the state , and our note state accordingly
            is SummariseDeclareBuilderEvents.OnDelivers ->{
                val a= event.deliversVal.toInt()
                summariseBuilderState.update { it.copy(delivers = a) }
                if(a!=1) {
                    builderValuesStateNotes.update { it.copy(delivers = true) }
                }else{
                    builderValuesStateNotes.update { it.copy(delivers = false) }
                }
            }
            //update the state , and the related total time attribute according to some minimal edge case validation logic
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
            //update the state , and our note state accordingly
            is SummariseDeclareBuilderEvents.OnExtra -> {
                val a = event.extraVal.toFloat()
                summariseBuilderState.update { it.copy(extras =a) }
                if(a !=0f) {
                    builderValuesStateNotes.update { it.copy(extra = true) }
                }else {
                    builderValuesStateNotes.update { it.copy(extra = false) }
                }
            }
            //update the state , and the related total time attribute according to some minimal edge case validation logic
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
            /*
            the main use case event , submit an declare
            we deal with the different options according to the validation check with help of an use cases
            an apply the matched behaviour accordingly to noticed our UI
            * the validation process is basically business logic , because we call some use case according to clean architecture
              we cant use another use case to all of that process
             */
            SummariseDeclareBuilderEvents.OnSubmit -> {

                if(summariseBuilderState.value.totalTime < 2f){
                    screenModelScope.launch {
                        onSummariseDeclareBuilderEvent(
                            SummariseDeclareBuilderEvents.SendUiMessage(
                                UiText.StringResource(Res.string.fail_time_insert_mes,""))
                        )
                    }
                }else {
                    //because this process is an IO coroutine , we well be more cautious with that coroutine to make sure we
                    //not get into race condition or just causing some unnecessary multi threading
                    submitJob?.cancel()
                   submitJob = screenModelScope.launch {
                        /*
                        the insert use case is an suspend fucntion which means as if I would place here an delay the coroutine will be wanting
                        for it , as with await just that the function will wait period .
                        * accordingly wwe will update the channle whic is an aSync function as well that will update the listenter on there coroutines...
                         */
                       val theObj = summariseBuilderState.value
                       val result = summariseBuilderUseCases.insertWorkDeclare(theObj).await()
                       val theDate = summariseBuilderState.value.startTime
                       val a ="${theDate.dayOfMonth}/${theDate.month.name}/${theDate.year}"
                        if(result){
                            withContext(Dispatchers.Main) {

                                summariseBuilderUseCases.updateBaseWage.invoke(summariseBuilderState.value.baseWage)

                                onSummariseDeclareBuilderEvent(
                                    SummariseDeclareBuilderEvents.SendNavigateMessage
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

            //basic connection between the data to the matched channle
            is SummariseDeclareBuilderEvents.SendUiMessage -> {
                screenModelScope.launch {
                    uiMessage.send(event.mess)
                }
            }
            //no validation needed straight updating our UI state
            is SummariseDeclareBuilderEvents.OnBaseWage -> {
                summariseBuilderState.update { it.copy(baseWage = event.baseWage) }
            }
            //basic connection between the data to the matched channle
            is SummariseDeclareBuilderEvents.SendNavigateMessage -> {
                screenModelScope.launch {
                    navigateMessage.send(event.mess)
                }
            }
        }
    }














}