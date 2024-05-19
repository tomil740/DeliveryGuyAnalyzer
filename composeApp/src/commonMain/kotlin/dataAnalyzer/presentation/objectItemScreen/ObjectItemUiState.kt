package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.models.SumObjectInterface
import kotlinx.coroutines.channels.Channel

data class ObjectItemUiState(
    val objectValueSum: SumObjectInterface,
    val uiMessage : Channel<String>,
)
