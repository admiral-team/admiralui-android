package com.admiral.demo.features.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.BuildConfig
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInfoBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ContactsScreen
import com.admiral.demo.screen.FaqScreen
import com.admiral.demo.screen.InfoMoreScreen

class InfoFragment : BaseFragment(R.layout.fmt_info) {

    private val binding by viewBinding(FmtInfoBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val version = "Версия ${BuildConfig.VERSION_NAME}"
        binding.versionTextView.text = version

        binding.infoButton.setOnClickListener {
            navigationViewModel.open(InfoMoreScreen())
        }

        binding.contactsButton.setOnClickListener {
            navigationViewModel.open(ContactsScreen())
        }

        binding.faqButton.setOnClickListener {
            navigationViewModel.open(FaqScreen())
        }
    }
}