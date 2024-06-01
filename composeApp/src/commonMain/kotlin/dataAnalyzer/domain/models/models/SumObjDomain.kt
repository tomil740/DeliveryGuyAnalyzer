package dataAnalyzer.domain.models.models

import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDateTime


/*
SumObjDomain class representing the actual domain data , that is the target data to get from all of the other version ...
* this object is a bit versital because I found that its make more sense to include all of the use cases in one object while we
  differ between them with the empty attributes that been used as a flag to mark which object taha is and save us from a lot of blue print
  code if I will go with the obvious option of interface and matched sub object to each type...
 */
data class SumObjDomain(
    val objectType : SumObjectsType,
    val objectName: String = "Builder data",
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val totalTime: Float,
    val totalIncome: Float,
    val baseIncome: Float,
    val extraIncome: Float,
    val delivers: Int,
    val subObjects:List<SumObjDomain>?=null,
    val workSessionAmount: Int = 1,
    val averageIncomePerHour1: Float,
    val averageIncomePerHour2: Float,
    val averageIncomePerDelivery1: Float,
    val averageIncomePerDelivery2: Float,
    val averageTimeSubObj:Float = 0f,
    val averageIncomeSubObj1: Float = 0f,
    val averageIncomeSubObj2: Float = 0f
)