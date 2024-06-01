package dataAnalyzer.domain.useCase

import androidx.compose.ui.text.font.FontLoadingStrategy.Companion.Async
import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
      operator fun invoke(): Deferred<List<MonthSumData>>{
          val theResultObjects = mutableListOf<MonthSumData>()

          return CoroutineScope(Dispatchers.IO).async {
             /*
         suing an repository helper function to get the first declare month , which from it we will start pulling data
         ,if its null the db is empty and we will return empty list to flag that
          */
             val startMont = repository.getFirstDeclareMonthYear()
             if (startMont != null) {
                 //if there is first declare we will take his month and start running on every month from it to the current date
                 var startTime = LocalDate(
                     year = startMont.substring(0, 4).toInt(),
                     month = Month(startMont.substring(4).toInt()),
                     dayOfMonth = 25
                 )
                 val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
                 while (if (startTime.year == currentTime.year) {
                         startTime.month <= currentTime.month
                     } else {
                         startTime < currentTime
                     }
                 ) {
                     //pull with the matched repository function the matched data( with a query ) according to our month...
                     val a = repository.getMonthWorkDeclare(
                         "${startTime.year}${startTime.month.number}"
                     )
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

             }
              theResultObjects
         }

    }
}

//an "special object" used to described each month data by adding string header with the matched month to each month data objects
data class MonthSumData(
    val month: String,
    val data: List<WorkSum>
)