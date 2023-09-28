package com.admiral.uikit.core.ext

internal fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun Int.hex(): String = "#${Integer.toHexString(this).removePrefix("ff").toUpperCase()}"