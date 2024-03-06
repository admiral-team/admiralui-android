package com.admiral.demo.features.info

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtDocumentationBinding

class DocumentationFragment : BaseFragment(R.layout.fmt_documentation) {

    private val binding by viewBinding(FmtDocumentationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            pdfView.fromAsset(DOCUMENTATION_NAME)
                .onLoad {
                    spinnerDocumentation.isVisible = false
                }
                .load()
        }
    }

    companion object {
        const val DOCUMENTATION_NAME = "documentation.pdf"
    }
}