package com.admiral.demo.ext

import android.content.Context
import java.io.IOException
import java.io.InputStream

fun Context.loadStringFromAssets(assetFileName: String): String? {
    return try {
        val inputStream: InputStream = this.assets.open(assetFileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}
