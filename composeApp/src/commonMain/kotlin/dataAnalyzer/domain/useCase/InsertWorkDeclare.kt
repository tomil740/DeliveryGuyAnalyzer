package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.repository.Repository
import kotlinx.datetime.number

/*
InsertWorkDeclare :
get an SummariseBuilderState object , and applty to "functions" on it
* check for legality , need to insure there is no overlapping
* insert the obj as is to the repository function to insert it into the db
 */
class InsertWorkDeclare(private val repository:Repository) {
    suspend operator fun invoke(theDeclare: SummariseBuilderState):Boolean {
        /*
        *as a reference we are using as uniqe primary key the start time + date,which is validating only on edge cases*
         with the matched repository function (by query) we will pull all the matched date declaries by the date
         then we will go over the specific day hours range if there is an overlap with any declare from the "matched" data list
         we will return false (flag overlapping) else we will inset the object...
         */
        val a = "${theDeclare.startTime.year}${theDeclare.startTime.month.number}"
        val matched = repository.getDeclareByDayOfMonthPlusYearAndMonth(dayOfMonth = theDeclare.startTime.dayOfMonth, yearAndMonth = a)

        if(matched.isNotEmpty()){
            val objectEndTime = theDeclare.endTime.time
            val sTime =theDeclare.startTime.time
            val eTime = if(objectEndTime <= sTime){objectEndTime.hour+24}else{objectEndTime.hour}
           for (i in matched){
               val sTimeArgu = i.startTime.hour
               val eTimeArgu = if(i.endTime.time <= i.startTime.time){i.endTime.hour+24}else{i.endTime.hour}

               if (sTimeArgu >= eTime || eTimeArgu <= sTime.hour){
                  // return true
               }
               else if(sTimeArgu >= sTime.hour || eTimeArgu <= eTime){
                   return false
               }
           }
        }
        return repository.insertWorkDeclare(theDeclare)
    }
}