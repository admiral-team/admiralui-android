package com.admiral.demo.features.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInfoMoreBinding
import com.admiral.demo.features.main.NavigationViewModel

class InfoMoreFragment : BaseFragment(R.layout.fmt_info_more) {

    private val binding by viewBinding(FmtInfoMoreBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, false, navigationViewModel::close)
    }
}