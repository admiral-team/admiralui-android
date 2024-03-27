package com.admiral.themes.compose

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

internal val LocalAdmiralColors = compositionLocalOf<AdmiralColors> {
    error("No AdmiralColorPalette provided")
}

@Suppress("MagicNumber")
object AdmiralPalette {
    //  ===== Light =====
    // Neutral
    val admiralWhiteLight = Color(255, 255, 255)
    val admiralGrey05Light = Color(243, 244, 247)
    val admiralGrey10Light = Color(228, 232, 238)
    val admiralGrey20Light = Color(213, 216, 223)
    val admiralGrey30Light = Color(176, 181, 190)
    val admiralGrey40Light = Color(153, 158, 169)
    val admiralGrey50Light = Color(121, 126, 139)
    val admiralGrey60Light = Color(103, 108, 119)
    val admiralGrey70Light = Color(81, 87, 100)
    val admiralGrey80Light = Color(53, 56, 67)
    val admiralGrey90Light = Color(33, 36, 44)
    val admiralGreyBlackLight = Color(0, 0, 0)

    // Primary
    val admiralBlue10Light = Color(235, 243, 254)
    val admiralBlue20Light = Color(216, 230, 252)
    val admiralBlue30Light = Color(176, 205, 249)
    val admiralBlue40Light = Color(137, 181, 247)
    val admiralBlue50Light = Color(98, 156, 244)
    val admiralBlue60Light = Color(58, 131, 241)
    val admiralBlue70Light = Color(49, 111, 204)
    val admiralBlue80Light = Color(34, 80, 148)
    val admiralBlue90Light = Color(20, 49, 92)
    val admiralBlue100Light = Color(11, 29, 55)

    // Error
    val admiralRed10Light = Color(255, 235, 234)
    val admiralRed20Light = Color(255, 216, 214)
    val admiralRed30Light = Color(255, 177, 172)
    val admiralRed40Light = Color(255, 137, 131)
    val admiralRed50Light = Color(255, 98, 89)
    val admiralRed60Light = Color(255, 59, 48)
    val admiralRed70Light = Color(214, 47, 38)
    val admiralRed80Light = Color(152, 29, 24)
    val admiralRed90Light = Color(91, 12, 10)
    val admiralRed100Light = Color(50, 0, 0)

    // Success
    val admiralGreen10Light = Color(236, 249, 241)
    val admiralGreen20Light = Color(218, 242, 228)
    val admiralGreen30Light = Color(180, 229, 201)
    val admiralGreen40Light = Color(143, 217, 174)
    val admiralGreen50Light = Color(106, 204, 147)
    val admiralGreen60Light = Color(68, 191, 120)
    val admiralGreen70Light = Color(51, 152, 93)
    val admiralGreen80Light = Color(38, 121, 71)
    val admiralGreen90Light = Color(17, 74, 39)
    val admiralGreen100Light = Color(0, 35, 12)

    // Attention
    val admiralOrange10Light = Color(255, 239, 215)
    val admiralOrange20Light = Color(255, 223, 175)
    val admiralOrange30Light = Color(255, 207, 135)
    val admiralOrange40Light = Color(255, 191, 95)
    val admiralOrange50Light = Color(255, 175, 55)
    val admiralOrange60Light = Color(251, 145, 48)
    val admiralOrange70Light = Color(194, 107, 34)
    val admiralOrange80Light = Color(156, 81, 24)
    val admiralOrange90Light = Color(99, 43, 10)
    val admiralOrange100Light = Color(61, 17, 0)

    // ===== Dark =====
    // Neutral
    val admiralGrey00Dark = Color(19, 21, 27)
    val admiralGrey05Dark = Color(26, 28, 36)
    val admiralGrey10Dark = Color(35, 38, 47)
    val admiralGrey20Dark = Color(55, 59, 73)
    val admiralGrey30Dark = Color(88, 94, 106)
    val admiralGrey40Dark = Color(120, 126, 141)
    val admiralGrey50Dark = Color(153, 161, 177)
    val admiralGrey60Dark = Color(179, 186, 199)
    val admiralGrey70Dark = Color(216, 220, 228)
    val admiralGrey80Dark = Color(227, 232, 240)
    val admiralGrey90Dark = Color(243, 244, 247)
    val admiralWhiteDark = Color(255, 255, 255)

    // Primary
    val admiralBlue10Dark = Color(23, 32, 48)
    val admiralBlue20Dark = Color(27, 43, 70)
    val admiralBlue30Dark = Color(35, 65, 113)
    val admiralBlue40Dark = Color(43, 87, 155)
    val admiralBlue50Dark = Color(50, 109, 198)
    val admiralBlue60Dark = Color(58, 131, 241)
    val admiralBlue70Dark = Color(92, 151, 241)
    val admiralBlue80Dark = Color(126, 171, 240)
    val admiralBlue90Dark = Color(160, 192, 239)
    val admiralBlue100Dark = Color(194, 212, 239)

    // Error
    val admiralRed10Dark = Color(40, 26, 30)
    val admiralRed20Dark = Color(61, 31, 34)
    val admiralRed30Dark = Color(104, 41, 41)
    val admiralRed40Dark = Color(146, 50, 48)
    val admiralRed50Dark = Color(189, 60, 55)
    val admiralRed60Dark = Color(231, 70, 62)
    val admiralRed70Dark = Color(230, 102, 98)
    val admiralRed80Dark = Color(229, 135, 133)
    val admiralRed90Dark = Color(229, 167, 169)
    val admiralRed100Dark = Color(228, 200, 204)

    // Success
    val admiralGreen10Dark = Color(23, 35, 34)
    val admiralGreen20Dark = Color(27, 48, 42)
    val admiralGreen30Dark = Color(35, 75, 57)
    val admiralGreen40Dark = Color(43, 103, 71)
    val admiralGreen50Dark = Color(51, 130, 86)
    val admiralGreen60Dark = Color(59, 157, 101)
    val admiralGreen70Dark = Color(68, 191, 120)
    val admiralGreen80Dark = Color(100, 199, 143)
    val admiralGreen90Dark = Color(132, 208, 167)
    val admiralGreen100Dark = Color(164, 216, 191)

    // Attention
    val admiralOrange10Dark = Color(37, 32, 32)
    val admiralOrange20Dark = Color(56, 44, 37)
    val admiralOrange30Dark = Color(93, 67, 47)
    val admiralOrange40Dark = Color(129, 90, 56)
    val admiralOrange50Dark = Color(166, 113, 66)
    val admiralOrange60Dark = Color(203, 136, 76)
    val admiralOrange70Dark = Color(249, 165, 88)
    val admiralOrange80Dark = Color(245, 178, 118)
    val admiralOrange90Dark = Color(241, 192, 148)
    val admiralOrange100Dark = Color(236, 205, 178)

    //  ===== Special =====
    val admiralVioletLight = Color(237, 108, 205)
    val admiralViolet = Color(195, 104, 218)
    val admiralVioletDeep = Color(154, 100, 247)
    val admiralFuchsia = Color(248, 109, 134)
    val admiralFuchsiaDeep = Color(217, 71, 124)
    val admiralYellow = Color(240, 222, 55)
    val admiralOrangeLight = Color(255, 175, 55)
    val admiralOrange = Color(255, 133, 21)
    val admiralOrangeDeep = Color(255, 90, 50)
    val admiralGreenLight = Color(192, 210, 15)
    val admiralGreen = Color(94, 207, 112)
    val admiralTiffany = Color(91, 216, 209)
    val admiralTiffanyDeep = Color(36, 167, 159)
    val admiralTiffanyDark = Color(20, 127, 148)
    val admiralTiffanyBlue = Color(0, 188, 212)
    val admiralBlueLight = Color(10, 187, 251)
    val admiralBlue = Color(63, 125, 254)
    val admiralBlueDeep = Color(62, 81, 181)
    val admiralSoftBlue = Color(224, 242, 255)
    val admiralSoftSkyBlue = Color(226, 244, 254)
    val admiralSoftTiffany = Color(219, 248, 255)
    val admiralSoftGreen = Color(216, 245, 232)
    val admiralSoftPurple = Color(237, 241, 255)
    val admiralSoftPink = Color(255, 235, 248)
    val admiralSoftRed = Color(255, 235, 237)
    val admiralSoftPapaya = Color(249, 238, 207)
    val admiralSoftYellow = Color(255, 241, 212)
}

@Suppress("LongParameterList")
@Stable
class AdmiralColors constructor(
    backgroundBasic: Color,
    backgroundAdditionalOne: Color,
    backgroundAdditionalOnePressed: Color,
    backgroundAdditionalTwo: Color,
    backgroundAdditionalTwoPressed: Color,
    backgroundModalView: Color,
    backgroundModalControl: Color,
    backgroundAccent: Color,
    backgroundAccentTwo: Color,
    backgroundAccentPressed: Color,
    backgroundSelected: Color,
    backgroundSelectedPressed: Color,
    backgroundAccentDark: Color,
    backgroundError: Color,
    backgroundErrorPressed: Color,
    backgroundSuccess: Color,
    backgroundSuccessPressed: Color,
    backgroundAttention: Color,
    backgroundAttentionPressed: Color,
    backgroundSecondary: Color,
    backgroundExtraSurface: Color,
    backgroundShadow: Color,

    textPrimary: Color,
    textSecondary: Color,
    textAdditional: Color,
    textMask: Color,
    textStaticWhite: Color,
    textContrast: Color,
    textAccent: Color,
    textAccentPressed: Color,
    textAccentAdditional: Color,
    textError: Color,
    textErrorPressed: Color,
    textSuccess: Color,
    textSuccessPressed: Color,
    textAttention: Color,
    textAttentionPressed: Color,

    elementPrimary: Color,
    elementSecondary: Color,
    elementBarDivider: Color,
    elementAdditional: Color,
    elementStaticWhite: Color,
    elementContrast: Color,
    elementExtra: Color,
    elementAccent: Color,
    elementAccentPressed: Color,
    elementAccentAdditional: Color,
    elementError: Color,
    elementErrorPressed: Color,
    elementSuccess: Color,
    elementSuccessPressed: Color,
    elementAttention: Color,
    elementAttentionPressed: Color,
    specialExtra01: Color,
    specialExtra02: Color,
    specialExtra03: Color,
    specialExtra04: Color,
    specialExtra05: Color,
    specialExtra06: Color,
    specialExtra07: Color,
    specialExtra08: Color,
    specialExtra09: Color,
    specialExtra10: Color,
    specialExtra11: Color,
    specialExtra12: Color,
    specialExtra13: Color,
    specialExtra14: Color,
    specialExtra15: Color,
    specialExtra16: Color,
    specialExtra17: Color,
    specialExtra18: Color,
    specialExtra19: Color,
    specialExtra20: Color,
    specialExtra21: Color,
    specialExtra22: Color,
    specialExtra23: Color,
    specialExtra24: Color,
    specialExtra25: Color,
    specialExtra26: Color,
    specialExtra27: Color,
) {
    var backgroundBasic by mutableStateOf(backgroundBasic)
        private set
    var backgroundAdditionalOne by mutableStateOf(backgroundAdditionalOne)
        private set
    var backgroundAdditionalOnePressed by mutableStateOf(backgroundAdditionalOnePressed)
        private set
    var backgroundAdditionalTwo by mutableStateOf(backgroundAdditionalTwo)
        private set
    var backgroundAdditionalTwoPressed by mutableStateOf(backgroundAdditionalTwoPressed)
        private set
    var backgroundModalView by mutableStateOf(backgroundModalView)
        private set
    var backgroundModalControl by mutableStateOf(backgroundModalControl)
        private set
    var backgroundAccent by mutableStateOf(backgroundAccent)
        private set
    var backgroundAccentTwo by mutableStateOf(backgroundAccentTwo)
        private set
    var backgroundAccentPressed by mutableStateOf(backgroundAccentPressed)
        private set
    var backgroundSelected by mutableStateOf(backgroundSelected)
        private set
    var backgroundSelectedPressed by mutableStateOf(backgroundSelectedPressed)
        private set
    var backgroundAccentDark by mutableStateOf(backgroundAccentDark)
        private set
    var backgroundError by mutableStateOf(backgroundError)
        private set
    var backgroundErrorPressed by mutableStateOf(backgroundErrorPressed)
        private set
    var backgroundSuccess by mutableStateOf(backgroundSuccess)
        private set
    var backgroundSuccessPressed by mutableStateOf(backgroundSuccessPressed)
        private set
    var backgroundAttention by mutableStateOf(backgroundAttention)
        private set
    var backgroundAttentionPressed by mutableStateOf(backgroundAttentionPressed)
        private set
    var backgroundSecondary by mutableStateOf(backgroundSecondary)
        private set
    var backgroundExtraSurface by mutableStateOf(backgroundExtraSurface)
        private set
    var backgroundShadow by mutableStateOf(backgroundShadow)
        private set

    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textAdditional by mutableStateOf(textAdditional)
        private set
    var textMask by mutableStateOf(textMask)
        private set
    var textStaticWhite by mutableStateOf(textStaticWhite)
        private set
    var textContrast by mutableStateOf(textContrast)
        private set
    var textAccent by mutableStateOf(textAccent)
        private set
    var textAccentPressed by mutableStateOf(textAccentPressed)
        private set
    var textAccentAdditional by mutableStateOf(textAccentAdditional)
        private set
    var textError by mutableStateOf(textError)
        private set
    var textErrorPressed by mutableStateOf(textErrorPressed)
        private set
    var textSuccess by mutableStateOf(textSuccess)
        private set
    var textSuccessPressed by mutableStateOf(textSuccessPressed)
        private set
    var textAttention by mutableStateOf(textAttention)
        private set
    var textAttentionPressed by mutableStateOf(textAttentionPressed)
        private set

    var elementPrimary by mutableStateOf(elementPrimary)
        private set
    var elementSecondary by mutableStateOf(elementSecondary)
        private set
    var elementBarDivider by mutableStateOf(elementBarDivider)
        private set
    var elementAdditional by mutableStateOf(elementAdditional)
        private set
    var elementStaticWhite by mutableStateOf(elementStaticWhite)
        private set
    var elementContrast by mutableStateOf(elementContrast)
        private set
    var elementExtra by mutableStateOf(elementExtra)
        private set
    var elementAccent by mutableStateOf(elementAccent)
        private set
    var elementAccentPressed by mutableStateOf(elementAccentPressed)
        private set
    var elementAccentAdditional by mutableStateOf(elementAccentAdditional)
        private set
    var elementError by mutableStateOf(elementError)
        private set
    var elementErrorPressed by mutableStateOf(elementErrorPressed)
        private set
    var elementSuccess by mutableStateOf(elementSuccess)
        private set
    var elementSuccessPressed by mutableStateOf(elementSuccessPressed)
        private set
    var elementAttention by mutableStateOf(elementAttention)
        private set
    var elementAttentionPressed by mutableStateOf(elementAttentionPressed)
        private set

    var specialExtra01 by mutableStateOf(specialExtra01)
        private set
    var specialExtra02 by mutableStateOf(specialExtra02)
        private set
    var specialExtra03 by mutableStateOf(specialExtra03)
        private set
    var specialExtra04 by mutableStateOf(specialExtra04)
        private set
    var specialExtra05 by mutableStateOf(specialExtra05)
        private set
    var specialExtra06 by mutableStateOf(specialExtra06)
        private set
    var specialExtra07 by mutableStateOf(specialExtra07)
        private set
    var specialExtra08 by mutableStateOf(specialExtra08)
        private set
    var specialExtra09 by mutableStateOf(specialExtra09)
        private set
    var specialExtra10 by mutableStateOf(specialExtra10)
        private set
    var specialExtra11 by mutableStateOf(specialExtra11)
        private set
    var specialExtra12 by mutableStateOf(specialExtra12)
        private set
    var specialExtra13 by mutableStateOf(specialExtra13)
        private set
    var specialExtra14 by mutableStateOf(specialExtra14)
        private set
    var specialExtra15 by mutableStateOf(specialExtra15)
        private set
    var specialExtra16 by mutableStateOf(specialExtra16)
        private set
    var specialExtra17 by mutableStateOf(specialExtra17)
        private set
    var specialExtra18 by mutableStateOf(specialExtra18)
        private set
    var specialExtra19 by mutableStateOf(specialExtra19)
        private set
    var specialExtra20 by mutableStateOf(specialExtra20)
        private set
    var specialExtra21 by mutableStateOf(specialExtra21)
        private set
    var specialExtra22 by mutableStateOf(specialExtra22)
        private set
    var specialExtra23 by mutableStateOf(specialExtra23)
        private set
    var specialExtra24 by mutableStateOf(specialExtra24)
        private set
    var specialExtra25 by mutableStateOf(specialExtra25)
        private set
    var specialExtra26 by mutableStateOf(specialExtra26)
        private set
    var specialExtra27 by mutableStateOf(specialExtra27)
        private set

    @Suppress("LongParameterList", "LongMethod")
    fun copy(
        backgroundBasic: Color = this.backgroundBasic,
        backgroundAdditionalOne: Color = this.backgroundAdditionalOne,
        backgroundAdditionalOnePressed: Color = this.backgroundAdditionalOnePressed,
        backgroundAdditionalTwo: Color = this.backgroundAdditionalTwo,
        backgroundAdditionalTwoPressed: Color = this.backgroundAdditionalTwoPressed,
        backgroundModalView: Color = this.backgroundModalView,
        backgroundModalControl: Color = this.backgroundModalControl,
        backgroundAccent: Color = this.backgroundAccent,
        backgroundAccentTwo: Color = this.backgroundAccentTwo,
        backgroundAccentPressed: Color = this.backgroundAccentPressed,
        backgroundSelected: Color = this.backgroundSelected,
        backgroundSelectedPressed: Color = this.backgroundSelectedPressed,
        backgroundAccentDark: Color = this.backgroundAccentDark,
        backgroundError: Color = this.backgroundError,
        backgroundErrorPressed: Color = this.backgroundErrorPressed,
        backgroundSuccess: Color = this.backgroundSuccess,
        backgroundSuccessPressed: Color = this.backgroundSuccessPressed,
        backgroundAttention: Color = this.backgroundAttention,
        backgroundAttentionPressed: Color = this.backgroundAttentionPressed,
        backgroundSecondary: Color = this.backgroundSecondary,
        backgroundExtraSurface: Color = this.backgroundExtraSurface,
        backgroundShadow: Color = this.backgroundShadow,

        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textAdditional: Color = this.textAdditional,
        textMask: Color = this.textMask,
        textStaticWhite: Color = this.textStaticWhite,
        textContrast: Color = this.textContrast,
        textAccent: Color = this.textAccent,
        textAccentPressed: Color = this.textAccentPressed,
        textAccentAdditional: Color = this.textAccentAdditional,
        textError: Color = this.textError,
        textErrorPressed: Color = this.textErrorPressed,
        textSuccess: Color = this.textSuccess,
        textSuccessPressed: Color = this.textSuccessPressed,
        textAttention: Color = this.textAttention,
        textAttentionPressed: Color = this.textAttentionPressed,

        elementPrimary: Color = this.elementPrimary,
        elementSecondary: Color = this.elementSecondary,
        elementBarDivider: Color = this.elementBarDivider,
        elementAdditional: Color = this.elementAdditional,
        elementStaticWhite: Color = this.elementStaticWhite,
        elementContrast: Color = this.elementContrast,
        elementExtra: Color = this.elementExtra,
        elementAccent: Color = this.elementAccent,
        elementAccentPressed: Color = this.elementAccentPressed,
        elementAccentAdditional: Color = this.elementAccentAdditional,
        elementError: Color = this.elementError,
        elementErrorPressed: Color = this.elementErrorPressed,
        elementSuccess: Color = this.elementSuccess,
        elementSuccessPressed: Color = this.elementSuccessPressed,
        elementAttention: Color = this.elementAttention,
        elementAttentionPressed: Color = this.elementAttentionPressed,

        specialExtra01: Color = this.specialExtra01,
        specialExtra02: Color = this.specialExtra02,
        specialExtra03: Color = this.specialExtra03,
        specialExtra04: Color = this.specialExtra04,
        specialExtra05: Color = this.specialExtra05,
        specialExtra06: Color = this.specialExtra06,
        specialExtra07: Color = this.specialExtra07,
        specialExtra08: Color = this.specialExtra08,
        specialExtra09: Color = this.specialExtra09,
        specialExtra10: Color = this.specialExtra10,
        specialExtra11: Color = this.specialExtra11,
        specialExtra12: Color = this.specialExtra12,
        specialExtra13: Color = this.specialExtra13,
        specialExtra14: Color = this.specialExtra14,
        specialExtra15: Color = this.specialExtra15,
        specialExtra16: Color = this.specialExtra16,
        specialExtra17: Color = this.specialExtra17,
        specialExtra18: Color = this.specialExtra18,
        specialExtra19: Color = this.specialExtra19,
        specialExtra20: Color = this.specialExtra20,
        specialExtra21: Color = this.specialExtra21,
        specialExtra22: Color = this.specialExtra22,
        specialExtra23: Color = this.specialExtra23,
        specialExtra24: Color = this.specialExtra24,
        specialExtra25: Color = this.specialExtra25,
        specialExtra26: Color = this.specialExtra26,
        specialExtra27: Color = this.specialExtra27,
    ): AdmiralColors = AdmiralColors(
        backgroundBasic = backgroundBasic,
        backgroundAdditionalOne = backgroundAdditionalOne,
        backgroundAdditionalOnePressed = backgroundAdditionalOnePressed,
        backgroundAdditionalTwo = backgroundAdditionalTwo,
        backgroundAdditionalTwoPressed = backgroundAdditionalTwoPressed,
        backgroundModalView = backgroundModalView,
        backgroundModalControl = backgroundModalControl,
        backgroundAccent = backgroundAccent,
        backgroundAccentTwo = backgroundAccentTwo,
        backgroundAccentPressed = backgroundAccentPressed,
        backgroundSelected = backgroundSelected,
        backgroundSelectedPressed = backgroundSelectedPressed,
        backgroundAccentDark = backgroundAccentDark,
        backgroundError = backgroundError,
        backgroundErrorPressed = backgroundErrorPressed,
        backgroundSuccess = backgroundSuccess,
        backgroundSuccessPressed = backgroundSuccessPressed,
        backgroundAttention = backgroundAttention,
        backgroundAttentionPressed = backgroundAttentionPressed,
        backgroundSecondary = backgroundSecondary,
        backgroundExtraSurface = backgroundExtraSurface,
        backgroundShadow = backgroundShadow,

        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textAdditional = textAdditional,
        textMask = textMask,
        textStaticWhite = textStaticWhite,
        textContrast = textContrast,
        textAccent = textAccent,
        textAccentPressed = textAccentPressed,
        textAccentAdditional = textAccentAdditional,
        textError = textError,
        textErrorPressed = textErrorPressed,
        textSuccess = textSuccess,
        textSuccessPressed = textSuccessPressed,
        textAttention = textAttention,
        textAttentionPressed = textAttentionPressed,

        elementPrimary = elementPrimary,
        elementSecondary = elementSecondary,
        elementBarDivider = elementBarDivider,
        elementAdditional = elementAdditional,
        elementStaticWhite = elementStaticWhite,
        elementContrast = elementContrast,
        elementExtra = elementExtra,
        elementAccent = elementAccent,
        elementAccentPressed = elementAccentPressed,
        elementAccentAdditional = elementAccentAdditional,
        elementError = elementError,
        elementErrorPressed = elementErrorPressed,
        elementSuccess = elementSuccess,
        elementSuccessPressed = elementSuccessPressed,
        elementAttention = elementAttention,
        elementAttentionPressed = elementAttentionPressed,

        specialExtra01 = specialExtra01,
        specialExtra02 = specialExtra02,
        specialExtra03 = specialExtra03,
        specialExtra04 = specialExtra04,
        specialExtra05 = specialExtra05,
        specialExtra06 = specialExtra06,
        specialExtra07 = specialExtra07,
        specialExtra08 = specialExtra08,
        specialExtra09 = specialExtra09,
        specialExtra10 = specialExtra10,
        specialExtra11 = specialExtra11,
        specialExtra12 = specialExtra12,
        specialExtra13 = specialExtra13,
        specialExtra14 = specialExtra14,
        specialExtra15 = specialExtra15,
        specialExtra16 = specialExtra16,
        specialExtra17 = specialExtra17,
        specialExtra18 = specialExtra18,
        specialExtra19 = specialExtra19,
        specialExtra20 = specialExtra20,
        specialExtra21 = specialExtra21,
        specialExtra22 = specialExtra22,
        specialExtra23 = specialExtra23,
        specialExtra24 = specialExtra24,
        specialExtra25 = specialExtra25,
        specialExtra26 = specialExtra26,
        specialExtra27 = specialExtra27,
    )

    @Suppress("LongParameterList", "LongMethod")
    fun update(other: AdmiralColors) {
        backgroundBasic = other.backgroundBasic
        backgroundAdditionalOne = other.backgroundAdditionalOne
        backgroundAdditionalOnePressed = other.backgroundAdditionalOnePressed
        backgroundAdditionalTwo = other.backgroundAdditionalTwo
        backgroundAdditionalTwoPressed = other.backgroundAdditionalTwoPressed
        backgroundModalView = other.backgroundModalView
        backgroundModalControl = other.backgroundModalControl
        backgroundAccent = other.backgroundAccent
        backgroundAccentTwo = other.backgroundAccentTwo
        backgroundAccentPressed = other.backgroundAccentPressed
        backgroundSelected = other.backgroundSelected
        backgroundSelectedPressed = other.backgroundSelectedPressed
        backgroundAccentDark = other.backgroundAccentDark
        backgroundError = other.backgroundError
        backgroundErrorPressed = other.backgroundErrorPressed
        backgroundSuccess = other.backgroundSuccess
        backgroundSuccessPressed = other.backgroundSuccessPressed
        backgroundAttention = other.backgroundAttention
        backgroundAttentionPressed = other.backgroundAttentionPressed
        backgroundSecondary = other.backgroundSecondary
        backgroundExtraSurface = other.backgroundExtraSurface
        backgroundShadow = other.backgroundShadow

        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textAdditional = other.textAdditional
        textMask = other.textMask
        textStaticWhite = other.textStaticWhite
        textContrast = other.textContrast
        textAccent = other.textAccent
        textAccentPressed = other.textAccentPressed
        textAccentAdditional = other.textAccentAdditional
        textError = other.textError
        textErrorPressed = other.textErrorPressed
        textSuccess = other.textSuccess
        textSuccessPressed = other.textSuccessPressed
        textAttention = other.textAttention
        textAttentionPressed = other.textAttentionPressed

        elementPrimary = other.elementPrimary
        elementSecondary = other.elementSecondary
        elementBarDivider = other.elementBarDivider
        elementAdditional = other.elementAdditional
        elementStaticWhite = other.elementStaticWhite
        elementContrast = other.elementContrast
        elementExtra = other.elementExtra
        elementAccent = other.elementAccent
        elementAccentPressed = other.elementAccentPressed
        elementAccentAdditional = other.elementAccentAdditional
        elementError = other.elementError
        elementErrorPressed = other.elementErrorPressed
        elementSuccess = other.elementSuccess
        elementSuccessPressed = other.elementSuccessPressed
        elementAttention = other.elementAttention
        elementAttentionPressed = other.elementAttentionPressed

        specialExtra01 = other.specialExtra01
        specialExtra02 = other.specialExtra02
        specialExtra03 = other.specialExtra03
        specialExtra04 = other.specialExtra04
        specialExtra05 = other.specialExtra05
        specialExtra06 = other.specialExtra06
        specialExtra07 = other.specialExtra07
        specialExtra08 = other.specialExtra08
        specialExtra09 = other.specialExtra09
        specialExtra10 = other.specialExtra10
        specialExtra11 = other.specialExtra11
        specialExtra12 = other.specialExtra12
        specialExtra13 = other.specialExtra13
        specialExtra14 = other.specialExtra14
        specialExtra15 = other.specialExtra15
        specialExtra16 = other.specialExtra16
        specialExtra17 = other.specialExtra17
        specialExtra18 = other.specialExtra18
        specialExtra19 = other.specialExtra19
        specialExtra20 = other.specialExtra20
        specialExtra21 = other.specialExtra21
        specialExtra22 = other.specialExtra22
        specialExtra23 = other.specialExtra23
        specialExtra24 = other.specialExtra24
        specialExtra25 = other.specialExtra25
        specialExtra26 = other.specialExtra26
        specialExtra27 = other.specialExtra27
    }
}