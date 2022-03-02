package com.admiral.demo.features.home.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.ItemOnboardingBinding
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.foundation.ColorState

class OnboardingScreenFragment : BaseFragment(R.layout.item_onboarding) {

    private val binding by viewBinding(ItemOnboardingBinding::bind)

    private var position = 0
    private val titles =
        listOf("Добро\nпожаловать!", "Что внутри приложения", "Ждем ваших\n\uD83D\uDC4D\uD83C\uDFFD️")
    private val descriptions = listOf(
        "Приветствуем вас в нашем мобильном приложении.  " +
                "Это приложение служит удобным инструментом для участников любых команд!",
        "В приложении есть набор всех стилей и компонентов UI kit 3.0 Mobile, " +
                "который вы сможете протестировать в реальном времени.",
        "Мы рады любому фидбеку и активно ведем непрерывную работу над дизайн-системой." +
                "\nВсе наши контакты находятся в разделе “Инфо”."
    )
    private val images = listOf(
        "ic_onboarding_1", "ic_onboarding_2", "ic_onboarding_3"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            position = bundle.getInt(KEY_POSITION, 0)
        }

        val resourceId: Int = requireContext().resources.getIdentifier(
            images[position], "drawable",
            requireContext().packageName
        )

        binding.admiralOnboardingImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), resourceId))
        binding.admiralOnboardingTitle.text = titles[position]
        binding.admiralOnboardintDescription.text = descriptions[position]
        binding.admiralOnboardintDescription.textColor = ColorState(ThemeManager.theme.palette.textSecondary)
    }

    companion object {
        const val KEY_POSITION = "position"
    }
}