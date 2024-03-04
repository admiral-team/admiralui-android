package com.admiral.lint.cellUnit

import com.admiral.lint.ADMIRAL_BASE_CELL
import com.admiral.lint.ISSUE_MAX_PRIORITY
import com.admiral.lint.children
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class CellUnitDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String> {
        return listOf(ADMIRAL_BASE_CELL)
    }

    override fun visitElement(context: XmlContext, element: Element) {
        element.children().forEach { node ->
            if (node is Element) {
                if (node.tagName.contains(NAME_CELL_UNIT).not()) {
                    context.report(
                        ISSUE,
                        context.getLocation(node),
                        MESSAGE,
                        null
                    )
                }
            }
        }
    }

    companion object {
        private const val NAME_CELL_UNIT = "CellUnit"
        private const val ID = "CellUnitId"
        private const val MESSAGE = "Do not use non CellUnit elements in BaseCell"
        private const val USAGE =
            "CellUnit Usage: Use the Admiral' team CellUnits like **TextCellUnit**"
        private val EDIT_TEXT_EXPLANATION = """According to the rules, we use 
            |**CellUnit** inside of BaseCell. This helps to maintain consistency 
            |in our code base and implementations"""
            .trimMargin()

        val ISSUE: Issue = Issue.create(
            ID,
            USAGE,
            EDIT_TEXT_EXPLANATION,
            Category.USABILITY,
            ISSUE_MAX_PRIORITY,
            Severity.ERROR,
            Implementation(CellUnitDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }
}