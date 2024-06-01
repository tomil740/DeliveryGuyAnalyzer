package dataAnalyzer.domain.models.builder

import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
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
   /*
    fun toWorkDeclareDto(): WorkDeclareData {
      return  WorkDeclareData().apply {
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

    */
/*
todo need to make shure this is not useable at all
I think that was used in some tenetive soulotion to use this data before I adjust (or delete for MVP) use the matched functions

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

 */

    /*
    this map our stright data from the user into the matched presentable object to the UI functions
     */
    fun toSumObjDomain(): SumObjDomain {
        val theTime = getTimeDifferent(startTime = startTime.time, endTime = endTime.time)
        val baseIncome = baseWage * theTime

        return SumObjDomain(
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
