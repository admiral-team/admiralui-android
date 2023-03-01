package com.admiral.demo.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtViewPagerContainerBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.helpers.viewpager.ViewPager2Adapter
import com.admiral.demo.helpers.viewpager.ViewPagerAdapter
import com.admiral.demo.helpers.viewpager.ViewPagerFragment

class ViewPagerFragment : BaseFragment(R.layout.fmt_view_pager_container) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtViewPagerContainerBinding::bind)

    private val fragments = mutableListOf(
        Pair("1", ViewPagerFragment().apply {
            arguments = bundleOf(Pair("TEXT", "1"))
        }),
        Pair("2", ViewPagerFragment().apply {
            arguments = bundleOf(Pair("TEXT", "2"))
        }),
        Pair("3", ViewPagerFragment().apply {
            arguments = bundleOf(Pair("TEXT", "3"))
        })
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        invalidateViewPager2()
    }

    private fun invalidateViewPager2() {
        with(binding) {
            viewPager.isVisible = false
            viewPager2.isVisible = true

            val adapter = ViewPager2Adapter(requireParentFragment(), fragments)
            viewPager2.adapter = adapter

            underlineTabs.setupWithViewPager(viewPager2) { tab, position ->
                tab.text = fragments[position].first
            }

            outlineTabs.setupWithViewPager(viewPager2) { tab, position ->
                tab.text = fragments[position].first
            }

            standardTabs.setupWithViewPager(viewPager2) { tab, position ->
                tab.text = fragments[position].first
            }

            btn.setOnClickListener {
                adapter.add(Pair((adapter.itemCount + 1).toString(), ViewPagerFragment().apply {
                    arguments = bundleOf(Pair("TEXT", (adapter.itemCount + 1).toString()))
                }))
            }
        }
    }

    private fun invalidateViewPager1() {
        with(binding) {
            viewPager.isVisible = true
            viewPager2.isVisible = false

            val adapter = ViewPagerAdapter(fragments, childFragmentManager)
            viewPager.adapter = adapter

            underlineTabs.setupWithViewPager(viewPager)
            outlineTabs.setupWithViewPager(viewPager)
            standardTabs.setupWithViewPager(viewPager)

            btn.setOnClickListener {
                adapter.add(
                    Pair((adapter.count + 1).toString(), ViewPagerFragment().apply {
                        arguments = bundleOf(Pair("TEXT", (adapter.count + 1).toString()))
                    })
                )
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}