package dataAnalyzer.domain.repository

import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.domain.WorkSum


/*
the repository at the domain level is an contract between the layers that make sure all of the data demands will be
filled at compile time ...
 */
interface Repository {

    suspend fun insertWorkDeclare(workDeclareData: SummariseBuilderState):Boolean

    fun getFirstDeclareMonthYear():String?

    fun getMonthWorkDeclare(yearMonth:String):List<WorkSum>

    fun getDeclareByDayOfMonthPlusYearAndMonth(dayOfMonth: Int, yearAndMonth: String):List<WorkSum>
    fun getBaseWage():Int
    suspend fun updateBaseWage(baseWage:Int)

}