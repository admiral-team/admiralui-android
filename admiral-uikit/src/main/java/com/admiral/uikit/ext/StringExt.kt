package com.admiral.uikit.ext

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

private const val SPACE_NBSP = " "
private const val MONEY_PATTERN = "#,###"
private const val EMPTY = ""
private const val SPACE = " "
private const val COMMA = ","
private const val DOT = "."

/**
 * Format sum according to Ru locale
 *
 * @param money sum
 * @param roundingMode rounding mode
 * @param locale pair of locales of language and country
 */
internal fun formatMoney(
    money: Float?,
    roundingMode: RoundingMode? = RoundingMode.HALF_UP,
    locale: Pair<String, String>? = Pair("ru", "RU")
): String {
    val notNullMoney: Float = money ?: 0.0f
    val localeDefault = locale?.takeIf { it.first.isNotBlank() && it.second.isNotBlank() } ?: ("ru" to "RU")
    return (NumberFormat.getNumberInstance(Locale(localeDefault.first, localeDefault.second)) as DecimalFormat)
        .apply {
            applyPattern(MONEY_PATTERN)
            roundingMode?.let { setRoundingMode(it) }
        }
        .format(notNullMoney)
}

/** Format string to cast Float volume */
internal fun String.formatStringToFloat(): Float {
    if (this.isEmpty()) return 0f
    return this.replace(SPACE, EMPTY).replace(SPACE_NBSP, EMPTY).replace(COMMA, DOT).toFloat()
}