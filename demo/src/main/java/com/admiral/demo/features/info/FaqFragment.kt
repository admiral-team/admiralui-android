package com.admiral.demo.features.info

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.expandablelayout.ExpandableLayout
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInfoFaqBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ThemeManager

class FaqFragment : BaseFragment(R.layout.fmt_info_faq) {

    private val binding by viewBinding(FmtInfoFaqBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        val questions = requireContext().resources.getStringArray(R.array.faq_questions)
        val answers = requireContext().resources.getStringArray(R.array.faq_answers)

        binding.container.children.forEachIndexed { index, view ->
            with(view as ExpandableLayout) {
                parentLayout.setOnClickListener {
                    if (isExpanded) collapse() else expand()
                }
                findViewById<TextView>(R.id.faqTitle).text = questions[index]
                findViewById<TextView>(R.id.faqText).apply {
                    text = answers[index] // Html.fromHtml()
                    setLinkTextColor(ThemeManager.theme.palette.textAccent)
                }
            }
        }
    }
}