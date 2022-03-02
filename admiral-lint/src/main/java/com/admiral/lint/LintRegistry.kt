package com.admiral.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.admiral.lint.cellUnit.CellUnitDetector
import com.admiral.lint.editText.EditTextDetector
import com.admiral.lint.textColor.AdmiralTextViewTextColorDetector
import com.admiral.lint.textView.TextViewDetector

@Suppress("UnstableApiUsage")
class LintRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(
            CellUnitDetector.ISSUE,
            EditTextDetector.ISSUE,
            TextViewDetector.ISSUE,
            AdmiralTextViewTextColorDetector.ISSUE
        )

    override val vendor = Vendor(
        vendorName = "Admiral UIKit"
    )
}