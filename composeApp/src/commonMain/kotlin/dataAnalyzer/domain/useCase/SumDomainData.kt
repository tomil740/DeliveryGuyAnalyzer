package dataAnalyzer.domain.useCase


import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

class SumDomainData() {

    fun getSummarizesDomainObject(a: List<WorkSumDomain>, objectType: SumObjectsType = SumObjectsType.MonthSum, workingPlat:String = "Any"): WorkSumDomain {
        var time: Float = 0f
        var deliveries: Int = 0
        var baseIncome: Float = 0f
        var extraIncome: Float = 0f


        if(a.isEmpty()){
          return  WorkSumDomain(
                startTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                endTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                time = -1f,
                 deliveries = 1,
                baseIncome = 1f,
                extraIncome = 1f,
                subObjects = listOf(),
              objectsType = objectType
            )
        }

        for (i in a) {
            time += i.time
            deliveries += i.deliveries
            baseIncome += i.baseIncome
            extraIncome += i.extraIncome
        }

        val currentTime = a.first().startTime
        val ab = WorkSumDomain(
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



        return ab

    }
/*
    fun getAverageDomainObject(a: List<WorkSumDomain>,objectType:String = SumObjectsType.MonthSum.name,workingPlat: String=""): WorkSumDomain {
        var time: Float = 0f
        var deliveries: Int = 0
        var baseIncome: Float = 0f
        var extraIncome: Float = 0f
        val dataPerHour = mutableListOf<DataPerHourDomain>()
        val shifts = mutableListOf<ShiftDomain>()

        if(a.isEmpty()){
            return  WorkSumDomain(
                sTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                eTime = LocalDateTime(LocalDate(year =2024, month = Month.MARCH, dayOfMonth = 3), time = LocalTime(12,12)),
                time = -1f,
                deliveries =1,
                baseIncome = 1f,
                extraIncome = 1f,
                yearAndMonth ="22222",
                dayOfMonth = 1,
                workingPlatform = workingPlat ?: "",
                workPerHour = listOf(),
                shifts = listOf(),
                objectsType = SumObjectsType.MonthSum,
                subObjects = listOf()
            )
        }
        val theObjectType = isShiftSession(objectType)
        var workingPlatform = a[0].workingPlatform
        //for shift time frame data ...
        var sTime=LocalTime(0, 0)
        var eTime =  LocalTime(23, 0)
        if (theObjectType==SumObjectsType.ShiftSession){
            sTime = a.first().sTime.time
            eTime = a.first().eTime.time
        }
        for (i in a) {

            if(i.sTime.time<sTime)
                sTime = i.sTime.time

            if(i.eTime.time > eTime)
                eTime = i.eTime.time

            if(i.workingPlatform !=workingPlatform)
                workingPlatform = "Any"

            time += i.time
            deliveries += i.deliveries
            baseIncome += i.baseIncome
            extraIncome += i.extraIncome
            //calculate dat PerHour
            for (j in i.workPerHour) {
                dataPerHour.add(j)
            }
            for (k in i.shifts)
                shifts.add(k)
        }


        val currentTime = a[0].sTime
        val ab = WorkSumDomain(
            sTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 1
                ), time = sTime
            ),
            eTime = LocalDateTime(
                LocalDate(
                    year = currentTime.year,
                    month = currentTime.month,
                    dayOfMonth = 1
                ), time =eTime
            ),
            time = time / a.size,
            deliveries = deliveries / a.size,
            baseIncome = baseIncome / a.size,
            extraIncome = extraIncome / a.size,
            yearAndMonth = a.get(0).yearAndMonth,
            dayOfMonth = 1,
            workingPlatform = workingPlatform,
            workPerHour = getDataPerHourSum(dataPerHour,sTime=sTime.hour,eTime=eTime.hour),
            shifts = shifts,
            objectsType = theObjectType,
            shiftType = if(theObjectType.name != objectType){objectType}else{null},
            subObjects = a
        )



        return ab

    }

 */
}