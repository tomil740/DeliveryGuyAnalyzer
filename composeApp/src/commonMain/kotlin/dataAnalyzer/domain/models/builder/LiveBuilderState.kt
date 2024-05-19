package dataAnalyzer.domain.models.builder

import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import kotlinx.datetime.LocalDateTime

data class LiveBuilderState(
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val totalTime : Float,
    val baseWage : Float,
    val totalBase : Float = baseWage*totalTime,
    val extras : Float,
    val delivers : Int,
    val deliversItem: List<LiveDeliveryItem>
){
    fun toWorkSessionSum(): SumObj {
        val theTime = getTimeDifferent(startTime =  startTime.time, endTime =  endTime.time)
        val baseIncome = baseWage*theTime

        return SumObj(startTime =  startTime, endTime = endTime, objectName = "Builder Data",
            totalTime = theTime,  baseIncome = baseIncome
            , extraIncome = extras, totalIncome = baseIncome+extras
            , delivers = delivers, averageIncomePerDelivery = (baseIncome+extras)/delivers,
            averageIncomePerHour = (baseIncome+extras)/theTime, objectType = SumObjectsType.WorkSession,
            averageIncomeSubObj = 1f, averageTimeSubObj = 1f,
            )
    }
}
