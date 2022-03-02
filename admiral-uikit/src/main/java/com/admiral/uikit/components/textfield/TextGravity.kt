package com.admiral.uikit.components.textfield

import android.view.Gravity

sealed class TextGravity(val gravity: Int) {

    object Start : TextGravity(Gravity.START)
    object Center : TextGravity(Gravity.CENTER)
    object End : TextGravity(Gravity.END)

    companion object Factory {

        fun fromGravity(gravity: Int): TextGravity {

            return when (gravity) {
                0 -> Start
                1 -> Center
                2 -> End
                else -> Start
            }
        }
    }
}
