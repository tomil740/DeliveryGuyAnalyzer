package dataAnalyzer.domain.models.util.helperFun

import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.presentation.util.toPresent
import kotlinx.datetime.LocalDateTime
/*
getSumObjectHeader :
Will define the matched header according to the object attributes ...
todo the all time here should be resources , maybe include here the builder header instead of marking it and apply the
behavior on the UI
 */
fun getSumObjectHeader(objectType: SumObjectsType, startTime: LocalDateTime):String{
    return when(objectType){
        SumObjectsType.MonthSum -> {"${startTime.month.toPresent()} ${startTime.year}"}
      //  SumObjectsType.ShiftSession-> {"${shiftType} ${startTime.dayOfMonth} / ${startTime.month.name}"}
        SumObjectsType.WorkSession->{"${startTime.dayOfMonth} / ${startTime.month.toPresent()},"}
      //  SumObjectsType.YearSum -> {"${startTime.year}"}
        SumObjectsType.AllTimeSum -> {"All Time"}
    }
}