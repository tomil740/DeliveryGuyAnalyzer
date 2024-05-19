package dataAnalyzer.data.repository

import dataAnalyzer.data.localDb.MongoDB
import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val mongoDB: MongoDB):Repository {
    override suspend fun insertWorkDeclare(workDeclareDto: WorkDeclareDto):Boolean {
        return mongoDB.addDeclare(workDeclareDto)
    }

    override fun getFirstDeclareMonthYear(): String? {
        return mongoDB.getFirstDeclare()
    }

    override fun getMonthWorkDeclare(yearMonth:String): Flow<List<WorkSumDomain>> {
        return mongoDB.getMonthSum(yearMonth)
    }

    override fun getDeclareByDayOfMonth(dayOfMonth: Int): List<WorkSumDomain> {
        return mongoDB.getDeclareByDayOfMonth(dayOfMonth)
    }

}

