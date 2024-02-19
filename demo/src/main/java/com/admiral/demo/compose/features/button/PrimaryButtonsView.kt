package com.admiral.demo.compose.features.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun PrimaryButtonsView() {
    var isLoading by remember { mutableStateOf(false) }
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(vertical = DIMEN_X4, horizontal = DIMEN_X2),
        ) {
            Spacer(modifier = Modifier.size(DIMEN_X11))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                actionText = "Выбрать",
                additionalText = "08.06.20 — 14.08.20",
                isLoading = isLoading,
                onClick = {
                    isLoading = isLoading.not()
                })
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                actionText = "Big Button",
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                actionText = "Big Button",
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Medium Button",
                size = AdmiralButtonSize.medium(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Medium Button",
                size = AdmiralButtonSize.medium(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Medium Button",
                size = AdmiralButtonSize.medium(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonsViewPreview() {
    PrimaryButtonsView()
}