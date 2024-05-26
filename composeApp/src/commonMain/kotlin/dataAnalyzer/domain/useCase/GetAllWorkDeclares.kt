package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.repository.Repository
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

/*
GetAllWorkDeclares :
will sum up all of the db data by months from the first month declare until the current time
if there is no data :
* at some specific month we will not add this month sum and skip it , for all data we will add an empty list ...,
this will define error / no data and the vm (UI) will note the user
 */
class GetAllWorkDeclares(private val repository: Repository) {
     suspend operator fun invoke():
           List<MonthSumData>{
        val startMont = repository.getFirstDeclareMonthYear()
        if(startMont!=null){
        var startTime = LocalDate(
            year = startMont.substring(0, 4).toInt(),
            month = Month(startMont.substring(4).toInt()),
            dayOfMonth = 25
        )
        val theResultObjects = mutableListOf<MonthSumData>()
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        while (if (startTime.year == currentTime.year) {
                startTime.month <= currentTime.month
            } else {
                startTime < currentTime
            }
        ) {
            val a = repository.getMonthWorkDeclare(
                "${startTime.year}${startTime.month.number}")
            if (a.isNotEmpty()) {
                theResultObjects.add(
                    MonthSumData(
                        month = "${startTime.year}${startTime.month.number}",
                        data = a
                    )
                )
            }
            startTime = startTime.plus(DatePeriod(months = 1))
        }
        return theResultObjects


    }
       return listOf()
    }

}
data class MonthSumData(
    val month: String,
    val data: List<WorkSumDomain>
)