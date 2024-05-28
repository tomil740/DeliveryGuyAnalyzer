package dataAnalyzer.domain.models.builder

import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

data class SummariseBuilderState(
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val totalTime : Float,
    val baseWage : Int,
    val totalBase : Float = baseWage*totalTime,
    val extras : Float,
    val delivers : Int,
) {
    fun toWorkDeclareDto():WorkDeclareDto{
      return  WorkDeclareDto().apply {
            sTime = startTime.toString()
            eTime = endTime.toString()
            baseIncome = (baseWage*totalTime)
            extraIncome = extras
            time = totalTime
            deliveries = delivers
            yearAndMonth = "${startTime.year}${startTime.month.number}"
            dayOfMonth = startTime.dayOfMonth
        }
    }

    fun toLiveBuilderState():LiveBuilderState{
        return LiveBuilderState(
            startTime =startTime,
            endTime =endTime,
            totalTime=totalTime,
            baseWage =baseWage.toFloat(),
            totalBase =totalBase,
            extras = extras,
            delivers =delivers,
            deliversItem= listOf()
        )
    }
    fun toWorkSessionSum(): SumObj {
        val theTime = getTimeDifferent(startTime = startTime.time, endTime = endTime.time)
        val baseIncome = baseWage * theTime

        return SumObj(
            startTime = startTime,
            endTime = endTime,
            objectName = "Builder data",
            totalTime = theTime,
            baseIncome = baseIncome,
            extraIncome = extras,
            totalIncome = baseIncome + extras,
            delivers = delivers,
            averageIncomePerDelivery1 = (baseIncome)/delivers,averageIncomePerDelivery2 = (extras)/delivers,
            averageIncomePerHour1 = (baseIncome)/theTime,averageIncomePerHour2 = (extras)/theTime, objectType = SumObjectsType.WorkSession,
            averageIncomeSubObj1 = 0f, averageIncomeSubObj2 = 0f,
            averageTimeSubObj = 1f,
        )
    }
}
