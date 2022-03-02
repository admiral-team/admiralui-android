package com.admiral.demo.features.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.ext.observe
import com.admiral.demo.screen.Screen

class NavigationFragment : BaseFragment(R.layout.fmt_tab_container) {

    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(navigationViewModel.openScreen()) {
            showScreen(it)
        }

        observe(navigationViewModel.closeScreen()) {
            childFragmentManager.popBackStack()
        }

        arguments?.getParcelable<Screen>(ARG_SCREEN)?.let(::showScreen)

        childFragmentManager.addOnBackStackChangedListener {
            val visible = (childFragmentManager.fragments.last() as? BaseFragment)?.isThemePickerVisible ?: true
            (requireParentFragment() as? MainFragment)?.themePickerVisibility(visible)
        }
    }

    fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }

    private fun showScreen(screen: Screen) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container, screen.getFragment(), screen.key)
            .addToBackStack(screen.key)
            .commit()
    }

    companion object {
        private const val ARG_SCREEN = "ARG_SCREEN"

        fun newInstance(screen: Screen): NavigationFragment {
            val fragment = NavigationFragment()
            val bundle = Bundle()
            bundle.putParcelable("ARG_SCREEN", screen)
            fragment.arguments = bundle

            return fragment
        }
    }
}