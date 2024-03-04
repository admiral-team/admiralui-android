package com.admiral.demo.features.main

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.commitNow
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtMainBinding
import com.admiral.demo.features.home.home.ComposeSwitcher
import com.admiral.demo.features.home.home.ThemeListener
import com.admiral.demo.features.home.home.ThemeSwitcher
import com.admiral.demo.features.home.onboarding.OnboardingFragment
import com.admiral.demo.features.home.onboarding.OnboardingStorage
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import com.admiral.demo.screen.FeedbackScreen
import com.admiral.demo.screen.HomeScreen
import com.admiral.demo.screen.HomeScreenCompose
import com.admiral.demo.screen.InfoScreen
import com.admiral.demo.screen.Screen
import com.admiral.demo.screen.SettingsScreen
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.darkTheme
import com.admiral.themes.lightTheme
import com.google.android.material.chip.Chip
import com.admiral.resources.R as res

class MainFragment : BaseFragment(R.layout.fmt_main), ThemeObserver, ComposeSwitcher, ThemeSwitcher,
    ThemeListener {

    private val binding by viewBinding(FmtMainBinding::bind)
    private var compose = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchToSystemTheme(ThemeStorageDAO.isThemeAuto())

        with(binding) {
            bottomNavigationView.setOnItemSelectedListener {
                selectTab(getScreen(it.itemId, compose))
                return@setOnItemSelectedListener true
            }

            bottomNavigationView.selectedItemId = R.id.mainMenuHome
        }

        initThemeSoloPickerButton()
        initThemePickerList()

        ThemeManager.subscribe(this)

        val onboardingStorage = OnboardingStorage(requireContext())
        if (!onboardingStorage.wasOpened()) {
            onboardingStorage.setOpened(true)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, OnboardingFragment())
                .addToBackStack(null)
                .commit()
        }

        requireActivity().window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val insetsCompat = toWindowInsetsCompat(insets, view)
            val isImeVisible = insetsCompat.isVisible(WindowInsetsCompat.Type.ime())
            binding.bottomNavigationView.visibility = if (isImeVisible) View.GONE else View.VISIBLE
            view.onApplyWindowInsets(insets)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this) {
                val currentFragment = childFragmentManager.fragments
                    .find { it.isVisible } as? NavigationFragment

                if (currentFragment?.onBackPressed() == false) {
                    requireActivity().finish()
                }
            }
    }

    override fun onPause() {
        super.onPause()
        ThemeManager.unsubscribe(this)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateThemePickerButtonColors()
        initThemePickerList()
    }

    override fun switchToCompose(isCompose: Boolean) {
        this.compose = isCompose

        binding.bottomNavigationView.selectedItemId = binding.bottomNavigationView.selectedItemId
    }

    override fun switchToSystemTheme(isSystem: Boolean) {
        ThemeManager.theme = if (isSystem) {
            when (isNightModeDetect()) {
                true -> darkTheme()
                false -> lightTheme()
            }
        } else {
            ThemeStorageDAO.theme
        }
    }

    fun themePickerVisibility(isVisible: Boolean) {
        binding.themePickerButton.isVisible = isVisible
    }

    private fun selectTab(screen: Screen) {
        val currentFragment = childFragmentManager.fragments.find { it.isVisible }
        val newFragment = childFragmentManager.findFragmentByTag(screen.key)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) {
            val currentNavFragment = currentFragment as? NavigationFragment
            currentNavFragment?.onClearBackStack()
            return
        }

        childFragmentManager.commitNow {
            if (newFragment == null) {
                add(R.id.container, NavigationFragment.newInstance(screen), screen.key)
            } else {
                show(newFragment)
            }

            if (currentFragment != null) {
                hide(currentFragment)
            }
        }
    }

    private fun getScreen(@IdRes id: Int, isCompose: Boolean): Screen {
        return if (isCompose) {
            when (id) {
                R.id.mainMenuHome -> HomeScreenCompose()
                R.id.mainMenuInfo -> InfoScreen()
                R.id.mainMenuFeedback -> FeedbackScreen()
                R.id.mainMenuSettings -> SettingsScreen()
                else -> HomeScreenCompose()
            }
        } else {
            when (id) {
                R.id.mainMenuHome -> HomeScreen()
                R.id.mainMenuInfo -> InfoScreen()
                R.id.mainMenuFeedback -> FeedbackScreen()
                R.id.mainMenuSettings -> SettingsScreen()
                else -> HomeScreen()
            }
        }
    }

    private fun initThemeSoloPickerButton() {
        binding.themePickerButton.setOnClickListener {
            if (ThemeStorageDAO.getThemes().count() == 2) {
                val theme = ThemeStorageDAO.getThemes().find { it != ThemeStorageDAO.theme }
                    ?: ThemeStorageDAO.theme
                ThemeManagerCompose.setCurrentTheme(theme)
                ThemeStorageDAO.theme = theme
            } else {
                binding.themesList.isVisible = !binding.themesList.isVisible

                if (binding.themesList.isVisible) {
                    binding.themePickerButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            res.drawable.admiral_ic_close_outline
                        )
                    )
                } else {
                    binding.themePickerButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_btn_theme
                        )
                    )
                }
            }
        }

        binding.themePickerButton.rippleColor = ThemeManager.theme.palette.elementContrast
    }

    private fun invalidateThemePickerButtonColors() {
        binding.themePickerButton.apply {
            backgroundTintList = ColorStateList.valueOf(
                ThemeManager.theme.palette.backgroundExtraSurface
            )
            imageTintList = ColorStateList.valueOf(
                ThemeManager.theme.palette.elementExtra
            )
        }
    }

    private fun initThemePickerList() {
        binding.themesList.apply {
            removeAllViews()
            backgroundTintList = ColorStateList.valueOf(
                ThemeManager.theme.palette.backgroundExtraSurface
            )
        }

        ThemeStorageDAO.getThemes().forEach { theme ->
            binding.themesList.addView(Chip(requireContext()).apply {
                setTextAppearance(Typography.getStyle(ThemeManager.theme.typography.body1))
                chipBackgroundColor = ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_checked),
                        intArrayOf(-android.R.attr.state_checked),
                    ),
                    intArrayOf(
                        ThemeManager.theme.palette.backgroundAccent,
                        ThemeManager.theme.palette.backgroundAdditionalOne
                    )
                )

                text = theme.name
                isCheckable = true
                setEnsureMinTouchTargetSize(false)
                isCheckedIconVisible = false

                layoutParams = FrameLayout.LayoutParams(
                    Toolbar.LayoutParams.WRAP_CONTENT,
                    Toolbar.LayoutParams.MATCH_PARENT
                )

                setOnClickListener {
                    ThemeManagerCompose.setCurrentTheme(theme)
                    ThemeStorageDAO.theme = theme
                    binding.themesList.isVisible = false

                    binding.themePickerButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_btn_theme
                        )
                    )
                }

                if (theme.name == ThemeManager.theme.name) {
                    isChecked = true
                    setTextColor(ThemeManager.theme.palette.textStaticWhite)
                } else {
                    setTextColor(ThemeManager.theme.palette.textPrimary)
                }
            })
        }
    }

    private fun isNightModeDetect(): Boolean {
        return requireActivity().applicationContext.resources.configuration.uiMode and
                UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    override fun updateThemes() {
        initThemePickerList()
        invalidateThemePickerButtonColors()
    }
}