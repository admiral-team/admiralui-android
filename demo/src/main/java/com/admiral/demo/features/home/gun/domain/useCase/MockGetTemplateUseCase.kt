package com.admiral.demo.features.home.gun.domain.useCase

import com.admiral.demo.features.home.gun.domain.model.Item
import com.admiral.demo.features.home.gun.domain.model.PRIMARY_BUTTON_ID
import com.admiral.demo.features.home.gun.domain.model.Properties
import com.admiral.demo.features.home.gun.domain.model.STANDARD_TABS_ID
import com.admiral.demo.features.home.gun.domain.model.TEXT_FIELD_STANDARD_ID
import com.admiral.demo.features.home.gun.domain.model.Template
import kotlinx.coroutines.delay

class MockGetTemplateUseCase : IGetTemplateUseCase {
    override suspend fun execute(id: String): Template {
        delay(DELAY_IN_MILLISECONDS)
        return Template(
            id = "TemplateId",
            description = "Description",
            items = listOf(
                Item(
                    id = PRIMARY_BUTTON_ID,
                    description = "Primary button enabled",
                    properties = Properties(
                        isEnabled = true,
                        text = "Enabled Button",
                        size = 3,
                        isLoading = false,
                        isIconEnabled = false
                    )
                ),
                Item(
                    id = PRIMARY_BUTTON_ID,
                    description = "Primary button disabled",
                    properties = Properties(
                        isEnabled = false,
                        text = "Disabled Button",
                        size = 3,
                        isLoading = false,
                        isIconEnabled = false
                    )
                ),
                Item(
                    id = TEXT_FIELD_STANDARD_ID,
                    description = "TextField",
                    properties = Properties(
                        isEnabled = true,
                        additionalText = "additionalText",
                        optionalLabel = "optionalLabel",
                        isIconEnabled = false
                    )
                ),
                Item(
                    id = STANDARD_TABS_ID,
                    description = "Standard tabs",
                    properties = Properties(
                        isEnabled = true,
                        titles = listOf("One", "Two", "Third"),
                        selectedIndex = 1
                    )
                )
            )
        )
    }

    private companion object {
        const val DELAY_IN_MILLISECONDS = 800L
    }
}