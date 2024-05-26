package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.models.SumObjectInterface


data class ObjectItemStatesAndEvents(
    val uiState: ObjectItemUiState,
    val initializeAnObject:(SumObjectInterface)->Unit,
    val onAllTimeData: ()->Unit,
    val onInitializeScreen: ()->Unit,
    val onUiMessage:(String)->Unit
)

