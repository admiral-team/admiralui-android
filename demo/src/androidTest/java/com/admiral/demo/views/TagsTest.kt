package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTagsLargeBinding
import com.admiral.demo.databinding.TestViewTagsMediumBinding
import com.admiral.demo.databinding.TestViewTagsSmallBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.chip.ChipGroup
import com.admiral.uikit.components.tag.Tag
import com.admiral.uikit.components.tag.TagColor
import com.admiral.uikit.components.tag.TagIconPosition
import com.admiral.uikit.components.tag.TagSize
import org.junit.Test

class TagsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val tagsLargeBinding = TestViewTagsLargeBinding.inflate(layoutInflater)
    private val tagsMediumBinding = TestViewTagsMediumBinding.inflate(layoutInflater)
    private val tagsSmallBinding = TestViewTagsSmallBinding.inflate(layoutInflater)
    private val tags = listOf(
        Tag(wrappedContext).apply {
            tagColor = TagColor.Blue
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isIconColored = true
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Blue
            text = context.getString(R.string.tags_chips_emoji)
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Blue
            text = context.getString(R.string.tags_chips_flag)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.test_ic_france)
            iconPosition = TagIconPosition.Right
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Gray
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isIconColored = true
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Gray
            text = context.getString(R.string.tags_chips_emoji)
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Gray
            text = context.getString(R.string.tags_chips_flag)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.test_ic_france)
            iconPosition = TagIconPosition.Right
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Green
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isIconColored = true
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Green
            text = context.getString(R.string.tags_chips_emoji)
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Green
            text = context.getString(R.string.tags_chips_flag)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.test_ic_france)
            iconPosition = TagIconPosition.Right
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Orange
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isIconColored = true
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Orange
            text = context.getString(R.string.tags_chips_emoji)
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Orange
            text = context.getString(R.string.tags_chips_flag)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.test_ic_france)
            iconPosition = TagIconPosition.Right
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Red
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isIconColored = true
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Red
            text = context.getString(R.string.tags_chips_emoji)
        },
        Tag(wrappedContext).apply {
            tagColor = TagColor.Red
            text = context.getString(R.string.tags_chips_flag)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.test_ic_france)
            iconPosition = TagIconPosition.Right
        },
    )

    private fun ChipGroup.crutchTextAppearance(resId: Int) {
        children.forEach { view ->
            // NB: I don't know why but screenshots lose font fontFamily
            // even though they exist in running app
            (view as Tag).setTextAppearance(resId)
        }
    }

    // region check by inflation
    private fun checkByInflation(size: TagSize, isEnabled: Boolean) {
        val binding = when (size) {
            TagSize.Large -> tagsLargeBinding.also {
                it.root.crutchTextAppearance(R.style.AdmiralTextAppearance_Body1)
            }
            TagSize.Medium -> tagsMediumBinding.also {
                it.root.crutchTextAppearance(R.style.AdmiralTextAppearance_Body1)
            }
            TagSize.Small -> tagsSmallBinding.also {
                it.root.crutchTextAppearance(R.style.AdmiralTextAppearance_Subhead2)
            }
        }

        with(binding.root) {
            this.isEnabled = isEnabled
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkByInflationTagsEnabledLargeState() {
        checkByInflation(
            size = TagSize.Large,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTagsDisabledLargeState() {
        checkByInflation(
            size = TagSize.Large,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTagsEnabledMediumState() {
        checkByInflation(
            size = TagSize.Medium,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTagsDisabledMediumState() {
        checkByInflation(
            size = TagSize.Medium,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTagsEnabledSmallState() {
        checkByInflation(
            size = TagSize.Small,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTagsDisabledSmallState() {
        checkByInflation(
            size = TagSize.Small,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    private fun checkProgrammatically(size: TagSize, isEnabled: Boolean) {
        val chipGroup = ChipGroup(wrappedContext).apply {
            tags.forEach { tag ->
                tag.tagSize = size
                addView(tag)
            }
            crutchTextAppearance(
                when (size) {
                    TagSize.Large -> R.style.AdmiralTextAppearance_Body1
                    TagSize.Medium -> R.style.AdmiralTextAppearance_Body1
                    TagSize.Small -> R.style.AdmiralTextAppearance_Subhead2
                }
            )
            this.isEnabled = isEnabled
        }

        with(chipGroup) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkProgrammaticallyTagsLargeEnabledState() {
        checkProgrammatically(
            size = TagSize.Large,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyTagsLargeDisabledState() {
        checkProgrammatically(
            size = TagSize.Large,
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyTagsMediumEnabledState() {
        checkProgrammatically(
            size = TagSize.Medium,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyTagsMediumDisabledState() {
        checkProgrammatically(
            size = TagSize.Medium,
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyTagsSmallEnabledState() {
        checkProgrammatically(
            size = TagSize.Small,
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyTagsSmallDisabledState() {
        checkProgrammatically(
            size = TagSize.Small,
            isEnabled = false
        )
    }
    // endregion
}