/*
 * Copyright (C) 2019 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package views

import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.R
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.core.components.button.ButtonSize
import com.admiral.uikit.core.components.button.ButtonStyle
import com.admiral.uikit.databinding.TestViewButtonGhostBigBinding
import com.admiral.uikit.databinding.TestViewButtonGhostMediumBinding
import com.admiral.uikit.databinding.TestViewButtonGhostSmallBinding
import com.admiral.uikit.databinding.TestViewButtonPrimaryBigAdditionBinding
import com.admiral.uikit.databinding.TestViewButtonPrimaryBigBinding
import com.admiral.uikit.databinding.TestViewButtonPrimaryMediumBinding
import com.admiral.uikit.databinding.TestViewButtonPrimarySmallBinding
import com.admiral.uikit.databinding.TestViewButtonSecondaryBigBinding
import com.admiral.uikit.databinding.TestViewButtonSecondaryMediumBinding
import com.admiral.uikit.databinding.TestViewButtonSecondarySmallBinding
import org.junit.Rule
import org.junit.Test

class ButtonTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_2,
    )

    private val buttonPrimaryBigBinding by lazy {
        TestViewButtonPrimaryBigBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonPrimaryMediumBinding by lazy {
        TestViewButtonPrimaryMediumBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonPrimarySmallBinding by lazy {
        TestViewButtonPrimarySmallBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonSecondaryBigBinding by lazy {
        TestViewButtonSecondaryBigBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonSecondaryMediumBinding by lazy {
        TestViewButtonSecondaryMediumBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonSecondarySmallBinding by lazy {
        TestViewButtonSecondarySmallBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonGhostBigBinding by lazy {
        TestViewButtonGhostBigBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonGhostMediumBinding by lazy {
        TestViewButtonGhostMediumBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonGhostSmallBinding by lazy {
        TestViewButtonGhostSmallBinding.inflate(paparazzi.layoutInflater)
    }
    private val buttonPrimaryBigAdditionBinding by lazy {
        TestViewButtonPrimaryBigAdditionBinding.inflate(paparazzi.layoutInflater)
    }

    private fun Button.check() {
        measureUnspecified()
//        compareScreenshot(
//            view = this,
//            widthInPx = measuredWidth,
//            heightInPx = measuredHeight
//        )
        paparazzi.snapshot(this)
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
        val button = Button(paparazzi.context).apply {
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

        with(buttonPrimaryBigBinding) {
            paparazzi.snapshot(root, "zero dollars")
        }
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
            textResId = R.string.buttons_test_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_test_big,
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
            textResId = R.string.buttons_test_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_test_medium,
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
            textResId = R.string.buttons_test_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimarySmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_test_small,
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
            textResId = R.string.buttons_test_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondaryBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_test_big,
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
            textResId = R.string.buttons_test_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondaryMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_test_medium,
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
            textResId = R.string.buttons_test_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonSecondarySmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Secondary,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_test_small,
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
            textResId = R.string.buttons_test_big,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostBigDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_test_big,
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
//    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonGhostMediumEnabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_test_medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostMediumDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Medium,
            textResId = R.string.buttons_test_medium,
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
            textResId = R.string.buttons_test_small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonGhostSmallDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Ghost,
            buttonSize = ButtonSize.Small,
            textResId = R.string.buttons_test_small,
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
            textResId = R.string.buttons_test_chose,
            additionalTextResId = R.string.buttons_test_date,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyButtonPrimaryBigAdditionDisabledState() {
        checkButtonProgrammatically(
            buttonStyle = ButtonStyle.Primary,
            buttonSize = ButtonSize.Big,
            textResId = R.string.buttons_test_chose,
            additionalTextResId = R.string.buttons_test_date,
            isEnabled = false
        )
    }
    // endregion
    // endregion
}


