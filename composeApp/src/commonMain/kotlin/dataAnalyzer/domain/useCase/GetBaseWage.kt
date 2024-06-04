package dataAnalyzer.domain.useCase

import dataAnalyzer.domain.repository.Repository

class GetBaseWage(val repository: Repository) {
    operator fun invoke():Int{
        return repository.getBaseWage()
    }
}