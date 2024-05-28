package dataAnalyzer.presentation.summariseDeclareBuilderScreen

import dataAnalyzer.presentation.util.UiText

sealed class SummariseDeclareBuilderEvents() {
    data class  SendUiMessage(val mess:UiText): SummariseDeclareBuilderEvents()
    data class OnStartTime(val sTime:String):SummariseDeclareBuilderEvents()
    data class OnEndTime(val eTime:String):SummariseDeclareBuilderEvents()
    data class OnDate(val date :String):SummariseDeclareBuilderEvents()
    data class OnDelivers(val deliversVal:String):SummariseDeclareBuilderEvents()
    data class OnExtra(val extraVal:String):SummariseDeclareBuilderEvents()
    data class OnBaseWage(val baseWage:Int):SummariseDeclareBuilderEvents()

     object OnSubmit:SummariseDeclareBuilderEvents()
}