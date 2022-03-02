package com.admiral.uikit.common.components.cell.base

enum class CellUnitType {
    LEADING,
    LEADING_TEXT,
    TRAILING;

    companion object {
        fun from(index: Int) = values()[index]
    }
}