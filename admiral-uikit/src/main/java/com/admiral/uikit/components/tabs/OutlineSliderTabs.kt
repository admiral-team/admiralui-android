package com.admiral.uikit.components.tabs

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.admiral.uikit.R
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.view.checkable.CheckableGroup

class OutlineSliderTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableGroup(context, attrs, defStyleAttr) {

    private var tabConfigurationStrategy: TabConfigurationStrategy? = null

    init {
        updatePadding(
            left = context.pixels(R.dimen.module_x4),
            top = context.pixels(R.dimen.module_x2),
            right = context.pixels(R.dimen.module_x2),
            bottom = context.pixels(R.dimen.module_x2)
        )

        orientation = HORIZONTAL
        showDividers = SHOW_DIVIDER_MIDDLE
        dividerDrawable = ContextCompat.getDrawable(
            context,
            R.drawable.admiral_devider_space_horizontal_8dp
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }

    fun setupWithViewPager(viewPager: ViewPager, tabConfigurationStrategy: TabConfigurationStrategy? = null) {
        this.tabConfigurationStrategy = tabConfigurationStrategy

        populate(viewPager)
        viewPager.addOnAdapterChangeListener(AdapterChangeListener())

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                check(getChildAt(position).id)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

    }

    inner class AdapterChangeListener : ViewPager.OnAdapterChangeListener {
        override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
            populate(viewPager)
        }
    }

    inner class PagerAdapterObserver(private val viewPager: ViewPager) : DataSetObserver() {
        override fun onChanged() {
            populate(viewPager)
        }

        override fun onInvalidated() {
            children.forEach {
                it.invalidate()
                it.requestLayout()
            }
        }
    }

    private fun populate(viewPager: ViewPager) {
        val onClick: (View) -> Unit = { view ->
            viewPager.setCurrentItem(indexOfChild(view), true)
        }
        removeAllViews()

        val adapter = viewPager.adapter
        adapter?.let {
            adapter.registerDataSetObserver(PagerAdapterObserver(viewPager))
        }

        removeAllViews()

        val adapterCount: Int = adapter?.count ?: 0
        for (i in 0 until adapterCount) {
            addView(OutlineSliderTab(context).apply {
                text = adapter?.getPageTitle(i).toString()

                configureOutlineTab(onClick, tabConfigurationStrategy, i)
            })
        }

        if (childCount != 0) {
            val currentItem = viewPager.currentItem
            val childAt = getChildAt(currentItem)
            val id = childAt.id
            check(id)
        }
    }

    fun setupWithViewPager(
        viewPager: ViewPager2,
        tabConfigurationStrategy: TabConfigurationStrategy
    ) {
        checkNotNull(viewPager.adapter) {
            "TabLayoutMediator attached before ViewPager2 has an adapter"
        }
        populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)

        viewPager.registerOnPageChangeCallback(TabLayoutOnPageChangeCallback())
        viewPager.adapter?.registerAdapterDataObserver(Pager2AdapterObserver(viewPager, tabConfigurationStrategy))
    }

    inner class TabLayoutOnPageChangeCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            getChildAt(position)?.id?.let {
                check(id)
            }
        }
    }

    inner class Pager2AdapterObserver(
        private val viewPager: ViewPager2,
        private val tabConfigurationStrategy: TabConfigurationStrategy
    ) : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            populateTabsFromPagerAdapter(viewPager, tabConfigurationStrategy)
        }
    }

    private fun populateTabsFromPagerAdapter(
        viewPager: ViewPager2,
        tabConfigurationStrategy: TabConfigurationStrategy
    ) {
        val onClick: (View) -> Unit = { view ->
            viewPager.setCurrentItem(indexOfChild(view), true)
        }
        removeAllViews()

        val adapter = viewPager.adapter
        if (adapter != null) {
            val adapterCount: Int = adapter.itemCount
            for (item in 0 until adapterCount) {
                addView(OutlineSliderTab(context).apply {
                    configureOutlineTab(onClick, tabConfigurationStrategy, item)
                })
            }
        }

        if (childCount != 0) {
            val currentItem = viewPager.currentItem
            val childAt = getChildAt(currentItem)
            val id = childAt.id
            check(id)
        }
    }

    private fun OutlineSliderTab.configureOutlineTab(
        onClick: (View) -> Unit,
        tabConfigurationStrategy: TabConfigurationStrategy?,
        i: Int
    ) {
        val lp =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.weight = 1f
        layoutParams = lp

        setOnClickListener {
            onClick(it)
        }

        tabConfigurationStrategy?.onConfigureTab(this, i)
    }

    /**
     * A callback interface that must be implemented to set the text and styling of newly created
     * tabs.
     */
    fun interface TabConfigurationStrategy {
        /**
         * Called to configure the tab for the page at the specified position.
         * Typically calls [OutlineSliderTab.getText], but any form of styling can be applied.
         *
         * @param tab The [OutlineSliderTab] which should be configured to represent the title
         * of the item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        fun onConfigureTab(tab: OutlineSliderTab, position: Int)
    }
}
