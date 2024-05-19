package dataAnalyzer.presentation.objectItemScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.useCase.ObjectItemUseCases
import dataAnalyzer.presentation.objectItemScreen.util.DefaultData
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ObjectItemViewmodel(private val objectItemUseCases: ObjectItemUseCases): ScreenModel {

    var getAllJob :Job?=null

     val valuedObj = (DefaultData().valuedObj)

    private val uiMessage = Channel<String>()

    private val _uiState = MutableStateFlow(
        ObjectItemUiState(
            objectValueSum = valuedObj.value,
            uiMessage = Channel {  }
        )
    )

    val uiState = combine(valuedObj, _uiState) { valueObj, _uiState ->
        _uiState.copy(
            objectValueSum = valueObj,
            uiMessage = uiMessage
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {

        screenModelScope.launch {
                onEvent(ObjectItemEvents.GetValueAllArchive(""))
        }
    }

    fun onEvent(event:ObjectItemEvents){
        when(event){
            is ObjectItemEvents.GetValueAllArchive -> {
                getAllJob?.cancel()
                getAllJob = screenModelScope.launch {
                    //get every month data list by passing the vm scope (controlling it by our job...)
                    //send the data to the sumarise function , return the result object of the all time sumobj
                    val resutlt =  objectItemUseCases.getAllWorkDeclares.invoke(screenModelScope)
                    if (resutlt.isEmpty()) {
                        //in case there is no data at all (if some months is missings we just skip them )
                        uiMessage.send("There is no data...")
                    }else{
                        val theLst = mutableListOf<WorkSumDomain>()
                        for (month in resutlt) {
                            val a = objectItemUseCases.sumDomainData.getSummarizesDomainObject(
                                month.data,
                                SumObjectsType.MonthSum
                            )
                            theLst.add(a)
                        }
                        val result = objectItemUseCases.sumDomainData.getSummarizesDomainObject(theLst,SumObjectsType.AllTimeSum).toSumObj(SumObjectsType.AllTimeSum)
                        valuedObj.update { result }
                    }
                }
            }
            is ObjectItemEvents.InitializeAnObject -> {
                valuedObj.update { (event.theSum) }
            }
            is ObjectItemEvents.SendUiMessage -> TODO()
        }
    }

}