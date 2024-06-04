package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
/*
SummariseDeclareBuilderScreenClass:
voygar library demand to implement our navigation with the matched needed dependencies (with the help of coin as well )
 */
class SummariseDeclareBuilderScreenClass():Screen {
    @Composable
    override fun Content() {
        val a = getScreenModel<SummariseDeclareBuilderViewmodel>()
        val state by a.state.collectAsState()
        val b = SummariseBuilderStatesAndEvents(uiState = state,
            onSubmitDeclare = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnSubmit)},
            onDate = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnDate(it))},
            onDelivers = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnDelivers(it))},
            onETime = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnEndTime(it))},
            onExtra = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnExtra(it))},
            onSTime ={a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnStartTime(it))},
            onBaseWage = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnBaseWage(it.toInt()))},
            )

        SummariseBuilderScreen(summariseBuilderStatesAndEvents =b ,modifier = Modifier)
    }

}