package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.model.WeekSum
import com.example.mywork120.domain.repository.DeclareRepository
import kotlinx.coroutines.flow.Flow

class GetWeekSumFlow(private val repository: DeclareRepository) {

    suspend operator fun invoke (): Flow<List<WeekSum>> {
        return repository.getAllWeekSumFlow()
    }

}