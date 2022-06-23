package com.admiral.demo.features.home.alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtAlertsZeroScreenBinding
import com.admiral.demo.features.main.NavigationViewModel

class ZeroscreenFragment : BaseFragment(R.layout.fmt_alerts_zero_screen) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtAlertsZeroScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        binding.zeroScreen.button.setOnClickListener {
            navigationViewModel.close()
        }
    }
}