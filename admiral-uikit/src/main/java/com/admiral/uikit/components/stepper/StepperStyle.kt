package com.admiral.uikit.components.stepper

/**
 * Define color for [AdmiralTag]
 */
sealed class StepperStyle {
    object Done : StepperStyle()
    object Current : StepperStyle()
    object Next : StepperStyle()
    object Error : StepperStyle()
    object Disabled : StepperStyle()

    companion object {
        private const val DONE = 0
        private const val CURRENT = 1
        private const val NEXT = 2
        private const val ERROR = 3
        private const val DISABLED = 4

        fun fromIndex(index: Int): StepperStyle {

            return when (index) {
                DONE -> Done
                CURRENT -> Current
                NEXT -> Next
                ERROR -> Error
                DISABLED -> Disabled
                else -> Done
            }
        }
    }
}