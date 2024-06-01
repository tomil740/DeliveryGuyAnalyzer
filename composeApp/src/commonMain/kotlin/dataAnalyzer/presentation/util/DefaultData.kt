package dataAnalyzer.presentation.util


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dataAnalyzer.domain.models.models.SumObjDomain
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
     var comparedObj: MutableStateFlow<SumObjDomain> =

        MutableStateFlow(
            SumObjDomain(
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
                averageIncomePerDelivery1 = (getTimeDifferent(
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
                averageIncomePerDelivery2 = (getTimeDifferent(
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
                averageIncomePerHour1 = (getTimeDifferent(
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
                averageIncomePerHour2 = (getTimeDifferent(
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
                averageIncomeSubObj1 = 4f,
                averageIncomeSubObj2 = 2f,
                objectType = SumObjectsType.AllTimeSum,
                objectName = "",
                averageTimeSubObj = 5f,
            )
        )

     var valuedObj: MutableStateFlow<SumObjDomain> =
        MutableStateFlow(
            SumObjDomain(
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
                averageIncomePerDelivery1 = (getTimeDifferent(
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
                averageIncomePerHour1 = (getTimeDifferent(
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
                averageIncomeSubObj1 = 4f,
                averageIncomeSubObj2 = 3f,
                averageIncomePerDelivery2 =4f,
                averageIncomePerHour2 = 5f,
                objectType = SumObjectsType.MonthSum,
                objectName = "",
                averageTimeSubObj = 7f,
            )
        )
    @Composable
    fun getMainColors(): ObjectItemColors {
      return ObjectItemColors(
            barColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimary,
          valueColor = MaterialTheme.colorScheme.primaryContainer,
          value2Color = MaterialTheme.colorScheme.surface,
          mainIconColor = Color.Green,
          subIconColor = Color.Blue
            )
    }
    @Composable
    fun getSecondaryColors(): ObjectItemColors {
        return ObjectItemColors(
            barColor = MaterialTheme.colorScheme.onSecondary,
            textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            valueColor = MaterialTheme.colorScheme.secondary,
            value2Color = MaterialTheme.colorScheme.error,
            mainIconColor = Color(0xFF1B5E20),
            subIconColor = Color(0xFF1A237E)

        )
    }

    fun getComparableMainValue(sumType:SumObjectsType, value : Float):ComparableData{
        when(sumType){
            SumObjectsType.WorkSession -> {
                return if (value >=1000){
                    ComparableData(2000,true)
                }else{
                    ComparableData(1000,false)
                }
            }
            SumObjectsType.MonthSum -> {
                return if (value >= 12000){
                    ComparableData(25000,true)
                }else{
                    ComparableData(12000,false)
                }
            }
            SumObjectsType.AllTimeSum -> {
                return if (value >= 140000){
                    ComparableData(200000,true)
                }else{
                    ComparableData(140000,false)
                }
            }
        }
    }
    fun getComparableSubValue(sumType:SumObjectsType, value : Float):ComparableData{
        when(sumType){
            SumObjectsType.WorkSession -> {
                return if (value >=10){
                    ComparableData(18,true)
                }else{
                    ComparableData(10,false)
                }
            }
            SumObjectsType.MonthSum -> {
                return if (value >= 200){
                    ComparableData(500,true)
                }else{
                    ComparableData(200,false)
                }
            }
            SumObjectsType.AllTimeSum -> {
                return if (value >= 2400){
                    ComparableData(5000,true)
                }else{
                    ComparableData(2400,false)
                }
            }
        }
    }

    fun getComparablePerHour(value : Float):ComparableData{
        return if (value >=80){
            ComparableData(250,true)
        }else{
            ComparableData(80,false)
        }
    }

    fun getComparablePerDelivery(value : Float):ComparableData{
        return if (value >=40){
            ComparableData(120,true)
        }else{
            ComparableData(40,false)
        }
    }


}

data class ObjectItemColors(
    val barColor : Color ,
    val textColor:Color,
    val mainIconColor:Color,
    val subIconColor:Color,
    val valueColor : Color,
    val value2Color : Color
)

data class ComparableData(
    val theVal:Int,
    val isException:Boolean
)


/*
declare builder vm fake comprqable obj
SumObjDomain(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30), eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30),
        totalTime = getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        platform = "Wolt", baseIncome = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f)
        , extraIncome = 300f, totalIncome = getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f
        , delivers = 35, averageIncomePerDelivery = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/35f,
        averageIncomePerHour = (getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time)*35f+300f)/
                getTimeDifferent(sTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 5,17,30).time, eTime =  LocalDateTime(year = 2024, month = Month.APRIL, dayOfMonth = 6,3,30).time),
        objectType = SumObjectsType.WorkSession, shiftType = null, averageIncomeSubObj = 5f, objectName = "w", subObjName = "", averageTimeSubObj = 5f,sumObjectSourceType = SumObjectSourceType.Archive
    )
 */