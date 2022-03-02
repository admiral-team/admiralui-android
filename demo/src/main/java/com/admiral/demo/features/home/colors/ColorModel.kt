package com.admiral.demo.features.home.colors

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ColorListModel

data class TitleModel(val title: String) : ColorListModel()

@Parcelize
data class ColorModel(
    val name: String,
    val hex: String,
    val color: Int
) : ColorListModel(), Parcelable