package dataAnalyzer.data.localDb

import dataAnalyzer.data.dataTables.WorkDeclareData
import dataAnalyzer.domain.models.domain.BaseWageState
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find

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
                schema = setOf(WorkDeclareData::class,BaseWageState::class)
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
            ?.sort(property = "dayOfMonth")
            ?.find()?.toList() ?: listOf()
        }

    //base wage function
    suspend fun updateBaseWage(state:Int){
        realm?.write {
             try {
                val obj = query<BaseWageState>("key == $0", 0).find().first()
                obj.baseWageState = state
            }catch (e:Exception){
                copyToRealm(BaseWageState().apply {
                    baseWageState = 45
                })
            }
        }
    }

    fun getBaseWage():Int{
       return try {
            realm?.query<BaseWageState>("key == $0", 0)?.find()?.first()?.baseWageState ?: 30
        }catch (e:Exception){
            35
        }
    }

}



