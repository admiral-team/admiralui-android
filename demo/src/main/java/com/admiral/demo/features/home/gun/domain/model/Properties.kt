package com.admiral.demo.features.home.gun.domain.model

data class Properties(
    val isEnabled: Boolean? = null,
    val text: String? = null,
    val size: Int? = null,
    val isLoading: Boolean? = null,
    val isIconEnabled: Boolean? = null,
    val additionalText: String? = null,
    val optionalLabel: String? = null,
    val state: Int? = null,
    val titles: List<String>? = null,
    val selectedIndex: Int? = null
)