package com.admiral.demo.features.home.gun.domain.useCase

import com.admiral.demo.features.home.gun.data.repository.IGunRepository
import com.admiral.demo.features.home.gun.domain.model.Template
import com.admiral.demo.features.home.gun.domain.toTemplateDomain
import kotlinx.coroutines.delay

class GetTemplateUseCase(
    private val repository: IGunRepository
) : IGetTemplateUseCase {
    override suspend fun execute(id: String): Template {
        delay(DELAY_IN_MILLISECONDS)
        return repository.getTemplate(id).toTemplateDomain()
    }

    private companion object {
        const val DELAY_IN_MILLISECONDS = 800L
    }
}