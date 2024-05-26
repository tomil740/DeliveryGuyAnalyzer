package dataAnalyzer.data.localDb

import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.models.dto.mapers.workDeclareDtoToWorkSumDomain
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MongoDB {
    private var realm: Realm? = null

    init {
        configureTheRealm()
    }

    private fun configureTheRealm() {
        if (realm == null || realm!!.isClosed()) {
            val config = RealmConfiguration.Builder(
                schema = setOf(WorkDeclareDto::class)
            )
                .compactOnLaunch()
                .build()
            realm = Realm.open(config)
        }
    }

    suspend fun addDeclare(task: WorkDeclareDto):Boolean{
        val a = realm?.write { copyToRealm(task) }
        return a?.isValid() ?: false
    }

    fun getDeclareByDayOfMonth(dayOfMonth:Int):List<WorkSumDomain> {
         val a = realm?.query<WorkDeclareDto>("dayOfMonth == $0",dayOfMonth)
            ?.find()?.toList()
        return if (!a.isNullOrEmpty()) {
           a.map { workDeclareDtoToWorkSumDomain(it)}
        }else{
            listOf()
        }
    }

    fun getFirstDeclare(): String? {
       return realm?.query<WorkDeclareDto>()?.sort(property = "yearAndMonth")?.first()
           ?.find()?.yearAndMonth
    }

    fun getMonthSum(theMonth:String): List<WorkSumDomain> {
        return realm?.query<WorkDeclareDto>(query = "yearAndMonth == $0", theMonth)
            ?.find()?.toList()?.map{ workDeclareDtoToWorkSumDomain(it)} ?: listOf()
        }

}



