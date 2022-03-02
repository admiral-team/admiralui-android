package com.admiral.demo.ext

import com.google.gson.Gson

inline fun <reified T> String.toModel(): T? {
    return Gson().fromJson(this, T::class.java)
}