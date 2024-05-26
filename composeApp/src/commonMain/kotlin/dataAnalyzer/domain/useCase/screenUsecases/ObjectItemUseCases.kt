package dataAnalyzer.domain.useCase.screenUsecases

import dataAnalyzer.domain.useCase.GetAllWorkDeclares
import dataAnalyzer.domain.useCase.GetCurrentMonthDeclares
import dataAnalyzer.domain.useCase.SumDomainData

data class ObjectItemUseCases(
    val getAllWorkDeclares: GetAllWorkDeclares,
    val getCurrentMonthDeclares: GetCurrentMonthDeclares,
    val sumDomainData: SumDomainData
)
