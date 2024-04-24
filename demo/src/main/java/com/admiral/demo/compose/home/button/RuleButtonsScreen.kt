package com.admiral.demo.compose.home.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.ActionItem
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

    Surface(
        color = AdmiralTheme.colors.backgroundBasic
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AdmiralCenterAlignedTopAppBar(
                    navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                    title = stringResource(id = R.string.buttons_rules),
                    titleAlign = TextAlign.Center,
                    onNavIconClick = onBackClicked,
                    actions = listOf(
                        ActionItem(
                            icon = painterResource(id = R.drawable.admiral_ic_info_outline)
                        )
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(DIMEN_X4)
                        .fillMaxWidth()
                )

                StandardTab(
                    modifier = Modifier.padding(horizontal = DIMEN_X4),
                    items = listOf(
                        stringResource(id = R.string.tabs_default),
                        stringResource(id = R.string.tabs_disabled)
                    )
                )

                ButtonRule(
                    modifier = Modifier
                        .padding(top = DIMEN_X14)
                        .padding(horizontal = DIMEN_X4),
                    checked = checked,
                    onCheckedChange = { isChecked -> checked = isChecked },
                    ruleText = stringResource(id = R.string.buttons_example_rule),
                    actionText = stringResource(id = R.string.buttons_example_rules_action)
                )
            }
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
