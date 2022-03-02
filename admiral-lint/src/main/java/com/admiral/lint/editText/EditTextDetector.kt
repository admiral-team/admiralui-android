package com.admiral.lint.editText

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.admiral.lint.ADMIRAL_TEXT_FIELD
import com.admiral.lint.ISSUE_MAX_PRIORITY
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class EditTextDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String> {
        return listOf(SdkConstants.EDIT_TEXT)
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
            .text(SdkConstants.EDIT_TEXT)
            .with(ADMIRAL_TEXT_FIELD)
            .build()
    }

    companion object {
        private const val ID = "AdmiralEditTextId"
        private const val MESSAGE = "Do not use EditText directly"
        private const val USAGE =
            "EditText Usage: Use the Admiral' team custom view like **TextField**"
        private val EXPLANATION = """According to the business rules, we use 
            |**TextField** in place of the android EditText. 
            |This helps to maintain consistency in our 
            |code base and implementations"""
            .trimMargin()

        val ISSUE: Issue = Issue.create(
            ID,
            USAGE,
            EXPLANATION,
            Category.CORRECTNESS,
            ISSUE_MAX_PRIORITY,
            Severity.WARNING,
            Implementation(EditTextDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}