package dataAnalyzer.presentation.objectItemScreen.util


import dataAnalyzer.domain.models.models.SumObj
import dataAnalyzer.domain.models.models.SumObjectInterface
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import dataAnalyzer.domain.models.util.helperFun.getTimeDifferent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

class DefaultData() {
    /*
   comparedObj :
   an component of the uiState object , will be an workSession sum (for default value only) , this object is just an simple helper value for deffualt initalization
    */
     var comparedObj: MutableStateFlow<SumObjectInterface> =

        MutableStateFlow(
            SumObj(
                startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5, 17, 30),
                endTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6, 3, 30),
                totalTime = getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ),
                baseIncome = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f),
                extraIncome = 300f,
                totalIncome = 6f,
                delivers = 35,
                averageIncomePerDelivery = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) / 35f,
                averageIncomePerHour = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) /
                        getTimeDifferent(
                            startTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 5,
                                17,
                                30
                            ).time,
                            endTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 6,
                                3,
                                30
                            ).time
                        ),
                averageIncomeSubObj = 4f,
                objectType = SumObjectsType.AllTimeSum,
                objectName = "",
                averageTimeSubObj = 5f,
            )
        )

     var valuedObj: MutableStateFlow<SumObjectInterface> =
        MutableStateFlow(
            SumObj(
                startTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5, 17, 30),
                endTime = LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6, 3, 30),
                totalTime = getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ),
                baseIncome = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f),
                extraIncome = 300f,
                totalIncome = 6f,
                delivers = 35,
                averageIncomePerDelivery = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) / 35f,
                averageIncomePerHour = (getTimeDifferent(
                    startTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 5,
                        17,
                        30
                    ).time,
                    endTime = LocalDateTime(
                        year = 2024,
                        month = Month.APRIL,
                        dayOfMonth = 6,
                        3,
                        30
                    ).time
                ) * 35f + 300f) /
                        getTimeDifferent(
                            startTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 5,
                                17,
                                30
                            ).time,
                            endTime = LocalDateTime(
                                year = 2024,
                                month = Month.APRIL,
                                dayOfMonth = 6,
                                3,
                                30
                            ).time
                        ),
                averageIncomeSubObj = 4f,
                objectType = SumObjectsType.AllTimeSum,
                objectName = "",
                averageTimeSubObj = 7f,
            )
        )
}

/*
declare builder vm fake comprqable obj
SumObj(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f)
        , extraIncome = 300f, totalIncome = getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/
                getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        objectType = SumObjectsType.WorkSession, shiftType = null, averageIncomeSubObj = 5f, objectName = "w", subObjName = "", averageTimeSubObj = 5f,sumObjectSourceType = SumObjectSourceType.Archive
    )
 */