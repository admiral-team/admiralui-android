package com.admiral.uikit.core.components.cell.base

enum class CellUnitType {
    LEADING,
    LEADING_TEXT,
    TRAILING;

    companion object {
        fun from(index: Int) = values()[index]
    }
}