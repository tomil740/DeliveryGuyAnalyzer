package dataAnalyzer.data.dataTables.mapers

import dataAnalyzer.data.dataTables.WorkDeclareData
import dataAnalyzer.domain.models.builder.SummariseBuilderState
import dataAnalyzer.domain.models.models.SumObjDomain
import kotlinx.datetime.number


//todo there is an chance this is useless and need to be erase
/*
   mapper to the workDeclareDto which is our builder "table"
   There is no problem making this conversion at the domain level because we are using relam no SQL db
   that means the table used for us the db dosnt use it at all...
    */
fun sumObjDomainToWorkDeclareData(theObj: SumObjDomain): WorkDeclareData {
    return  WorkDeclareData().apply {
        sTime = theObj.startTime.toString()
        eTime = theObj.endTime.toString()
        baseIncome = theObj.baseIncome
        extraIncome = theObj.extraIncome
        time = theObj.totalTime
        deliveries = theObj.delivers
        yearAndMonth = "${theObj.startTime.year}${theObj.startTime.month.number}"
        dayOfMonth = theObj.startTime.dayOfMonth
    }
}