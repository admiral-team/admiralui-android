package com.admiral.demo.compose.home.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.button.ButtonRule
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X14
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
fun RuleButtonsScreen(
    onBackClicked: () -> Unit = {}
) {
    var checked by remember { mutableStateOf(false) }

    var isEnable by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                title = stringResource(id = R.string.buttons_rules),
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClicked,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = DIMEN_X4)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnable = it == 0
                }
            )

            ButtonRule(
                modifier = Modifier
                    .padding(top = DIMEN_X14)
                    .padding(horizontal = DIMEN_X4),
                checked = checked,
                enabled = isEnable,
                onCheckedChange = { isChecked -> checked = isChecked },
                ruleText = stringResource(id = R.string.buttons_example_rule),
                actionText = stringResource(id = R.string.buttons_example_rules_action)
            )
        }
    }
}


@Preview
@Composable
private fun RuleButtonsScreenPreview() {
    AdmiralTheme {
        RuleButtonsScreen()
    }
}
