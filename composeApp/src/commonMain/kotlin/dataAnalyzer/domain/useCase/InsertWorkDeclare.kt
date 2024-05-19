package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.repository.Repository
import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration.Companion.hours

class InsertWorkDeclare(val repository:Repository) {
    suspend operator fun invoke(theDeclare: WorkDeclareDto):Boolean {
        val matched = repository.getDeclareByDayOfMonth(theDeclare.dayOfMonth)
        if(matched.isNotEmpty()){
            val objectEndTime = LocalDateTime.parse(theDeclare.eTime).time
            val sTime = LocalDateTime.parse(theDeclare.sTime).time
            val eTime = if(objectEndTime <= sTime){objectEndTime.hour+24}else{objectEndTime.hour}
           for (i in matched){
               val sTimeArgu = i.startTime.hour
               val eTimeArgu = if(i.endTime.time <= i.startTime.time){i.endTime.hour+24}else{i.endTime.hour}

               if (sTimeArgu >= eTime || eTimeArgu <= sTime.hour){
                   return true
               }
               else if(sTimeArgu >= sTime.hour || eTimeArgu <= eTime){
                   return false
               }
           }
        }
        return repository.insertWorkDeclare(theDeclare)
    }
}