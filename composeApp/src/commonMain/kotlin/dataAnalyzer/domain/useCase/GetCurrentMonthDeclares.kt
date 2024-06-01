package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.domain.repository.Repository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


/*
GetCurrentMonthDeclares
this is pretty simple use case, we used the repository query method to pull the matched data according to the current data
and returned the function outcome(if there isnt data we will return empty list)
 */
class GetCurrentMonthDeclares(val repository: Repository) {
    operator fun invoke(): List<WorkSum> {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val monthId = "${currentTime.year}${currentTime.monthNumber}"

        return repository.getMonthWorkDeclare(monthId)

    }

}