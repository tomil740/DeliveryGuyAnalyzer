package dataAnalyzer.domain.models.domain


import dataAnalyzer.domain.models.models.SumObjDomain
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getSumObjectHeader
import kotlinx.datetime.LocalDateTime
/*
WorkSum:
represent the matched domain object of our workDeclareData data object , this object target is
* be an model object that can be use instand of our data object at the domain and presentation
* save us code by applying basic conversion on this object compared to the data object that cant implement them because it have to fit our db itSelf
 */
data class WorkSum(
    val objectsType: SumObjectsType,
    val startTime : LocalDateTime,
    val endTime : LocalDateTime,
    val time : Float,
    val deliveries : Int,
    val baseIncome : Float,
    val extraIncome : Float,
    val subObjects: List<WorkSum>
){
    //regular basic map function to the domain object from that data
    //the argument of the sumobj type will be used to convert an list of summarise object of some time frame for example
    fun toSumObjDomain(theObjectsType: SumObjectsType?=null): SumObjDomain {

        var theType = objectsType

        if(theObjectsType!=null){
            theType = theObjectsType
        }

        return SumObjDomain(
            objectName= getSumObjectHeader(objectsType,startTime),
            startTime= startTime,
            endTime=endTime,
            totalTime=time,
            totalIncome=baseIncome+extraIncome,
           baseIncome=baseIncome,
      extraIncome=extraIncome,
        delivers=deliveries,
        averageIncomePerHour1 = (baseIncome)/time ,
            averageIncomePerHour2 =(extraIncome)/time,
            averageIncomePerDelivery1 = baseIncome/deliveries,
            averageIncomePerDelivery2 = extraIncome/deliveries,
            averageIncomeSubObj1 = if(subObjects.isNotEmpty()){baseIncome/subObjects.size}else{0f},
            averageIncomeSubObj2 = if(subObjects.isNotEmpty()){extraIncome/subObjects.size}else{0f},
            subObjects = subObjects.map { it.toSumObjDomain() },
            averageTimeSubObj = time/subObjects.size,
            objectType =theType)
    }
}
