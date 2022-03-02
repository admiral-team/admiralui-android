package com.admiral.demo.ext

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.withArgs(init: Bundle.() -> Unit): Fragment {
    val args = Bundle()
    init.invoke(args)
    arguments = args

    return this
}