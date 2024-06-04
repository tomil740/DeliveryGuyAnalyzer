package dataAnalyzer.domain.models.builder

import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import dataAnalyzer.presentation.util.toPresent
import kotlinx.datetime.LocalDateTime


/*
SummariseBuilderState :
This class representing the builder data as it its from the UI
the matched clases we map to below will make the needed adjuments to insert or pull the statistics
 */
data class SummariseBuilderState(
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val totalTime : Float,
    val baseWage : Int,
    val totalBase : Float = baseWage*totalTime,
    val extras : Float,
    val delivers : Int,
) {
    fun toSumObjDomain(): SumObjDomain {
        val theTime = getTimeDifferent(startTime = startTime.time, endTime = endTime.time)
        val baseIncome = baseWage * theTime

        return SumObjDomain(
            objectName = "BL${startTime.date.dayOfMonth}/${startTime.date.month.toPresent()}",
            startTime = startTime,
            endTime = endTime,
            totalTime = theTime,
            baseIncome = baseIncome,
            extraIncome = extras,
            totalIncome = baseIncome + extras,
            delivers = delivers,
            averageIncomePerDelivery1 = (baseIncome)/delivers,averageIncomePerDelivery2 = (extras)/delivers,
            averageIncomePerHour1 = (baseIncome)/theTime,averageIncomePerHour2 = (extras)/theTime, objectType = SumObjectsType.WorkSession,
        )
    }
}
