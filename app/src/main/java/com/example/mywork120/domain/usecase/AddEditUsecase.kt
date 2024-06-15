package com.example.mywork120.domain.usecase

data class AddEditUsecase (
    val onlegalityCheck:OnlegalityCheck,
    val onSubmitDeclare: OnSubmitDeclare,
    val getDeclareById: GetDeclareById,
    val onDeleteDeclareById:OnDeleteDeclareById

)