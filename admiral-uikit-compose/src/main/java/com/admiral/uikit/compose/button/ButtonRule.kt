package com.admiral.uikit.compose.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X5

@Suppress("LongMethod")
@Composable
fun ButtonRule(
    modifier: Modifier = Modifier,
    actionText: String? = null,
    ruleText: String? = null,
    checked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    val ruleTextColor =
        if (enabled) AdmiralTheme.colors.textPrimary else AdmiralTheme.colors.textPrimary.withAlpha()

    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            checkBoxId,
            actionTextId,
            ruleTextId,
        ) = createRefs()

        Checkbox(
            modifier = Modifier
                .constrainAs(checkBoxId) {
                    start.linkTo(parent.start)
                    top.linkTo(ruleTextId.top)
                }
                .sizeIn(maxWidth = DIMEN_X5, maxHeight = DIMEN_X5),
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = CheckboxDefaults.colors(
                checkedColor = AdmiralTheme.colors.elementAccent,
                uncheckedColor = AdmiralTheme.colors.elementAccent,
                checkmarkColor = AdmiralTheme.colors.elementStaticWhite,
                disabledColor = AdmiralTheme.colors.elementAccent.withAlpha(),
                disabledIndeterminateColor = AdmiralTheme.colors.elementAccent.withAlpha(),
            )
        )

        ruleText?.let {
            Text(
                modifier = Modifier.constrainAs(ruleTextId) {
                    start.linkTo(checkBoxId.end, DIMEN_X3)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.preferredWrapContent
                },
                color = ruleTextColor,
                text = ruleText,
                style = AdmiralTheme.typography.subtitle3
            )
        }

        actionText?.let {
            Box(
                modifier = Modifier.constrainAs(actionTextId) {
                    start.linkTo(ruleTextId.start)
                    end.linkTo(parent.end)
                    top.linkTo(ruleTextId.bottom, DIMEN_X3)
                    width = Dimension.fillToConstraints
                },
            ) {
                Link(
                    isEnabled = enabled,
                    linkText = actionText
                )
            }

        }
    }
}

@Preview
@Composable
private fun ButtonRulePreview() {
    var checked by remember { mutableStateOf(false) }

    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Row {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ButtonRule(
                        checked = checked,
                        enabled = true,
                        onCheckedChange = { isChecked -> checked = isChecked },
                        ruleText = "Я согласен с условиями договора и подтверждаю свое согласие на обработку персональных данных Банком",
                        actionText = "Открыть список документов"
                    )
                }
            }
        }
    }
}