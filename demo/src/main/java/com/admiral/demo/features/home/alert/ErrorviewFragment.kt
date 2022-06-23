package com.admiral.demo.features.home.alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtAlertsErrorScreenBinding
import com.admiral.demo.features.main.NavigationViewModel

class ErrorviewFragment : BaseFragment(R.layout.fmt_alerts_error_screen) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtAlertsErrorScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        binding.errorView.button.setOnClickListener {
            navigationViewModel.close()
        }
    }
}