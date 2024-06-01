package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.models.SumObjDomain

/*
ObjectItemStatesAndEvents :
this data class defines our screen state and events thats need to be operated from the UI
* this class is build according to the clean architecture pattern
 */
data class ObjectItemStatesAndEvents(
    val uiState: ObjectItemUiState,
    val initializeAnObject:(SumObjDomain)->Unit,
    val onAllTimeData: ()->Unit,
    val onInitializeScreen: ()->Unit,
    val onUiMessage:(String)->Unit
)

