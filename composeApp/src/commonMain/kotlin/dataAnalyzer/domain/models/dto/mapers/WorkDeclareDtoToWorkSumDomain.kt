package dataAnalyzer.domain.models.dto.mapers

import dataAnalyzer.domain.models.domain.WorkSumDomain
import dataAnalyzer.domain.models.dto.WorkDeclareDto
import dataAnalyzer.domain.models.util.closeTypesCollections.SumObjectsType
import kotlinx.datetime.LocalDateTime


fun workDeclareDtoToWorkSumDomain(workDeclareDto: WorkDeclareDto):WorkSumDomain{

    val sTime = LocalDateTime.parse(workDeclareDto.sTime)
    val eTime = LocalDateTime.parse(workDeclareDto.eTime)


    return WorkSumDomain(
        objectsType = SumObjectsType.WorkSession,
        startTime= sTime,
        endTime= eTime,
        baseIncome=workDeclareDto.baseIncome,
        extraIncome=workDeclareDto.extraIncome,
        subObjects = listOf(),
        deliveries = workDeclareDto.deliveries,
        time = workDeclareDto.time

    )
}