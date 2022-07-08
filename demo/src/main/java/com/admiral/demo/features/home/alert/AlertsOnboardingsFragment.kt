package com.admiral.demo.features.home.alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtAlertsOnboardingBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ErrorviewScreen
import com.admiral.demo.screen.OnboardingScreen
import com.admiral.demo.screen.AlertScreen
import com.admiral.demo.screen.ZeroScreen

class AlertsOnboardingsFragment : BaseFragment(R.layout.fmt_alerts_onboarding) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtAlertsOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            btnAlerts.setOnClickListener {
                navigationViewModel.open(AlertScreen())
            }

            btnOnboarding.setOnClickListener {
                navigationViewModel.open(OnboardingScreen())
            }

            btnZeroscreen.setOnClickListener {
                navigationViewModel.open(ZeroScreen())
            }

            btnErrorview.setOnClickListener {
                navigationViewModel.open(ErrorviewScreen())
            }
        }
    }
}