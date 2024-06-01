package dataAnalyzer.presentation.objectItemScreen

import dataAnalyzer.domain.models.models.SumObjDomain


/*
basic sealed class to represent our screen event according to the clean architecture pattern
 */
sealed class ObjectItemEvents {

    data class InitializeAnObject(val theSum: SumObjDomain):ObjectItemEvents()
    data class GetValueAllArchive(val workingPlatform: String):ObjectItemEvents()
    data object GetValueCurrentMonthArchive:ObjectItemEvents()
    data class  SendUiMessage(val mess:String):ObjectItemEvents()

}