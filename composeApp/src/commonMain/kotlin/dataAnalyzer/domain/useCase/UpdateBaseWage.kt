package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.repository.Repository

class UpdateBaseWage(val repository: Repository) {
    suspend operator fun invoke(baseWage:Int){
        repository.updateBaseWage(baseWage)
    }
}