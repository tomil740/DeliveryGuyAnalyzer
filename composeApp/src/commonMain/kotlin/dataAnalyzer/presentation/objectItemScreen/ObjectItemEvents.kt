package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.models.SumObjectInterface

sealed class ObjectItemEvents {

    data class InitializeAnObject(val theSum: SumObjectInterface):ObjectItemEvents()

    data class GetValueAllArchive(val workingPlatform: String):ObjectItemEvents()
    data object GetValueCurrentMonthArchive:ObjectItemEvents()
    data class  SendUiMessage(val mess:String):ObjectItemEvents()

}