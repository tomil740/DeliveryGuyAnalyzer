package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.presentation.util.UiText
import kotlinx.coroutines.channels.Channel

//according to our architecture we implement the matched UI states of our screen at this object
data class SummariseDeclareBuilderUiState(
    val typeBuilderState: SummariseBuilderState,
    val dayOfMonthMenu : List<Int>,
    val totalIncome : Float,
    val currentSum: SumObjDomain,
    val uiMessage : Channel<UiText>,
    val navigateMessage : Channel<UiText>,
    val errorMes:UiText,
)

