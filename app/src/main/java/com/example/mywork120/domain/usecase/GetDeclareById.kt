package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.model.Declare
import com.example.mywork120.domain.repository.DeclareRepository

class GetDeclareById(private val repository: DeclareRepository) {

    suspend operator fun invoke(theId : Int):Declare{
       return repository.getDeclareById(theId)

    }

}