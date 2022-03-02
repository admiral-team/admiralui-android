package com.admiral.lint.textColor

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.admiral.lint.ADMIRAL_TEXT_VIEW
import com.admiral.lint.ATTR_TEXT_COLOR_NORMAL_ENABLED
import com.admiral.lint.ISSUE_MAX_PRIORITY
import com.admiral.lint.attributes
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class AdmiralTextViewTextColorDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String> {
        return listOf(ADMIRAL_TEXT_VIEW)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        val attribute = element.attributes().find { node ->
            node.textContent.contains(SdkConstants.ATTR_TEXT_COLOR)
        } ?: return

        context.report(
            ISSUE,
            context.getLocation(attribute),
            MESSAGE,
            createQuickFix()
        )
    }

    private fun createQuickFix(): LintFix {
        return LintFix.create()
            .replace()
            .text("android:${SdkConstants.ATTR_TEXT_COLOR}")
            .with("app:$ATTR_TEXT_COLOR_NORMAL_ENABLED")
            .build()
    }

    companion object {
        private const val ID = "AdmiralTextViewTextColorId"
        private const val MESSAGE = "Do not use `android:textColor` directly"
        private val USAGE = """TextView Usage: It's better to use 
            |`app:admiralTextColorNormalEnabled`, 
            |`app:admiralTextColorPressed`, 
            |`app:admiralTextColorNormalDisabled` or 
            |`app:admiralTextColorNormalEnabledPalette`"""
            .trimMargin()
        private val EXPLANATION = """According to the rules, 
            |we shouldn't use `android:textColor`. 
            |This helps to maintain consistency in our code base and implementations"""
            .trimMargin()

        val ISSUE: Issue = Issue.create(
            ID,
            USAGE,
            EXPLANATION,
            Category.CORRECTNESS,
            ISSUE_MAX_PRIORITY,
            Severity.WARNING,
            Implementation(AdmiralTextViewTextColorDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}