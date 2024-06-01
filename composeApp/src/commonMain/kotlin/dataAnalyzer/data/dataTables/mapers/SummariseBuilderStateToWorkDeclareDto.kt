package dataAnalyzer.data.dataTables.mapers

import dataAnalyzer.data.dataTables.WorkDeclareData
import dataAnalyzer.domain.models.builder.SummariseBuilderState
import kotlinx.datetime.number

/*
   mapper to the workDeclareDto which is our builder "table"
   There is no problem making this conversion at the domain level because we are using relam no SQL db
   that means the table used for us the db dosnt use it at all...
    */
fun summariseBuilderStateToWorkDeclareDto(theObj:SummariseBuilderState):WorkDeclareData{
    return  WorkDeclareData().apply {
        sTime = theObj.startTime.toString()
        eTime = theObj.endTime.toString()
        baseIncome = (theObj.baseWage*theObj.totalTime)
        extraIncome = theObj.extras
        time = theObj.totalTime
        deliveries = theObj.delivers
        yearAndMonth = "${theObj.startTime.year}${theObj.startTime.month.number}"
        dayOfMonth = theObj.startTime.dayOfMonth
    }
}