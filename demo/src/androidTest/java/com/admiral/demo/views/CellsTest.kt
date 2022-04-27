package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewButtonGooglePlayBinding
import com.admiral.demo.databinding.TestViewCellsCentralBinding
import com.admiral.demo.databinding.TestViewCellsCentralTrailingBinding
import com.admiral.demo.databinding.TestViewCellsLeadingCentralBinding
import com.admiral.demo.databinding.TestViewCellsLeadingCentralTrailingBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.components.badge.BadgeSize
import com.admiral.uikit.components.button.ButtonGooglePay
import com.admiral.uikit.components.cell.BaseCell
import com.admiral.uikit.components.cell.unit.CardCellUnit
import com.admiral.uikit.components.cell.unit.CheckboxCellUnit
import com.admiral.uikit.components.cell.unit.DoubleSubtitleCellUnit
import com.admiral.uikit.components.cell.unit.IconBackgroundCellUnit
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.admiral.uikit.components.cell.unit.LabelCellUnit
import com.admiral.uikit.components.cell.unit.RadioButtonCellUnit
import com.admiral.uikit.components.cell.unit.SubtitlePaymentCellUnit
import com.admiral.uikit.components.cell.unit.SubtitleTitleCellUnit
import com.admiral.uikit.components.cell.unit.SwitcherCellUnit
import com.admiral.uikit.components.cell.unit.TextCellUnit
import com.admiral.uikit.components.cell.unit.TextLabelCellUnit
import com.admiral.uikit.components.cell.unit.TextMessageCellUnit
import com.admiral.uikit.components.cell.unit.TitleSubtitleCellUnit
import com.admiral.uikit.components.cell.unit.TitleSubtitleTextbuttonCellUnit
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class CellsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(
        context,
        R.style.Theme_AdmiralUIAndroid_Launcher
    )

    private val layoutInflater = LayoutInflater.from(wrappedContext)

    private val centralBinding = TestViewCellsCentralBinding.inflate(layoutInflater)
    private val leadingCentralBinding = TestViewCellsLeadingCentralBinding.inflate(layoutInflater)
    private val centralTrailingBinding = TestViewCellsCentralTrailingBinding.inflate(layoutInflater)
    private val leadingCentralTrailingBinding = TestViewCellsLeadingCentralTrailingBinding.inflate(layoutInflater)

    private val leadingViews = listOf(
        CellData(
            view = IconCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_diamond_outline)
            },
            name = IconCellUnit::class.java.simpleName
        ),
        CellData(
            view = IconBackgroundCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_diamond_outline)
            },
            name = IconBackgroundCellUnit::class.java.simpleName
        ),
        CellData(
            view = IconBackgroundCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_diamond_outline)
                badge.apply {
                    isVisible = true
                    badgeSize = BadgeSize.SMALL
                }
            },
            name = "${IconBackgroundCellUnit::class.java.simpleName}-SmallBadge"
        ),
        CellData(
            view = IconBackgroundCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_diamond_outline)
                badge.apply {
                    isVisible = true
                    badgeSize = BadgeSize.NORMAL
                    text = "6"
                }
            },
            name = "${IconBackgroundCellUnit::class.java.simpleName}-NormalBadge"
        ),
        CellData(
            view = LabelCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_label)
            },
            name = LabelCellUnit::class.java.simpleName
        ),
        CellData(
            view = LabelCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_label)
                badge.apply {
                    isVisible = true
                    badgeSize = BadgeSize.SMALL
                }
            },
            name = "${LabelCellUnit::class.java.simpleName}-SmallBadge"
        ),
        CellData(
            view = LabelCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_label)
                badge.apply {
                    isVisible = true
                    badgeSize = BadgeSize.NORMAL
                    text = "6"
                }
            },
            name = "${LabelCellUnit::class.java.simpleName}-NormalBadge"
        ),
        CellData(
            view = TextLabelCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                text = "TT"
            },
            name = TextLabelCellUnit::class.java.simpleName
        ),
        CellData(
            view = CardCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_card_start)
            },
            name = TextLabelCellUnit::class.java.simpleName
        )
    )

    private val centerViews = listOf(
        CellData(
            view = TextCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING_TEXT
                text = "TextCellUnit"
            },
            name = TextCellUnit::class.java.simpleName
        ),
        CellData(
            view = SubtitleTitleCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING_TEXT
                title = "SubtitleTitleCellUnit"
                subtitle = "SubtitleTitleCellUnit"
            },
            name = SubtitleTitleCellUnit::class.java.simpleName
        ),
        CellData(
            view = TitleSubtitleCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING_TEXT
                title = "TitleSubtitleCellUnit"
                subtitle = "TitleSubtitleCellUnit"
            },
            name = TitleSubtitleCellUnit::class.java.simpleName
        ),
        CellData(
            view = TextMessageCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING_TEXT
                title = "Title"
                titleMore = "More"
                summ = "Summ"
                summMore = "More"
                message = "Message"
            },
            name = TextMessageCellUnit::class.java.simpleName
        ),
        CellData(
            view = TitleSubtitleTextbuttonCellUnit(wrappedContext).apply {
                unitType = CellUnitType.LEADING_TEXT
                title = "Title"
                subtitle = "Subtitle"
                subtitleSecond = "Subtitle second"
                button = "Button"
                percent = "Percent"
            },
            name = TitleSubtitleTextbuttonCellUnit::class.java.simpleName
        )
    )

    private val trailingViews = listOf(
        CellData(
            view = IconCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
                icon = ContextCompat.getDrawable(
                    context,
                    R.drawable.admiral_ic_chevron_right_outline
                )
            },
            name = IconCellUnit::class.java.simpleName
        ),
        CellData(
            view = RadioButtonCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
            },
            name = RadioButtonCellUnit::class.java.simpleName
        ),
        CellData(
            view = CheckboxCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
            },
            name = CheckboxCellUnit::class.java.simpleName
        ),
        CellData(
            view = SwitcherCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
            },
            name = SwitcherCellUnit::class.java.simpleName
        ),
        CellData(
            view = LabelCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_label)
            },
            name = LabelCellUnit::class.java.simpleName
        ),
        CellData(
            view = CardCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
                icon = ContextCompat.getDrawable(context, R.drawable.test_ic_card_start)
            },
            name = CardCellUnit::class.java.simpleName
        ),
        CellData(
            view = DoubleSubtitleCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
                subtitleTop = "Date"
                subtitleBottom = "Percent"
            },
            name = DoubleSubtitleCellUnit::class.java.simpleName
        ),
        CellData(
            view = SubtitlePaymentCellUnit(wrappedContext).apply {
                unitType = CellUnitType.TRAILING
                payment = ContextCompat.getDrawable(context, R.drawable.test_ic_visa)
                subtitle = "Subtitle"
            },
            name = SubtitlePaymentCellUnit::class.java.simpleName
        ),
    )

    private fun BaseCell.check(testName: String? = null) {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight,
            name = testName
        )
    }

    // region check by inflation
    private fun checkCellByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean
    ) {
        with(viewBinding.root as BaseCell) {
            this.isEnabled = isEnabled
            check()
        }
    }

    @Test
    fun checkByInflationCentralEnabledState() {
        checkCellByInflation(
            viewBinding = centralBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationCentralDisabledState() {
        checkCellByInflation(
            viewBinding = centralBinding,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationLeadingCentralEnabledState() {
        checkCellByInflation(
            viewBinding = leadingCentralBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationLeadingCentralDisabledState() {
        checkCellByInflation(
            viewBinding = leadingCentralBinding,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationCentralTrailingEnabledState() {
        checkCellByInflation(
            viewBinding = centralTrailingBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationCentralTrailingDisabledState() {
        checkCellByInflation(
            viewBinding = centralTrailingBinding,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationLeadingCentralTrailingEnabledState() {
        checkCellByInflation(
            viewBinding = leadingCentralTrailingBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationLeadingCentralTrailingDisabledState() {
        checkCellByInflation(
            viewBinding = leadingCentralTrailingBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    private fun checkCellProgrammatically(
        isEnabled: Boolean,
        leadingCell: CellData?,
        centerCell: CellData?,
        trailingCell: CellData?
    ) {
        val baseCell = BaseCell(wrappedContext).apply {
            val views = listOfNotNull(leadingCell?.view, centerCell?.view, trailingCell?.view)
            addViews(views)
            this.isEnabled = isEnabled
        }

        val suffix = if (isEnabled) "Enabled" else "Disabled"
        val testName = when {
            leadingCell != null && centerCell != null && trailingCell != null -> {
                "${leadingCell.name}_${centerCell.name}_${trailingCell.name}_$suffix"
            }
            leadingCell != null && centerCell != null -> {
                "${leadingCell.name}_${centerCell.name}_$suffix"
            }
            centerCell != null && trailingCell != null -> {
                "${centerCell.name}_${trailingCell.name}_$suffix"
            }
            centerCell != null -> {
                "${centerCell.name}_$suffix"
            }
            else -> throw IllegalStateException("Unknown state")
        }

        baseCell.check(testName)

        // Since we reusing views for tests we need to remove them
        // from the baseCell. If we don't there will be a crash.
        baseCell.removeAllViews()
    }

    private fun checkProgrammaticallyCentralCell(isEnabled: Boolean) {
        centerViews.forEach { centerCell ->
            checkCellProgrammatically(
                isEnabled = isEnabled,
                leadingCell = null,
                centerCell = centerCell,
                trailingCell = null
            )
        }
    }

    private fun checkProgrammaticallyLeadingCentralCell(isEnabled: Boolean) {
        leadingViews.forEach { leadingCell ->
            checkCellProgrammatically(
                isEnabled = isEnabled,
                leadingCell = leadingCell,
                centerCell = centerViews.last(),
                trailingCell = null
            )
        }
    }

    private fun checkProgrammaticallyCentralTrailingCell(isEnabled: Boolean) {
        trailingViews.forEach { trailingCell ->
            checkCellProgrammatically(
                isEnabled = isEnabled,
                leadingCell = null,
                centerCell = centerViews.first(),
                trailingCell = trailingCell
            )
        }
    }

    private fun checkProgrammaticallyLeadingCentralTrailingCell(isEnabled: Boolean) {
        centerViews.forEach { centerCell ->
            checkCellProgrammatically(
                isEnabled = isEnabled,
                leadingCell = leadingViews.first(),
                centerCell = centerCell,
                trailingCell = trailingViews.first()
            )
        }
    }

    @Test
    fun checkProgrammaticallyCentralCellEnabled() {
        checkProgrammaticallyCentralCell(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyCentralCellDisabled() {
        checkProgrammaticallyCentralCell(isEnabled = false)
    }

    @Test
    fun checkProgrammaticallyLeadingCentralCellEnabled() {
        checkProgrammaticallyLeadingCentralCell(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyLeadingCentralCellDisabled() {
        checkProgrammaticallyLeadingCentralCell(isEnabled = false)
    }

    @Test
    fun checkProgrammaticallyCentralTrailingCellEnabled() {
        checkProgrammaticallyCentralTrailingCell(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyCentralTrailingCellDisabled() {
        checkProgrammaticallyCentralTrailingCell(isEnabled = false)
    }

    @Test
    fun checkProgrammaticallyLeadingCentralTrailingCellEnabled() {
        checkProgrammaticallyLeadingCentralTrailingCell(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyLeadingCentralTrailingCellDisabled() {
        checkProgrammaticallyLeadingCentralTrailingCell(isEnabled = false)
    }
    // endregion

    data class CellData(
        val view: View,
        val name: String
    )
}