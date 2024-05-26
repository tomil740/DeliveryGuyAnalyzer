package dataAnalyzer.domain.repository

import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.dto.WorkDeclareDto
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertWorkDeclare(workDeclareDto: WorkDeclareDto):Boolean

    fun getFirstDeclareMonthYear():String?

    fun getMonthWorkDeclare(yearMonth:String):List<WorkSumDomain>

    fun getDeclareByDayOfMonth(dayOfMonth:Int):List<WorkSumDomain>

}