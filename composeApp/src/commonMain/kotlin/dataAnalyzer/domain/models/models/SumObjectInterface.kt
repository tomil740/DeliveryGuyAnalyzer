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
    val averageIncomePerHour: Float
    val averageIncomePerDelivery: Float
    val averageIncomeSubObj:Float
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
    override val averageIncomePerHour: Float,
    override val averageIncomePerDelivery: Float,
    override val averageIncomeSubObj:Float,
    override val averageTimeSubObj:Float,
    override val subObjects:List<SumObjectInterface>?=null,
    override val workSessionAmount: Int = 1
):SumObjectInterface