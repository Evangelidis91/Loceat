package com.evangelidis.loceat.extensions

fun Int.isMultipleOf(n: Int) = this % n == 0

fun Int.isEven(): Boolean = isMultipleOf(2)

fun Int.isOdd(): Boolean = !isEven()

fun Int?.orZero(): Int = this ?: 0

fun Float?.orZero(): Float = this ?: 0f

fun Double?.orZero(): Double = this ?: 0.0

fun Long?.orZero(): Long = this ?: 0L
