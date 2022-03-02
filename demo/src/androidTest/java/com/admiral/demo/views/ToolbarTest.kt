package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import org.junit.Test
import com.admiral.demo.databinding.TestViewToolbarWithManyItemsBinding
import com.admiral.demo.databinding.TestViewToolbarWithOneItemBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.toolbar.Toolbar
import com.admiral.uikit.components.toolbar.ToolbarItem

class ToolbarTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val toolbarWithManyItemsBinding = TestViewToolbarWithManyItemsBinding.inflate(layoutInflater)
    private val toolbarWithOneItemBinding = TestViewToolbarWithOneItemBinding.inflate(layoutInflater)
    private val toolbarItems = listOf(
        ToolbarItem(
            id = 1,
            text = context.getString(R.string.menu_toolbar_settings),
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_settings_solid)
        ),
        ToolbarItem(
            id = 2,
            text = context.getString(R.string.menu_toolbar_more),
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_info_solid)
        ),
        ToolbarItem(
            id = 3,
            text = context.getString(R.string.menu_toolbar_fill),
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_get_cash_solid)
        ),
        ToolbarItem(
            id = 4,
            text = context.getString(R.string.menu_toolbar_pay),
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_card_solid)
        ),
    )

    // region check by inflation
    @Test
    fun checkByInflationToolbarWithManyItemsEnabledState() {
        with(toolbarWithManyItemsBinding.root) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkByInflationToolbarWithManyItemDisabledState() {
        with(toolbarWithManyItemsBinding.root) {
            isEnabled = false
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkByInflationToolbarWithOneItemEnabledState() {
        with(toolbarWithOneItemBinding.root) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkByInflationToolbarWithOneItemDisabledState() {
        with(toolbarWithOneItemBinding.root) {
            isEnabled = false
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyToolbarWithManyItemsEnabledState() {
        val toolbar = Toolbar(context).apply {
            toolbarItems.forEach { addItem(it) }
        }

        toolbar.apply {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkProgrammaticallyToolbarWithManyItemsDisabledState() {
        val toolbar = Toolbar(context).apply {
            toolbarItems.forEach { addItem(it) }
            isEnabled = false
        }

        toolbar.apply {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkProgrammaticallyToolbarWithOneItemEnabledState() {
        val toolbar = Toolbar(context).apply {
            addItem(toolbarItems.first())
        }

        toolbar.apply {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkProgrammaticallyToolbarWithOneItemDisabledState() {
        val toolbar = Toolbar(context).apply {
            addItem(toolbarItems.first())
            isEnabled = false
        }

        toolbar.apply {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }
    // endregion
}