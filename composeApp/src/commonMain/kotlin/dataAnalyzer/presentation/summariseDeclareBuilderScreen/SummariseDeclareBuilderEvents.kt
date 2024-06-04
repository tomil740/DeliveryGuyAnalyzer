package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import dataAnalyzer.presentation.util.UiText

/*
according to our architecture we define all of our screen events to implement an match function
for dealing with all of them together ...
 */
sealed class SummariseDeclareBuilderEvents() {
    data class  SendUiMessage(val mess:UiText): SummariseDeclareBuilderEvents()
    data class  SendNavigateMessage(val mess:UiText): SummariseDeclareBuilderEvents()
    data class OnStartTime(val sTime:String):SummariseDeclareBuilderEvents()
    data class OnEndTime(val eTime:String):SummariseDeclareBuilderEvents()
    data class OnDate(val date :String):SummariseDeclareBuilderEvents()
    data class OnDelivers(val deliversVal:String):SummariseDeclareBuilderEvents()
    data class OnExtra(val extraVal:String):SummariseDeclareBuilderEvents()
    data class OnBaseWage(val baseWage:Int):SummariseDeclareBuilderEvents()
     data object OnSubmit:SummariseDeclareBuilderEvents()
}