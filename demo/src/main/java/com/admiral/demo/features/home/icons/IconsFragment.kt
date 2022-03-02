package com.admiral.demo.features.home.icons

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtIconsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.model.BaseItem
import com.admiral.demo.model.icon.Icon
import com.admiral.demo.model.icon.IconCategoryListItem
import com.admiral.demo.model.icon.IconItemTypes.ICON
import com.admiral.demo.model.icon.IconItemTypes.ICON_CATEGORY
import com.admiral.demo.model.icon.IconListItem
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.ext.spToPx
import com.admiral.uikit.view.checkable.CheckableGroup
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.InputStreamReader

class IconsFragment : BaseFragment(R.layout.fmt_icons), ThemeObserver {

    private val binding by viewBinding(FmtIconsBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private lateinit var adapter: IconsListAdapter

    private var filter = ""
    private var iconsMode = IconsMode.OUTLINE
    private var categories: List<Icon>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, false, navigationViewModel::close)
        initList()
        initData()

        var drawableResources = getIconsFiltered()
        adapter.submitList(drawableResources)

        binding.tabLayout.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                iconsMode = when (checkedId) {
                    R.id.tabOutline -> {
                        IconsMode.OUTLINE
                    }
                    R.id.tabSolid -> {
                        IconsMode.SOLID
                    }
                    else -> IconsMode.OUTLINE
                }

                drawableResources = getIconsFiltered()
                adapter.submitList(drawableResources)
            }
        }

        binding.search.textFlow.debounce(DEBOUNCE_TIME)
            .distinctUntilChanged()
            .onEach {
                it?.let {
                    filter = it.toString()
                    drawableResources = getIconsFiltered()
                    adapter.submitList(drawableResources)
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun initData() {
        requireContext().assets.open(ICONS_ASSETS_FILE_NAME).use {
            val typeToken = object : TypeToken<List<Icon>>() {}.type
            categories = Gson().fromJson(InputStreamReader(it), typeToken)
        }
        iconsMode = IconsMode.OUTLINE
    }

    private fun getIconsFiltered(): ArrayList<BaseItem> {
        val iconsList = ArrayList<BaseItem>()

        categories
            ?.groupBy { it.type }
            ?.forEach {
                val iconsFiltered: MutableList<BaseItem> = mutableListOf()
                for (icon in it.value) {
                    val name = icon.id
                    if (name.contains(ADMIRAL_ICONS_PREFIX) &&
                        name.contains(iconsMode.modeName) &&
                        name.contains(filter)
                    ) {
                        iconsFiltered.add(IconListItem(icon))
                    }
                }

                if (iconsFiltered.isNotEmpty()) {
                    iconsList.add(IconCategoryListItem(it.key))
                }
                iconsList.addAll(iconsFiltered)
            }

        return iconsList
    }

    private fun initList() {
        adapter = IconsListAdapter { viewX, viewY, name ->
            binding.informer.info = name
            binding.informer.isVisible = true
            val infoWidth = name.length * CHARS_LENGTH.spToPx(requireContext())

            if (infoWidth + viewX + INFORMER_SAVE_MARGIN_RIGHT.spToPx(requireContext()) < binding.root.width) {
                binding.informer.x = viewX
                binding.informer.y = viewY + INFORMER_MARGIN_TOP.spToPx(requireContext())
                binding.informer.pointerBias = INFORMER_BIAS_LEFT
            } else {
                binding.informer.x = viewX - infoWidth
                binding.informer.y = viewY + INFORMER_MARGIN_TOP.spToPx(requireContext())
                binding.informer.pointerBias = INFORMER_BIAS_RIGHT
            }
        }

        binding.rvIcons.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    ICON.type -> SPAN_COUNT_ICON
                    ICON_CATEGORY.type -> SPAN_COUNT
                    else -> -1
                }
            }
        }
        binding.rvIcons.layoutManager = gridLayoutManager
        binding.rvIcons.adapter = adapter

        binding.rvIcons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.informer.isVisible = false
            }
        })

        ThemeManager.subscribe(this)
    }

    private companion object {
        const val INFORMER_SAVE_MARGIN_RIGHT = 50
        const val INFORMER_MARGIN_TOP = 15
        const val INFORMER_BIAS_LEFT = 0.1f
        const val INFORMER_BIAS_RIGHT = 0.9f
        const val CHARS_LENGTH = 8
        const val SPAN_COUNT = 5
        const val SPAN_COUNT_ICON = 1
        const val DEBOUNCE_TIME = 300L
        const val ADMIRAL_ICONS_PREFIX = "admiral_ic"
        const val ICONS_ASSETS_FILE_NAME = "admiral_icons.json"
    }

    override fun onPause() {
        super.onPause()
        ThemeManager.unsubscribe(this)
    }

    override fun onThemeChanged(theme: Theme) {
        adapter.notifyDataSetChanged()
        binding.rvIcons.invalidate()
    }
}

enum class IconsMode(val modeName: String) {
    SOLID("solid"),
    OUTLINE("outline")
}