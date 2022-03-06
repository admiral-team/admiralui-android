package com.admiral.demo.features.home.gun.data.service

import com.admiral.demo.features.home.gun.data.model.TemplateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GunService {
    object Urls {
        const val TEMPLATE = "api/template/{id}"
    }

    @GET(Urls.TEMPLATE)
    suspend fun getTemplate(@Path("id") id: String): TemplateResponse
}