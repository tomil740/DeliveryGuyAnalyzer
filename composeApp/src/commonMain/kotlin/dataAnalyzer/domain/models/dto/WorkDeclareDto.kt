package dataAnalyzer.domain.models.dto

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class WorkDeclareDto:RealmObject  {
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
