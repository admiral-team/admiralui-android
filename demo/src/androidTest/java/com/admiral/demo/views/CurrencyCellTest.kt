package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewCurrencyDefaultBinding
import com.admiral.demo.databinding.TestViewCurrencyFlagsBinding
import com.admiral.demo.databinding.TestViewCurrencyHeaderBinding
import com.admiral.demo.databinding.TestViewCurrencyIconsBinding
import com.admiral.demo.databinding.TestViewCurrencyIconsFlagsBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.currency.CurrencyCell
import com.admiral.uikit.components.currency.CurrencyDrawableType
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class CurrencyCellTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val bindingDefault = TestViewCurrencyDefaultBinding.inflate(layoutInflater)
    private val bindingFlags = TestViewCurrencyFlagsBinding.inflate(layoutInflater)
    private val bindingHeader = TestViewCurrencyHeaderBinding.inflate(layoutInflater)
    private val bindingIconsFlags = TestViewCurrencyIconsFlagsBinding.inflate(layoutInflater)
    private val bindingIcons = TestViewCurrencyIconsBinding.inflate(layoutInflater)

    private fun CurrencyCell.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        binding: ViewBinding = bindingDefault
    ) {
        with(binding.root as CurrencyCell) {
            check()
        }
    }

    private fun checkProgrammatically(
        isHeader: Boolean = false,
        isIcons: Boolean = false,
        isFlags: Boolean = false
    ) {
        CurrencyCell(wrappedContext).apply {
            this.textBuy = "68,65"
            this.textSell = "67,88"
            this.textCurrency = "USD"

            if (isHeader) {
                this.isHeader = true
                this.textBuy = "Купить"
                this.textSell = "Продать"
                this.textCurrency = "Валюта"
            }

            if (isIcons) {
                this.drawableSellType = CurrencyDrawableType.UP
                this.drawableBuyType = CurrencyDrawableType.DOWN
            }

            if (isFlags) {
                drawableCurrency = context.getDrawable(R.drawable.ic_flag_united_states_of_america)
            }
            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationDefaultState() {
        checkByInflation()
    }

    @Test
    fun checkByInflationFlagsState() {
        checkByInflation(binding = bindingFlags)
    }

    @Test
    fun checkByInflationHeaderState() {
        checkByInflation(binding = bindingHeader)
    }

    @Test
    fun checkByInflationIconsState() {
        checkByInflation(binding = bindingIcons)
    }

    @Test
    fun checkByInflationIconsFlagsState() {
        checkByInflation(binding = bindingIconsFlags)
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyDefaultState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyFlagsState() {
        checkProgrammatically(isFlags = true)
    }

    @Test
    fun checkProgrammaticallyHeaderState() {
        checkProgrammatically(isHeader = true)
    }

    @Test
    fun checkProgrammaticallyIconsState() {
        checkProgrammatically(isIcons = true)
    }

    @Test
    fun checkProgrammaticallyIconsFlagsState() {
        checkProgrammatically(isIcons = true, isFlags = true)
    }

    // endregion
}