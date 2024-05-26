package dataAnalyzer.presentation.objectItemScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.useCase.screenUsecases.ObjectItemUseCases
import dataAnalyzer.presentation.util.DefaultData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ObjectItemViewmodel(private val objectItemUseCases: ObjectItemUseCases): ScreenModel {

    private var getAllJob :Job?=null

     private val valuedObj = (DefaultData().valuedObj)

    private val uiMessage = Channel<String>()

    private val _uiState = MutableStateFlow(
        ObjectItemUiState(
            objectValueSum = valuedObj.value,
            uiMessage = uiMessage
        )
    )

    val uiState = combine(valuedObj, _uiState) { valueObj, _uiState ->
        if(valueObj.objectType == SumObjectsType.WorkSession){

        }
        ObjectItemUiState(
            objectValueSum = valueObj,
            uiMessage = uiMessage
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
        screenModelScope.launch {
                onEvent(ObjectItemEvents.GetValueCurrentMonthArchive)
        }
    }

    var theJob:Job?=null
    fun onEvent(event:ObjectItemEvents){
        when(event){
            is ObjectItemEvents.GetValueAllArchive -> {
                /*
              this use case is the main process of the app , it will pull all of the db data
              and apply on it the matched use cases to calculate from it the needed data...
               */
                getAllJob?.cancel()
                getAllJob = screenModelScope.launch {
                    CoroutineScope(Dispatchers.IO).launch {
                        //get every month data list by passing the vm scope (controlling it by our job...)
                        //send the data to the sumarise function , return the result object of the all time sumobj
                        val resutlt = objectItemUseCases.getAllWorkDeclares.invoke()

                        if (resutlt.isEmpty()) {
                            //in case there is no data at all (if some months is missings we just skip them )
                            withContext(Dispatchers.Main) {
                                uiMessage.send("There is no data...")
                            }
                        } else {
                            val theLst = mutableListOf<WorkSumDomain>()
                            for (month in resutlt) {
                                val a = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                    month.data,
                                    SumObjectsType.MonthSum
                                )
                                theLst.add(a)
                            }
                            val result = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                theLst,
                                SumObjectsType.AllTimeSum
                            ).toSumObj(SumObjectsType.AllTimeSum)
                            withContext(Dispatchers.Main) {
                                valuedObj.update { result }
                            }
                        }
                    }
                }
            }
            is ObjectItemEvents.InitializeAnObject -> {
                valuedObj.update { (event.theSum) }
            }
            is ObjectItemEvents.SendUiMessage -> {
                theJob?.cancel()
                theJob =
                screenModelScope.launch {
                   uiMessage.send(event.mess)
                }
            }

            ObjectItemEvents.GetValueCurrentMonthArchive -> {

                getAllJob?.cancel()
                getAllJob = screenModelScope.launch {
                    CoroutineScope(Dispatchers.IO).launch {
                        //get every month data list by passing the vm scope (controlling it by our job...)
                        //send the data to the sumarise function , return the result object of the all time sumobj
                        val theResult = objectItemUseCases.getCurrentMonthDeclares.invoke()
                        if (theResult.isEmpty()) {
                            //in case there is no data at all (if some months is missings we just skip them )
                            withContext(Dispatchers.Main) {
                                uiMessage.send("There is no data at the current month...")
                            }
                        } else {
                            val result = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                theResult,
                                SumObjectsType.MonthSum
                            ).toSumObj(SumObjectsType.MonthSum)
                            withContext(Dispatchers.Main) {
                                valuedObj.update { result }
                            }
                        }
                    }
                }
            }
        }
    }

}