package com.admiral.demo.helpers.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val myFragments: MutableList<Pair<String, ViewPagerFragment>>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return myFragments.count()
    }

    override fun getItem(position: Int): Fragment {
        return myFragments[position].second
    }

    override fun getPageTitle(position: Int): CharSequence {
        return position.toString()
    }

    fun add(c: Pair<String, ViewPagerFragment>) {
        myFragments.add(c)
    }
}