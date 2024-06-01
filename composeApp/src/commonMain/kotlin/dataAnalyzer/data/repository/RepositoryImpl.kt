package dataAnalyzer.data.repository

import dataAnalyzer.data.dataTables.mapers.summariseBuilderStateToWorkDeclareDto
import dataAnalyzer.data.localDb.MongoDB
import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.data.dataTables.toWorkSumDomain
import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.repository.Repository
import kotlinx.coroutines.delay


/*
Repository
The repository job in general is to unit different data sources togther , here we only have one source but I decided
to use it as a structure frame to build upon at the feature
according to the repository pattern I implemnted the dependency inverison method with this class in order of keping the seprations of
layers and keep the code reliable as possiable
 */
class RepositoryImpl(private val mongoDB: MongoDB):Repository {
    override suspend fun insertWorkDeclare(workDeclareData: SummariseBuilderState):Boolean {
        return mongoDB.addDeclare(summariseBuilderStateToWorkDeclareDto(workDeclareData))
    }

    override fun getFirstDeclareMonthYear(): String? {
        return mongoDB.getFirstDeclare()
    }

    override fun getMonthWorkDeclare(yearMonth:String): List<WorkSum> {
        return mongoDB.getMonthSum(yearMonth).map { it.toWorkSumDomain() }
    }

    override fun getDeclareByDayOfMonthPlusYearAndMonth(dayOfMonth: Int, yearAndMonth: String): List<WorkSum> {
        return mongoDB.getDeclareByDayOfMonth(dayOfMonth = dayOfMonth, yearAndMonth = yearAndMonth).map { it.toWorkSumDomain() }
    }

}

