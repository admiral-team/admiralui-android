package com.admiral.demo.features.home.typography

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTypographyBinding
import com.admiral.demo.features.main.NavigationViewModel

class TypographyFragment : BaseFragment(R.layout.fmt_typography) {

    private val binding by viewBinding(FmtTypographyBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { navigationViewModel.close() }
    }
}