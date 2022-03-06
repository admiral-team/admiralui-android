package com.admiral.demo.features.home.gun.domain.useCase

import com.admiral.demo.features.home.gun.domain.model.Template

interface IGetTemplateUseCase {
    suspend fun execute(id: String): Template
}