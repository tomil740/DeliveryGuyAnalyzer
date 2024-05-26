package dataAnalyzer.domain.models.util.helperFun

import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDateTime

fun getSumObjectHeader(objectType: SumObjectsType, shiftType: String?, startTime: LocalDateTime):String{
    return when(objectType){
        SumObjectsType.MonthSum -> {"${startTime.month.name} ${startTime.year}"}
      //  SumObjectsType.ShiftSession-> {"${shiftType} ${startTime.dayOfMonth} / ${startTime.month.name}"}
        SumObjectsType.WorkSession->{"${startTime.dayOfMonth} / ${startTime.month.name},"}
      //  SumObjectsType.YearSum -> {"${startTime.year}"}
        SumObjectsType.AllTimeSum -> {"All Time"}
    }
}