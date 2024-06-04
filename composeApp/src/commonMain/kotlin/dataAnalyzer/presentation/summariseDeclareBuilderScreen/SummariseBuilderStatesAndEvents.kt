package dataAnalyzer.presentation.summariseDeclareBuilderScreen

/*
SummariseBuilderStatesAndEvents :
according to the clean arhitecture we define our screen states and events on this object ,
will be send to our UI at the navigation class with the matched needed dependency injection
 */
data class SummariseBuilderStatesAndEvents(
    val uiState : SummariseDeclareBuilderUiState,
    val onSubmitDeclare : ()->Unit,

    val onExtra:(String)->Unit,
    val onSTime:(String)->Unit,
    val onETime:(String)->Unit,
    val onDate:(String)->Unit,
    val onDelivers:(String)->Unit,
    val onBaseWage:(String)->Unit


)