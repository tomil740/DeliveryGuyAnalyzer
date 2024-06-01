package dataAnalyzer.data.dataTables

import dataAnalyzer.domain.models.domain.WorkSum
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.LocalDateTime


/*
In general this table could be use as an domain table , by that saving the work of using another table
but I decided to use another table anyWay to some basic businsse logic that is need for our time and etc
because of that the frame states as with Sql but in noSql db...
 */
class WorkDeclareData:RealmObject  {
    @PrimaryKey
    var sTime : String = ""
    var eTime : String = ""
    var time : Float = 0f
    var deliveries : Int = 0
    var baseIncome : Float = 0f
    var extraIncome : Float = 0f
    var yearAndMonth : String = ""//to sort the data per month esayl
    var dayOfMonth: Int = 0
}

fun WorkDeclareData.toWorkSumDomain(): WorkSum {
    val sTime = LocalDateTime.parse(this.sTime)
    val eTime = LocalDateTime.parse(this.eTime)


    return WorkSum(
        objectsType = SumObjectsType.WorkSession,
        startTime= sTime,
        endTime= eTime,
        baseIncome= this.baseIncome,
        extraIncome=this.extraIncome,
        subObjects = listOf(),
        deliveries = this.deliveries,
        time = this.time
    )

}
