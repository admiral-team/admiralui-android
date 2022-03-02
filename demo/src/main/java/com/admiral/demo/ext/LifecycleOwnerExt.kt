package com.admiral.demo.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T : Any, reified L : LiveData<T>> LifecycleOwner.observe(
    liveData: L,
    noinline block: (T) -> Unit
) = liveData.observe(this, Observer(block::invoke))