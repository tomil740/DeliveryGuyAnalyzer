package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dataAnalyzer.presentation.objectItemScreen.ObjectItemEvents
import dataAnalyzer.presentation.objectItemScreen.ObjectItemStatesAndEvents
import dataAnalyzer.presentation.objectItemScreen.ObjectItemViewmodel

class SummariseDeclareBuilderScreenClass():Screen {
    @Composable
    override fun Content() {
        val a = getScreenModel<SummariseDeclareBuilderViewmodel>()
        val state by a.state.collectAsState()
        val b = SummariseBuilderStatesAndEvents(uiState = state,
            onSubmitDeclare = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnSubmitDeclare)},
            onDate = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnDate(it))},
            onDelivers = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnDelivers(it))},
            onEtime = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnEndTime(it))},
            onExtra = {a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnExtra(it))},
            onStime ={a.onSummariseDeclareBuilderEvent(SummariseDeclareBuilderEvents.OnStartTime(it))}
        )

        SummariseBuilderScreen(summariseBuilderStatesAndEvents =b ,modifier = Modifier)
    }

}