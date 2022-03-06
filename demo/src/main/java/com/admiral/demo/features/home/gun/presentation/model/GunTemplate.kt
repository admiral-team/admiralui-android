package com.admiral.demo.features.home.gun.presentation.model

data class GunTemplate(
    val id: String,
    val description: String,
    val items: List<GunItem>
)
