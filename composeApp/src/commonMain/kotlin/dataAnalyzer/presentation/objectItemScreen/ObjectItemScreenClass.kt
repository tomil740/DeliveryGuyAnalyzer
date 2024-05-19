package dataAnalyzer.presentation.objectItemScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dataAnalyzer.domain.models.models.SumObjectInterface


class ObjectItemScreenClass(private val initializeObj: SumObjectInterface? = null):Screen {

    @Composable
    override fun Content() {
        val a = getScreenModel<ObjectItemViewmodel>()
        val state by a.uiState.collectAsState()
        val b = ObjectItemStatesAndEvents(uiState = state,
            initializeAnObject = { a.onEvent(ObjectItemEvents.InitializeAnObject(it)) },
            onValueArchiveTopMenu = {a.onEvent(ObjectItemEvents.GetValueAllArchive(it))}
        )
        ObjectItemScreen(initializeObj = initializeObj, objectItemStatesAndEvents = b,modifier = Modifier)
    }

}