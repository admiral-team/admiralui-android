package com.admiral.uikit.components.cell.base

@Deprecated("Use com.admiral.uikit.core.components.cell.base.CellUnitType instead")
enum class CellUnitType {
    LEADING,
    LEADING_TEXT,
    TRAILING;

    companion object {
        fun from(index: Int) = values()[index]
    }
}