package com.admiral.demo.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.admiral.demo.R
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment(
    @LayoutRes val layoutId: Int,
    private val menuId: Int? = null
) : Fragment(layoutId) {

    open val isThemePickerVisible: Boolean = true
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationView = view.rootView.findViewById(R.id.bottomNavigationView)
        hideKeyboardOnClick(view.rootView)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menuId != null) inflater.inflate(menuId, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mainMenuInfo -> {
                bottomNavigationView?.selectedItemId = R.id.mainMenuInfo
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        bottomNavigationView = null
        super.onDestroyView()
    }

    fun registerToolbar(toolbar: Toolbar, hasOptionsMenu: Boolean, listener: () -> Unit) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(hasOptionsMenu)
        toolbar.setNavigationOnClickListener { listener.invoke() }
    }

    fun hideBackButton() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
    }

    fun showBackButton() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyboardOnClick(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            var startClickTime: Long = 0

            view.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    startClickTime = System.currentTimeMillis()
                } else if (event.action == MotionEvent.ACTION_UP) {
                    if ((System.currentTimeMillis() - startClickTime) < ViewConfiguration.getTapTimeout() * TAP_TIMEOUT_CONST) {
                        hideKeyboard(activity?.currentFocus)
                    }
                }
                false
            }
        }

        //If a layout is a container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyboardOnClick(innerView)
            }
        }
    }

    private fun hideKeyboard(view: View?) {
        if (view == null) return
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    private companion object {
        const val TAP_TIMEOUT_CONST = 1.8
    }
}