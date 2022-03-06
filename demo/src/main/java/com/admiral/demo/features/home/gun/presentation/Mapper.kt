package com.admiral.demo.features.home.gun.presentation

import com.admiral.demo.features.home.gun.domain.model.GHOST_BUTTON_ID
import com.admiral.demo.features.home.gun.domain.model.PRIMARY_BUTTON_ID
import com.admiral.demo.features.home.gun.domain.model.SECONDARY_BUTTON_ID
import com.admiral.demo.features.home.gun.domain.model.STANDARD_TABS_ID
import com.admiral.demo.features.home.gun.domain.model.TEXT_FIELD_STANDARD_ID
import com.admiral.uikit.common.components.button.ButtonSize
import com.admiral.uikit.common.components.button.ButtonStyle
import com.admiral.demo.features.home.gun.presentation.model.GunTemplate as TemplatePresentation
import com.admiral.demo.features.home.gun.presentation.model.GunItem as ItemPresentation
import com.admiral.demo.features.home.gun.domain.model.Template as TemplateDomain
import com.admiral.demo.features.home.gun.domain.model.Item as ItemDomain

fun TemplateDomain.toTemplatePresentation(): TemplatePresentation {
    return TemplatePresentation(
        id = this.id,
        description = this.description,
        items = this.items.mapIndexed { index, item -> item.toItemPresentation(index) }
    )
}

fun ItemDomain.toItemPresentation(index: Int): ItemPresentation {
    return when (this.id) {
        PRIMARY_BUTTON_ID, SECONDARY_BUTTON_ID, GHOST_BUTTON_ID -> ItemPresentation.Button(
            id = "${this.id}_$index",
            typeId = this.id,
            description = this.description,
            isEnabled = this.properties.isEnabled ?: true,
            size = when (this.properties.size) {
                1 -> ButtonSize.Small
                2 -> ButtonSize.Medium
                3 -> ButtonSize.Big
                else -> throw IllegalStateException("Unknown size: ${this.properties.size}")
            },
            style = when(this.id) {
                PRIMARY_BUTTON_ID -> ButtonStyle.Primary
                SECONDARY_BUTTON_ID -> ButtonStyle.Secondary
                GHOST_BUTTON_ID -> ButtonStyle.Ghost
                else -> throw IllegalStateException("Unknown button id: ${this.id}")
            },
            text = properties.text ?: "",
            isLoading = properties.isLoading ?: false,
            isIconEnabled = properties.isIconEnabled ?: false
        )
        TEXT_FIELD_STANDARD_ID -> ItemPresentation.TextField(
            id = "${this.id}_$index",
            typeId = this.id,
            description = this.description,
            isEnabled = this.properties.isEnabled ?: true,
            additionalText = this.properties.additionalText ?: "",
            optionalLabel = this.properties.optionalLabel ?: "",
            isError = this.properties.state == 1,
            isReadOnly = this.properties.state == 2,
            text = properties.text ?: "",
            isIconEnabled = properties.isIconEnabled ?: false
        )
        STANDARD_TABS_ID -> ItemPresentation.StandardTabs(
            id = "${this.id}_$index",
            typeId = this.id,
            description = this.description,
            isEnabled = this.properties.isEnabled ?: true,
            titles = this.properties.titles ?: emptyList(),
            selectedIndex = this.properties.selectedIndex ?: 0
        )
        else -> throw IllegalStateException("Unknown Item id: ${this.id}")
    }
}

