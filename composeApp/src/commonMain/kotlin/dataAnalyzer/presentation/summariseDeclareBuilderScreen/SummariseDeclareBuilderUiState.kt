package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.models.SumObjectInterface
import kotlinx.coroutines.channels.Channel

data class SummariseDeclareBuilderUiState(
    val typeBuilderState: SummariseBuilderState,
    val totalIncome : Float,
    val currentSum: SumObjectInterface,
    val uiMessage : Channel<String>,
    val errorMes:String,
)

