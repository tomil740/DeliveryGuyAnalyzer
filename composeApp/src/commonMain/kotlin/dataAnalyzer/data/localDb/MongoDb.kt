package dataAnalyzer.data.localDb

import dataAnalyzer.data.dataTables.WorkDeclareData
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query

/*
This is the actual db , and at this phase preaty mauch the all data layer
* initalize our db here
* includes all the need functions on the db data , queries and etc ...
 */
class MongoDB {
    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    private fun configureTheRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(WorkDeclareData::class)
            )
                .compactOnLaunch()
                .build()
            realm = Realm.open(config)
        }
    }

    suspend fun addDeclare(task: WorkDeclareData):Boolean{
        val a = realm?.write { copyToRealm(task) }
        return a?.isValid() ?: false
    }

    fun getDeclareByDayOfMonth(dayOfMonth:Int,yearAndMonth:String):List<WorkDeclareData> {
         val a = realm?.query<WorkDeclareData>("dayOfMonth == $0 AND yearAndMonth == $1",dayOfMonth , yearAndMonth)
            ?.find()?.toList()
        return a ?: listOf()
    }

    fun getFirstDeclare(): String? {
       return realm?.query<WorkDeclareData>()?.sort(property = "yearAndMonth")?.first()
           ?.find()?.yearAndMonth
    }

    fun getMonthSum(theMonth:String): List<WorkDeclareData> {
        return realm?.query<WorkDeclareData>(query = "yearAndMonth == $0", theMonth)
            ?.find()?.toList() ?: listOf()
        }

}



