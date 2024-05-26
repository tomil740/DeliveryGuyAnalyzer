package dataAnalyzer.domain.models.models

import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDateTime

interface SumObjectInterface {
    val objectType : SumObjectsType
    val objectName: String
    val startTime: LocalDateTime
    val endTime: LocalDateTime
    val totalTime: Float
    val totalIncome: Float
    val baseIncome: Float
    val extraIncome: Float
    val delivers: Int
    val averageIncomePerHour1: Float
    val averageIncomePerHour2: Float
    val averageIncomePerDelivery1: Float
    val averageIncomePerDelivery2: Float
    val averageIncomeSubObj1:Float
    val averageIncomeSubObj2:Float
    val averageTimeSubObj:Float
    val subObjects:List<SumObjectInterface>?
    val workSessionAmount:Int
}

data class SumObj(
    override val objectType : SumObjectsType,
    override val objectName: String,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    override val totalTime: Float,
    override val totalIncome: Float,
    override val baseIncome: Float,
    override val extraIncome: Float,
    override val delivers: Int,
    override val averageTimeSubObj:Float,
    override val subObjects:List<SumObjectInterface>?=null,
    override val workSessionAmount: Int = 1,
    override val averageIncomePerHour1: Float,
    override val averageIncomePerHour2: Float,
    override val averageIncomePerDelivery1: Float,
    override val averageIncomePerDelivery2: Float,
    override val averageIncomeSubObj1: Float,
    override val averageIncomeSubObj2: Float
):SumObjectInterface