package com.admiral.demo.features.home.gun.domain.model

const val PRIMARY_BUTTON_ID = "PrimaryButton"
const val SECONDARY_BUTTON_ID = "SecondaryButton"
const val GHOST_BUTTON_ID = "GhostButton"
const val TEXT_FIELD_STANDARD_ID = "StandartTextfield"
const val STANDARD_TABS_ID = "Tab"
const val SWITCHER_ID = "Switcher"
const val TOOLBAR_ID = "Toolbar"

data class Item(
    val id: String,
    val description: String,
    val properties: Properties
)