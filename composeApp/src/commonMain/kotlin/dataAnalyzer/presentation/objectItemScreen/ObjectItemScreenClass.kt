package dataAnalyzer.presentation.objectItemScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.presentation.util.UiText

/*
ObjectItemScreenClass :
An voygar navigation class demand , this class perpus is to connect between screen and initialize it with our dependency injection
 */
class ObjectItemScreenClass(private val initializeObj: SumObjDomain? = null, private val builderMes:UiText? = null):Screen {

    @Composable
    override fun Content() {
        val a = getScreenModel<ObjectItemViewmodel>()
        val state by a.uiState.collectAsState()
        val b = ObjectItemStatesAndEvents(uiState = state,
            initializeAnObject = { a.onEvent(ObjectItemEvents.InitializeAnObject(it)) },
            onAllTimeData = {a.onEvent(ObjectItemEvents.GetValueAllArchive(""))},
            onInitializeScreen = {a.onEvent(ObjectItemEvents.GetValueCurrentMonthArchive)},
            onUiMessage = {a.onEvent(ObjectItemEvents.SendUiMessage(it))}
        )
        ObjectItemScreen(builderMes = builderMes,  objectItemStatesAndEvents = b,modifier = Modifier,
            initializeObj = initializeObj
        )
    }

}