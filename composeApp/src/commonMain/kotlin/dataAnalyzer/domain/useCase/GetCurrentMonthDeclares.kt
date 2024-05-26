package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetCurrentMonthDeclares(val repository: Repository) {
    operator fun invoke(): List<WorkSumDomain> {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val monthId = "${currentTime.year}${currentTime.monthNumber}"

        return repository.getMonthWorkDeclare(monthId)

    }

}