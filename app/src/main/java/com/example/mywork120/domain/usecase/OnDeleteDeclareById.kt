package com.example.mywork120.domain.usecase

import com.example.mywork120.domain.repository.DeclareRepository

class OnDeleteDeclareById(private val repository: DeclareRepository) {

    suspend operator fun invoke(theId:Int){
        repository.deleteDeclareById(theId)
    }

}