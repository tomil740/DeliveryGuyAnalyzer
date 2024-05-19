package dataAnalyzer.domain.models.domain


import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getSumObjectHeader
import kotlinx.datetime.LocalDateTime

data class WorkSumDomain(
    val objectsType: SumObjectsType,
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val time : Float,
    val deliveries : Int,
    val baseIncome : Float,
    val extraIncome : Float,
    val subObjects: List<WorkSumDomain>
){
    fun toSumObj(theObjectsType: SumObjectsType?=null): SumObj {

        var theType = objectsType

        if(theObjectsType!=null){
            theType = theObjectsType
        }

        return SumObj(
            objectName= getSumObjectHeader(objectsType, shiftType = null,startTime),
            startTime= startTime,
            endTime=endTime,
            totalTime=time,
            totalIncome=baseIncome+extraIncome,
           baseIncome=baseIncome,
      extraIncome=extraIncome,
        delivers=deliveries,
        averageIncomePerHour= (baseIncome+extraIncome)/time ,
       averageIncomePerDelivery=(baseIncome+extraIncome)/deliveries,
            subObjects = subObjects.map { it.toSumObj() },
            averageIncomeSubObj = if(subObjects.isNotEmpty()){baseIncome+extraIncome/subObjects.size}else{1f},
            averageTimeSubObj = time/subObjects.size,
            objectType =theType)
    }
/*
    fun toShiftSum(): SumObj {
        return SumObj(
            objectName = getSumObjectHeader(objectType = SumObjectsType.ShiftSession,shiftType,sTime),
            shiftType=shiftType,
            sTime= sTime,
            eTime=eTime,
            totalTime = time,
            extraIncome = extraIncome,
            baseIncome =baseIncome,
            delivers = deliveries,
            averageIncomePerHour = (baseIncome+extraIncome)/time,
            averageIncomePerDelivery = (baseIncome+extraIncome)/deliveries,
            workPerHour = GraphState(ogLst = getIncomeDataPerHour(workPerHour), listStartTime = sTime.hour, listEndTime = eTime.hour),
            incomePerSpecificHour = GraphState(ogLst = getWorkDataPerHour(workPerHour), listStartTime = sTime.hour, listEndTime = eTime.hour),
            averageIncomeSubObj = 5f,
            platform = workingPlatformId,
            objectType = SumObjectsType.ShiftSession,
            totalIncome = baseIncome+extraIncome,
            subObjName = "Shift",
            subObjects = null,
            shiftsSumByType = null

        )
    }



 */
}
