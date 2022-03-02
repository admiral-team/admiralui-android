package com.admiral.lint.textView

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
import com.admiral.lint.ISSUE_MAX_PRIORITY
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class TextViewDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String> {
        return listOf(SdkConstants.TEXT_VIEW)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        context.report(
            ISSUE,
            context.getElementLocation(element),
            MESSAGE,
            createQuickFix()
        )
    }

    private fun createQuickFix(): LintFix {
        return LintFix.create()
            .replace()
            .text(SdkConstants.TEXT_VIEW)
            .with(ADMIRAL_TEXT_VIEW)
            .build()
    }

    companion object {
        private const val ID = "CustomTextViewId"
        private const val MESSAGE = "Do not use TextView directly"
        private const val USAGE = "TextView Usage: Use the Admiral' team custom view **TextView**"
        var EXPLANATION = """According to the business rules, we use 
            |**Admiral TextView** in place of the android texts. 
            |This helps to maintain consistency in our code base 
            |and implementations"""
            .trimMargin()

        val ISSUE: Issue = Issue.create(
            ID,
            USAGE,
            EXPLANATION,
            Category.CORRECTNESS,
            ISSUE_MAX_PRIORITY,
            Severity.WARNING,
            Implementation(TextViewDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}