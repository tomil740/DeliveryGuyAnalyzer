package dataAnalyzer.presentation.objectItemScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.useCase.screenUsecases.ObjectItemUseCases
import dataAnalyzer.presentation.util.DefaultData
import deliveryguyanalyzer.composeapp.generated.resources.Res
import deliveryguyanalyzer.composeapp.generated.resources.empty_all_data_list
import deliveryguyanalyzer.composeapp.generated.resources.empty_month_list
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString

/*
ObjectItemViewmodel :
According to our picked architecture we will use an common android component "view model" which is job is to handle the
business logic and connect between our data layer and our presentation layer as clean as possible
*we are using clean architecture so the vm job will be mostly business logics*

Arguments : objectItemUseCases an group of the needed use case to our screen according to the clean architecture we will model
our tasks to an small and focused domain level class


 */
class ObjectItemViewmodel(private val objectItemUseCases: ObjectItemUseCases): ScreenModel {

    //the job for our data pulling coroutines
    private var pullAllDataJob :Job?=null

    //the job for our data pulling coroutines
    private var pullDataJob :Job?=null

    //an channel of type string to emit the snack bar message for our UI
    private val uiMessage = Channel<String>()

    //the private updating mutableStateflow to mange our screen state , basically all of the screen data
    private val _uiState = MutableStateFlow(
        ObjectItemUiState(
            objectValueSum = DefaultData().valuedObj.value,
            uiMessage = uiMessage
        )
    )
    //the observable stateflow ui state that is listening to the original ui state
    var uiState = _uiState.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
        //the default initialization
        screenModelScope.launch {
            onEvent(ObjectItemEvents.GetValueCurrentMonthArchive)
        }
    }

    var theJob:Job?=null
    @OptIn(ExperimentalResourceApi::class)
    fun onEvent(event:ObjectItemEvents){
        when(event){
            is ObjectItemEvents.GetValueAllArchive -> {
                /*
              this use case is the main process of the app , it will pull all of the db data
              and apply on it the matched use cases to calculate from it the needed data...
               */
                pullAllDataJob?.cancel()
                pullAllDataJob = screenModelScope.launch {
                    //we access an task that may require a lot of processing
                     withContext(Dispatchers.IO) {
                        //get every month data list by passing the vm scope (controlling it by our job...)
                        //send the data to the sumarise function , return the result object of the all time sumobj
                        val result = objectItemUseCases.getAllWorkDeclares.invoke().await()

                        if (result.isEmpty()) {
                            //in case there is no data at all (if some months is missings we just skip them )
                            withContext(Dispatchers.Main) {
                                uiMessage.send(getString(Res.string.empty_all_data_list))
                            }
                        } else {
                            /*
                            using another use case on our pulled data to summarise it into the target object...
                             */
                            val theLst = mutableListOf<WorkSum>()
                            for (month in result) {
                                //the use of the await is processing because as mentioned those could be heavy process that
                                //depend on each other ...
                                val a = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                    month.data,
                                    SumObjectsType.MonthSum
                                ).await()
                                theLst.add(a)
                            }
                            val result = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                theLst,
                                SumObjectsType.AllTimeSum
                            ).await().toSumObjDomain(SumObjectsType.AllTimeSum)
                            withContext(Dispatchers.Main) {
                                _uiState.update { it.copy(objectValueSum = result) }
                            }
                        }
                    }
                }
            }
            is ObjectItemEvents.InitializeAnObject -> {
                _uiState.update { it.copy(objectValueSum = event.theSum) }
            }
            is ObjectItemEvents.SendUiMessage -> {
                theJob?.cancel()
                theJob =
                screenModelScope.launch {
                   uiMessage.send(event.mess)
                }
            }

            ObjectItemEvents.GetValueCurrentMonthArchive -> {

                pullDataJob?.cancel()
                pullDataJob = screenModelScope.launch {
                   withContext(Dispatchers.IO) {
                        //get every month data list by passing the vm scope (controlling it by our job...)
                        //send the data to the sumarise function , return the result object of the all time sumobj
                        val theResult = objectItemUseCases.getCurrentMonthDeclares.invoke()
                        if (theResult.isEmpty()) {
                            //in case there is no data at all (if some months is missings we just skip them )
                            withContext(Dispatchers.Main) {

                                uiMessage.send(getString(Res.string.empty_month_list))
                            }
                        } else {
                            val result = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                theResult,
                                SumObjectsType.MonthSum
                            ).await().toSumObjDomain(SumObjectsType.MonthSum)
                            withContext(Dispatchers.Main) {
                                _uiState.update { it.copy(objectValueSum = result) }
                            }
                        }
                    }
                }
            }
        }
    }

}