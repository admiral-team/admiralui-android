package com.admiral.demo.features.home.gun.presentation

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtGunBinding
import com.admiral.demo.features.home.gun.presentation.model.GunState
import com.admiral.demo.features.home.gun.presentation.recycler.GunAdapter
import com.admiral.demo.features.home.gun.presentation.recycler.GunSpacingDecorator
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.ext.pixels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GunFragment : BaseFragment(R.layout.fmt_gun) {
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val gunViewModel: GunFragmentVm by viewModels()

    private val binding by viewBinding(FmtGunBinding::bind)

    private val adapter: GunAdapter
        get() = binding.recycler.adapter as GunAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        initGunViewModel()
        initRecycler()
        initSwipeRefreshLayout()
        subscribeForStateUpdates()
    }

    // region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_gun, menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (gunViewModel.stateFlow.value is GunState.SearchState) {
            menu.findItem(R.id.gunOpenSearchTemplate).isVisible = false
            menu.findItem(R.id.gunSubmitSearchTemplate).isVisible = true
        } else {
            menu.findItem(R.id.gunOpenSearchTemplate).isVisible = true
            menu.findItem(R.id.gunSubmitSearchTemplate).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val idTemplate = binding.search.textFlow.value ?: ""

        return when (item.itemId) {
            R.id.gunOpenSearchTemplate -> {
                gunViewModel.onOpenSearchAction(idTemplate)
                true
            }
            R.id.gunSubmitSearchTemplate -> {
                hideKeyboard()
                gunViewModel.loadTemplate(idTemplate)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // endregion

    private fun initGunViewModel() {
        gunViewModel.init(requireContext())
    }

    private fun initRecycler() {
        binding.recycler.apply {
            adapter = GunAdapter(context) { id ->
                val state = gunViewModel.stateFlow.value as? GunState.SuccessState
                    ?: return@GunAdapter
                val description = state.gunTemplate.items.find { it.id == id }?.description
                Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
            }
            addItemDecoration(
                GunSpacingDecorator(
                    spacingInPx = pixels(R.dimen.gun_spacing_decorator_size)
                )
            )
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            val idTemplate = binding.search.textFlow.value ?: ""
            gunViewModel.loadTemplate(idTemplate)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun subscribeForStateUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                gunViewModel.stateFlow.collect { state ->
                    when (state) {
                        is GunState.ErrorState -> {
                            binding.search.isVisible = false
                            binding.recycler.isVisible = false
                            binding.spinner.isVisible = false
                            binding.error.apply {
                                text = state.description
                                isVisible = true
                            }
                            binding.swipeRefresh.isEnabled = true
                        }
                        GunState.LoadingState -> {
                            binding.search.isVisible = false
                            binding.recycler.isVisible = false
                            binding.spinner.isVisible = true
                            binding.error.isVisible = false
                            binding.swipeRefresh.isEnabled = false
                        }
                        is GunState.SearchState -> {
                            binding.search.isVisible = true
                            binding.recycler.isVisible = false
                            binding.spinner.isVisible = false
                            binding.error.isVisible = false
                            binding.swipeRefresh.isEnabled = false
                        }
                        is GunState.SuccessState -> {
                            binding.search.isVisible = false
                            binding.recycler.isVisible = true
                            binding.spinner.isVisible = false
                            binding.error.isVisible = false
                            binding.swipeRefresh.isEnabled = true
                            adapter.submitList(state.gunTemplate.items)
                        }
                    }
                    requireActivity().invalidateOptionsMenu()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.search.windowToken, 0)
    }
}