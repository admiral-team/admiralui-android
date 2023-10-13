package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewButtonGhostBigBinding
import com.admiral.demo.databinding.TestViewButtonGhostMediumBinding
import com.admiral.demo.databinding.TestViewButtonGhostSmallBinding
import com.admiral.demo.databinding.TestViewButtonPrimaryBigAdditionBinding
import com.admiral.demo.databinding.TestViewButtonPrimaryBigBinding
import com.admiral.demo.databinding.TestViewButtonPrimaryMediumBinding
import com.admiral.demo.databinding.TestViewButtonPrimarySmallBinding
import com.admiral.demo.databinding.TestViewButtonSecondaryBigBinding
import com.admiral.demo.databinding.TestViewButtonSecondaryMediumBinding
import com.admiral.demo.databinding.TestViewButtonSecondarySmallBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.core.components.button.ButtonSize
import com.admiral.uikit.core.components.button.ButtonStyle
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ButtonsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val buttonPrimaryBigBinding = TestViewButtonPrimaryBigBinding.inflate(layoutInflater)
    private val buttonPrimaryMediumBinding =
        TestViewButtonPrimaryMediumBinding.inflate(layoutInflater)
    private val buttonPrimarySmallBinding =
        TestViewButtonPrimarySmallBinding.inflate(layoutInflater)
    private val buttonSecondaryBigBinding =
        TestViewButtonSecondaryBigBinding.inflate(layoutInflater)
    private val buttonSecondaryMediumBinding =
        TestViewButtonSecondaryMediumBinding.inflate(layoutInflater)
    private val buttonSecondarySmallBinding =
        TestViewButtonSecondarySmallBinding.inflate(layoutInflater)
    private val buttonGhostBigBinding = TestViewButtonGhostBigBinding.inflate(layoutInflater)
    private val buttonGhostMediumBinding = TestViewButtonGhostMediumBinding.inflate(layoutInflater)
    private val buttonGhostSmallBinding = TestViewButtonGhostSmallBinding.inflate(layoutInflater)
    private val buttonPrimaryBigAdditionBinding =
        TestViewButtonPrimaryBigAdditionBinding.inflate(layoutInflater)

    private fun Button.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkButtonByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean
    ) {
        with(viewBinding.root as Button) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkButtonProgrammatically(
        buttonStyle: ButtonStyle,
        buttonSize: ButtonSize,
        @StringRes textResId: Int,
        isEnabled: Boolean,
        @StringRes additionalTextResId: Int? = null
    ) {
        val button = Button(context).apply {
            this.buttonStyle = buttonStyle
            this.buttonSize = buttonSize
            this.text = context.getString(textResId)
            this.isEnabled = isEnabled
            additionalTextResId?.let {
                this.additionalText = context.getString(it)
            }
        }

        button.check()
    }

    // region ButtonPrimaryBig
    // region check by inflation
    @Test
    fun checkByInflationButtonPrimaryBigEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryBigBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonPrimaryBigDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryBigBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonPrimaryBigEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonPrimaryMedium
    // region check by inflation
    @Test
    fun checkByInflationButtonPrimaryMediumEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryMediumBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonPrimaryMediumDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryMediumBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonPrimaryMediumEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonPrimarySmall
    // region check by inflation
    @Test
    fun checkByInflationButtonPrimarySmallEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimarySmallBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonPrimarySmallDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimarySmallBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonPrimarySmallEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimarySmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonSecondaryBig
    // region check by inflation
    @Test
    fun checkByInflationButtonSecondaryBigEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondaryBigBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonSecondaryBigDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondaryBigBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonSecondaryBigEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondaryBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonSecondaryMedium
    // region check by inflation
    @Test
    fun checkByInflationButtonSecondaryMediumEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondaryMediumBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonSecondaryMediumDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondaryMediumBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonSecondaryMediumEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondaryMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonSecondarySmall
    // region check by inflation
    @Test
    fun checkByInflationButtonSecondarySmallEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondarySmallBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonSecondarySmallDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonSecondarySmallBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonSecondarySmallEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondarySmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonGhostBig
    // region check by inflation
    @Test
    fun checkByInflationButtonGhostBigEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostBigBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonGhostBigDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostBigBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonGhostBigEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_big,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonSecondaryMedium
    // region check by inflation
    @Test
    fun checkByInflationButtonGhostMediumEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostMediumBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonGhostMediumDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostMediumBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonGhostMediumEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_example_medium,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonSecondarySmall
    // region check by inflation
    @Test
    fun checkByInflationButtonGhostSmallEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostSmallBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonGhostSmallDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonGhostSmallBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonGhostSmallEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostSmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_example_small,
            isEnabled = false
        )
    }
    // endregion
    // endregion

    // region ButtonPrimaryBigAddition
    // region check by inflation
    @Test
    fun checkByInflationButtonPrimaryBigAdditionEnabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryBigAdditionBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonPrimaryBigAdditionDisabledState() {
        checkButtonByInflation(
            viewBinding = buttonPrimaryBigAdditionBinding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonPrimaryBigAdditionEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_chose,
            additionalTextResId = R.string.buttons_example_date,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryBigAdditionDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_example_chose,
            additionalTextResId = R.string.buttons_example_date,
            isEnabled = false
        )
    }
    // endregion
    // endregion
}