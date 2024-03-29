package com.admiral.uikit.compose.cell.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.admiral.uikit.core.components.cell.base.CellUnit

interface CellUnit : CellUnit {
    @Composable
    fun Create(modifier: Modifier)
}