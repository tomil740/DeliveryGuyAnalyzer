package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number
//todo this validation check function isnt valid , we can add two declaries with overLapping time frames
/*
InsertWorkDeclare :
get an SummariseBuilderState object , and applty to "functions" on it
* check for legality , need to insure there is no overlapping
* insert the obj as is to the repository function to insert it into the db
 */
class InsertWorkDeclare(private val repository:Repository) {
    suspend operator fun invoke(theDeclare: SummariseBuilderState):Deferred<Boolean> {
        return CoroutineScope(Dispatchers.IO).async {
            val a = isOverLap(
                dataId = "${theDeclare.startTime.year}${theDeclare.startTime.month.number}",
                dayOfMont = theDeclare.startTime.dayOfMonth, objSTime = theDeclare.startTime.time,
                objETime = theDeclare.endTime.time
            ).await()
            if (a) {
                 repository.insertWorkDeclare(theDeclare)
            }else{
                 false
            }
        }
    }

    private suspend fun isOverLap(dataId:String,dayOfMont : Int,objSTime:LocalTime,objETime:LocalTime):Deferred<Boolean>{
        return CoroutineScope(Dispatchers.IO).async {
            /*
        *as a reference we are using as uniqe primary key the start time + date,which is validating only on edge cases*
         with the matched repository function (by query) we will pull all the matched date declaries by the date
         then we will go over the specific day hours range if there is an overlap with any declare from the "matched" data list
         we will return false (flag overlapping) else we will inset the object...
         */
            val matched = repository.getDeclareByDayOfMonthPlusYearAndMonth(dayOfMonth = dayOfMont, yearAndMonth = dataId)

            if(matched.isNotEmpty()){
                /*
                the algoritem is :
                if s/e Time is in the object time frame FALSE
                then if they both isnt the they could overlap only if eTime is after objEtime and stime is befor obj Stime.
                 */
                val sTime = objSTime.hour + (objSTime.minute/60f)
                var eTime = objETime.hour + (objETime.minute/60f)
                if(eTime <= sTime){eTime+=24}
                for (i in matched){
                    val sTimeArgs = i.startTime.time.hour + (i.startTime.time.minute/60f)
                    var eTimeArgs = i.endTime.time.hour + (i.endTime.time.minute/60f)
                    if(eTimeArgs <= sTimeArgs){eTimeArgs+=24}

                    if ((sTimeArgs > sTime && sTimeArgs < eTime) || (eTimeArgs < eTime && eTimeArgs > sTime)){
                         false
                    }
                    else if (sTimeArgs < sTime && eTimeArgs > eTime){
                         false
                    }
                }
                true
            }else {
                true
            }
        }
    }
}