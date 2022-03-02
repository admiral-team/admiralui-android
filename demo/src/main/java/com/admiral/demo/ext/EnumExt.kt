package com.admiral.demo.ext

import android.content.Intent
import android.os.Bundle

inline fun <reified T : Enum<T>> Intent.putEnumExtra(name: String, value: T) {
    putExtra(name, value.ordinal)
}

inline fun <reified T : Enum<T>> Intent.getEnumExtra(name: String, defaultValue: T? = null): T? {
    val extra = getIntExtra(name, -1)
    return if (extra == -1) defaultValue else enumValues<T>()[extra]
}

inline fun <reified T : Enum<T>> Bundle.putEnumExtra(name: String, value: T) {
    putInt(name, value.ordinal)
}

inline fun <reified T : Enum<T>> Bundle.getEnumExtra(name: String, defaultValue: T): T {
    val extra = getInt(name, -1)
    return if (extra == -1) defaultValue else enumValues<T>()[extra]
}