package dataAnalyzer.domain.models.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class BaseWageState(): RealmObject {
    @PrimaryKey
    var key:Int = 0
    var baseWageState:Int = 35
}

