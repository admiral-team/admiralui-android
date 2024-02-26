package com.admiral.demo.compose.features.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.admiral.uikit.compose.button.AdmiralButtonColor
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6

@Composable
@Suppress("LongMethod")
fun SecondaryButtonsView() {
    var isLoading by remember { mutableStateOf(false) }
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(vertical = DIMEN_X4, horizontal = DIMEN_X2),
        ) {
            Spacer(modifier = Modifier.size(DIMEN_X11))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                color = AdmiralButtonColor.secondary(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline),
                onClick = {
                    isLoading = isLoading.not()
                }
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                color = AdmiralButtonColor.secondary(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                actionText = "Big Button",
                modifier = Modifier
                    .fillMaxWidth(),
                color = AdmiralButtonColor.secondary(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                color = AdmiralButtonColor.secondary(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                color = AdmiralButtonColor.secondary(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.medium(),
                actionText = "Medium Button",
                color = AdmiralButtonColor.secondary(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                color = AdmiralButtonColor.secondary(),
                iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                color = AdmiralButtonColor.secondary(),
                iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
            )
            Spacer(modifier = Modifier.size(DIMEN_X6))
            Button(
                size = AdmiralButtonSize.small(),
                actionText = "Small Button",
                color = AdmiralButtonColor.secondary(),
            )
        }
    }
}

@Preview
@Composable
fun SecondaryButtonsViewPreview() {
    SecondaryButtonsView()
}