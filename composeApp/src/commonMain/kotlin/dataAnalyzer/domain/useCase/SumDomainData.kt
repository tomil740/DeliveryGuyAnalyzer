package dataAnalyzer.domain.useCase


import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

/*
SumDomainData :
will get as parameters the list of working data objects (workSum) and the target type "to make"
then the function summaries all of the object list data into one workSum object and assign the argument data target type to it

* if the data list is empty (to mark no data) , we will return an fake work sum object and to flag it we will assign to the total
  time attribute -1f

this function will return (at this basic version) only Month sum object or all time sum , according to that all of the unnecessary
attributes as start/end time will be assign with fake day and time (1-28) while the date is valid for the needed use
*this is example for the table idea explained at the data sum ...*

 */
class SumDomainData() {

    fun getSummarizesDomainObject(a: List<WorkSum>, objectType: SumObjectsType = SumObjectsType.MonthSum): Deferred<WorkSum> {

        return CoroutineScope(Dispatchers.IO).async {

            var time: Float = 0f
            var deliveries: Int = 0
            var baseIncome: Float = 0f
            var extraIncome: Float = 0f


            if (a.isEmpty()) {
                 WorkSum(
                    startTime = LocalDateTime(
                        LocalDate(
                            year = 2024,
                            month = Month.MARCH,
                            dayOfMonth = 3
                        ), time = LocalTime(12, 12)
                    ),
                    endTime = LocalDateTime(
                        LocalDate(
                            year = 2024,
                            month = Month.MARCH,
                            dayOfMonth = 3
                        ), time = LocalTime(12, 12)
                    ),
                    time = -1f,
                    deliveries = 1,
                    baseIncome = 1f,
                    extraIncome = 1f,
                    subObjects = listOf(),
                    objectsType = objectType
                )
            }else {

                for (i in a) {
                    time += i.time
                    deliveries += i.deliveries
                    baseIncome += i.baseIncome
                    extraIncome += i.extraIncome
                }

                val currentTime = a.first().startTime
                val ab = WorkSum(
                    startTime = LocalDateTime(
                        LocalDate(
                            year = currentTime.year,
                            month = currentTime.month,
                            dayOfMonth = 1
                        ), time = LocalTime(0, 0)
                    ),
                    endTime = LocalDateTime(
                        LocalDate(
                            year = currentTime.year,
                            month = currentTime.month,
                            dayOfMonth = 28
                        ), time = LocalTime(0, 0)
                    ),
                    time = time,
                    deliveries = deliveries,
                    baseIncome = baseIncome,
                    extraIncome = extraIncome,
                    subObjects = a.reversed(),
                    objectsType = objectType
                )

                ab
            }
        }
    }
}