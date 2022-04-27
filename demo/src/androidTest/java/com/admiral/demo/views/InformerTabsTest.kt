package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsInformerBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.InformerTab
import com.admiral.uikit.components.tabs.InformerTabContent
import com.admiral.uikit.components.tabs.InformerTabLayout
import com.admiral.uikit.components.tabs.InformerTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class InformerTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewTabsInformerBinding.inflate(layoutInflater)

    private fun InformerTabLayout.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean
    ) {
        with(binding.root) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean,
    ) {
        InformerTabLayout(wrappedContext).apply {
            this.addView(InformerTabs(wrappedContext).apply {
                val checkedId = View.generateViewId()
                addView(InformerTab(wrappedContext).apply {
                    id = checkedId
                    titleText = context.getString(R.string.tabs_example_informer_tab_title)
                    subtitleText = context.getString(R.string.tabs_example_informer_tab_subtitle)
                })

                addView(InformerTab(wrappedContext).apply {
                    titleText = context.getString(R.string.tabs_example_informer_tab_title)
                    subtitleText = context.getString(R.string.tabs_example_informer_tab_subtitle)
                })

                addView(InformerTab(wrappedContext).apply {
                    titleText = context.getString(R.string.tabs_example_informer_tab_title)
                    subtitleText = context.getString(R.string.tabs_example_informer_tab_subtitle)
                })

                this.check(checkedId)
            })
            this.addView(InformerTabContent(wrappedContext).apply {
                titleText = context.getString(R.string.tabs_informer_example_title)
                subtitleText = context.getString(R.string.tabs_informer_example_subtitle)
                summText = context.getString(R.string.tabs_informer_example_summ)
                iconDrawable = ContextCompat.getDrawable(context, R.drawable.admiral_ic_question_solid)
            })

            this.isEnabled = isEnabled
            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationEnabledState() {
        checkByInflation(
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationDisabledState() {
        checkByInflation(
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyEnabledState() {
        checkProgrammatically(
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyDisabledState() {
        checkProgrammatically(
            isEnabled = false
        )
    }
    // endregion
}