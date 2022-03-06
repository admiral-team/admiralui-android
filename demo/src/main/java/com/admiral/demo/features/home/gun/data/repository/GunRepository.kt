package com.admiral.demo.features.home.gun.data.repository

import com.admiral.demo.features.home.gun.data.service.GunService
import com.admiral.demo.features.home.gun.data.model.Template

class GunRepository(
    private val gunService: GunService
) : IGunRepository {
    override suspend fun getTemplate(id: String): Template {
        return gunService.getTemplate(id).template ?: throw Exception("Template is null")
    }
}