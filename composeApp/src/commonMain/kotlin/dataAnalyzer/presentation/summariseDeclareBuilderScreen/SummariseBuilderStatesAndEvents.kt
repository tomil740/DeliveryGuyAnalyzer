package dataAnalyzer.presentation.summariseDeclareBuilderScreen

data class SummariseBuilderStatesAndEvents(
    val uiState : SummariseDeclareBuilderUiState,
    val onSubmitDeclare : ()->Unit,

    val onExtra:(String)->Unit,
    val onStime:(String)->Unit,
    val onEtime:(String)->Unit,
    val onDate:(String)->Unit,
    val onDelivers:(String)->Unit

)