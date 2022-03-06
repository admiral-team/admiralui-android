package com.admiral.demo.features.home.gun.domain.model

data class Template(
    val id: String,
    val description: String,
    val items: List<Item>
)