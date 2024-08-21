package com.admiral.demo.features.info

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtDocumentationBinding

class DocumentationFragment : BaseFragment(R.layout.fmt_documentation) {

    private val binding by viewBinding(FmtDocumentationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }

    companion object {
        const val DOCUMENTATION_NAME = "documentation.pdf"
    }
}