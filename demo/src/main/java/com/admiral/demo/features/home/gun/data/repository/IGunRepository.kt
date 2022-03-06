package com.admiral.demo.features.home.gun.data.repository

import com.admiral.demo.features.home.gun.data.model.Template

interface IGunRepository {
    suspend fun getTemplate(id: String): Template
}