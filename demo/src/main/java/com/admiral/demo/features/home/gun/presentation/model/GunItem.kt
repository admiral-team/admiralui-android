package com.admiral.demo.features.home.gun.presentation.model

import com.admiral.uikit.common.components.button.ButtonSize
import com.admiral.uikit.common.components.button.ButtonStyle

sealed class GunItem(
    open val id: String,
    open val typeId: String,
    open val description: String,
    open val isEnabled: Boolean,
) {
    data class Button(
        override val id: String,
        override val typeId: String,
        override val description: String,
        override val isEnabled: Boolean,
        val size: ButtonSize,
        val style: ButtonStyle,
        val text: String,
        val isLoading: Boolean,
        val isIconEnabled: Boolean
    ) : GunItem(
        id = id,
        typeId = typeId,
        description = description,
        isEnabled = isEnabled
    )

    data class TextField(
        override val id: String,
        override val typeId: String,
        override val description: String,
        override val isEnabled: Boolean,
        val text: String,
        val additionalText: String,
        val optionalLabel: String,
        val isIconEnabled: Boolean,
        val isError: Boolean,
        val isReadOnly: Boolean
    ) : GunItem(
        id = id,
        typeId = typeId,
        description = description,
        isEnabled = isEnabled
    )

    data class StandardTabs(
        override val id: String,
        override val typeId: String,
        override val description: String,
        override val isEnabled: Boolean,
        val titles: List<String>,
        val selectedIndex: Int
    ) : GunItem(
        id = id,
        typeId = typeId,
        description = description,
        isEnabled = isEnabled
    )
}