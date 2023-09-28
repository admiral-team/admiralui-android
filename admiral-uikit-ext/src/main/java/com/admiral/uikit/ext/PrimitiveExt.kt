package com.admiral.uikit.ext

internal fun Boolean?.orFalse(): Boolean = this ?: false

internal fun Int?.orZero(): Int = this ?: 0

fun Int.hex(): String = "#${Integer.toHexString(this).removePrefix("ff").toUpperCase()}"