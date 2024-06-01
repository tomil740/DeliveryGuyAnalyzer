package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.presentation.util.UiText
import kotlinx.coroutines.channels.Channel

/*
according to our picked architecture pattern we define the screen states in one object that will be easy to update
and observe from the viewmodel life cycle in order to get interactive UI
 */
data class ObjectItemUiState(
    val objectValueSum: SumObjDomain,
    val uiMessage : Channel<String>,
)
