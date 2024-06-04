package dataAnalyzer.domain.useCase.screenUsecases

import dataAnalyzer.domain.models.domain.BaseWageState
import dataAnalyzer.domain.useCase.GetBaseWage
import dataAnalyzer.domain.useCase.InsertWorkDeclare
import dataAnalyzer.domain.useCase.UpdateBaseWage

data class SummariseBuilderUseCases(
    val insertWorkDeclare: InsertWorkDeclare,
    val getBaseWage:GetBaseWage,
    val updateBaseWage: UpdateBaseWage
    )
