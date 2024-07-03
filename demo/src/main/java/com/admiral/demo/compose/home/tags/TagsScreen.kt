package com.admiral.demo.compose.home.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.admiral.demo.compose.home.links.LinkScreen
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.chip.ChipEmojiPosition
import com.admiral.uikit.compose.chip.ChipGroup
import com.admiral.uikit.compose.chip.ChipItem
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.tag.AdmiralTagColor
import com.admiral.uikit.compose.tag.TagGroup
import com.admiral.uikit.compose.tag.TagItem
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X10
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.demo.R as demoR

internal const val TAGS_SCREEN_ROUTE = "tagsScreenRoute"

internal fun NavGraphBuilder.tagsScreen(onBackClick: () -> Unit) {
    composable(route = TAGS_SCREEN_ROUTE) {
        TagsScreen(onBackClick = onBackClick)
    }
}

internal fun NavController.navigateToTagsScreen() {
    navigate(TAGS_SCREEN_ROUTE)
}

@Composable
@Suppress("LongMethod")
fun TagsScreen(
    onBackClick: () -> Unit = {},
) {
    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = demoR.string.tags_chips_title)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = DIMEN_X4)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier,
                items = mutableListOf(
                    stringResource(id = com.admiral.demo.R.string.tabs_default),
                    stringResource(id = com.admiral.demo.R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnabled = it == 0
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X6, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = demoR.string.tags_chips_default),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            ChipGroup(
                isEnabled = isEnabled,
                chips = listOf(
                    ChipItem(
                        text = stringResource(id = demoR.string.tags_chips_chip_text),
                        isCloseIconVisible = true,
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                        isIconColored = true
                    ),
                    ChipItem(
                        text = stringResource(id = demoR.string.tags_chips_chip),
                        isCloseIconVisible = true,
                    ),
                    ChipItem(
                        text = stringResource(id = demoR.string.tags_chips_icons),
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid)
                    ),
                    ChipItem(
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid)
                    ),
                    ChipItem(
                        emoji = stringResource(id = demoR.string.tags_chips_dog_emoji),
                        text = stringResource(id = demoR.string.tags_chips_emoji_text)
                    ),
                    ChipItem(
                        isIconColored = false,
                        icon = painterResource(id = demoR.drawable.test_ic_russia),
                        text = stringResource(id = demoR.string.tags_chips_flag),
                    ),
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = demoR.string.tags_chips_additional),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            TagGroup(
                tags = listOf(
                    TagItem(
                        isIconColored = true,
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_icons),
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                        color = AdmiralTagColor.gray(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_emoji),
                        color = AdmiralTagColor.gray(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        icon = painterResource(id = demoR.drawable.test_ic_russia),
                        text = stringResource(id = demoR.string.tags_chips_flag),
                        color = AdmiralTagColor.gray(),
                    ),
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = demoR.string.tags_chips_success),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            TagGroup(
                tags = listOf(
                    TagItem(
                        isIconColored = true,
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_icons),
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                        color = AdmiralTagColor.green(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_emoji),
                        color = AdmiralTagColor.green(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        icon = painterResource(id = demoR.drawable.test_ic_russia),
                        text = stringResource(id = demoR.string.tags_chips_flag),
                        color = AdmiralTagColor.green(),
                    ),
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = demoR.string.tags_chips_error),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            TagGroup(
                tags = listOf(
                    TagItem(
                        isIconColored = true,
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_icons),
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                        color = AdmiralTagColor.red(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_emoji),
                        color = AdmiralTagColor.red(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        icon = painterResource(id = demoR.drawable.test_ic_russia),
                        text = stringResource(id = demoR.string.tags_chips_flag),
                        color = AdmiralTagColor.red(),
                    ),
                )
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = TextVerticalPadding)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X1)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = demoR.string.tags_chips_attention),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )

            TagGroup(
                tags = listOf(
                    TagItem(
                        isIconColored = true,
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_icons),
                        icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                        color = AdmiralTagColor.orange(),
                    ),
                    TagItem(
                        isIconColored = true,
                        isEnabled = isEnabled,
                        text = stringResource(id = demoR.string.tags_chips_emoji),
                        color = AdmiralTagColor.orange(),
                    ),
                    TagItem(
                        isEnabled = isEnabled,
                        icon = painterResource(id = demoR.drawable.test_ic_russia),
                        text = stringResource(id = demoR.string.tags_chips_flag),
                        color = AdmiralTagColor.orange(),
                    ),
                )
            )

        }
    }
}

private val TextVerticalPadding = 18.dp

@Preview
@Composable
fun TagsScreenPreview() {
    AdmiralTheme {
        TagsScreen()
    }
}