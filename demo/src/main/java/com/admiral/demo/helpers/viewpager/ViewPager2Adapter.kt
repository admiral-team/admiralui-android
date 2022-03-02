package com.admiral.demo.helpers.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(
    fragment: Fragment,
    val fragmentList: MutableList<Pair<String, ViewPagerFragment>>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].second
    }

    override fun getItemId(position: Int): Long {
        return fragmentList[position].second.hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragmentList.find { it.hashCode().toLong() == itemId } != null
    }

    fun add(fragment: Pair<String, ViewPagerFragment>) {
        fragmentList.add(fragment)
        notifyItemInserted(itemCount)
    }

    fun add(index: Int, fragment: Pair<String, ViewPagerFragment>) {
        fragmentList.add(index, fragment)
        notifyItemInserted(index)
    }

    fun remove(index: Int) {
        fragmentList.removeAt(index)
        notifyItemRemoved(index)
    }
}