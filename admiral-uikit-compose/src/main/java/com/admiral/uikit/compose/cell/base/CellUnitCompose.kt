package com.admiral.uikit.compose.cell.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.admiral.uikit.common.components.cell.base.CellUnit

interface CellUnitCompose : CellUnit {
    @Composable
    fun Create(modifier: Modifier)
}