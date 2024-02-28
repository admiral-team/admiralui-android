package com.admiral.demo.compose.tabs

import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun TabsScreen() {
    Scaffold { padding ->
        Checkbox(checked = true, onCheckedChange = {})
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        TabsScreen()
    }
}
