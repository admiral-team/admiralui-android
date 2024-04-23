package com.admiral.uikit.compose.tabs

fun MutableList<TabItem>.selectNewTab(tabItemSelected: TabItem) {
    val indexOfLastSelected = this.indexOf(this.find { it.isSelected })
    val indexOfItem = this.indexOf(tabItemSelected)

    if (indexOfItem != indexOfLastSelected) {
        if (indexOfLastSelected != -1) {
            this[indexOfLastSelected] = this[indexOfLastSelected].copy(isSelected = false)
        }

        if (indexOfItem != -1) {
            this[indexOfItem] = this[indexOfItem].copy(isSelected = true)
        }
    }
}
