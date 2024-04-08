package com.admiral.uikit.compose.toolbar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.bottom.BottomNavigation
import com.admiral.uikit.compose.bottom.BottomNavigationItem
import com.admiral.uikit.compose.util.DIMEN_X20
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
fun toolbar(
    items: List<ToolbarItem>,
    checked: ToolbarItem? = null,
    backgroundColor: Color = AdmiralTheme.colors.backgroundAccentDark
): ToolbarItem? {
    val (value, setValue) = remember { mutableStateOf(checked) }

    BottomNavigation(
        modifier = Modifier
            .height(DIMEN_X20)
            .padding(start = DIMEN_X4, end = DIMEN_X4)
            .clip(RoundedCornerShape(DIMEN_X4)),
        backgroundColor = backgroundColor
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selectedContentColor = item.selectedContentColor ?: AdmiralTheme.colors.elementAccent,
                unselectedContentColor = item.unselectedContentColor ?: AdmiralTheme.colors.elementStaticWhite,
                selected = item == value,
                onClick = {
                    setValue(item)
                }
            )
        }
    }

    return value
}
