package com.admiral.demo.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.admiral.demo.screen.Screen

class NavigationViewModel : ViewModel() {

    private val open: MutableLiveData<Screen> = MutableLiveData()
    private val close: MutableLiveData<String> = MutableLiveData()

    fun openScreen(): LiveData<Screen> = open
    fun closeScreen(): LiveData<String> = close

    fun open(screen: Screen) {
        open.value = screen
    }

    fun close() {
        close.value = "close"
    }
}