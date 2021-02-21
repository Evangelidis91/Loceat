package com.evangelidis.loceat.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

//region Keyboard
fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }
}

fun View.showKeyboard() {

    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}
//endregion

//region Visibility
fun View.show() {
    visibility = View.VISIBLE
}

fun View.showIf(default: Int = View.GONE, predicate: (View) -> Boolean) {
    visibility = predicate(this) then View.VISIBLE ?: default
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.hideIf(default: Int = View.VISIBLE, predicate: (View) -> Boolean) {
    visibility = predicate(this) then View.INVISIBLE ?: default
}

fun View.gone() {
    visibility = View.GONE
}

fun View.goneIf(default: Int = View.VISIBLE, predicate: (View) -> Boolean) {
    visibility = predicate(this) then View.GONE ?: default
}

fun View.isVisible() = visibility == View.VISIBLE
//endregion

//region Width/Height/Padding/Margin

fun View.updatePadding(left: Int = paddingLeft, top: Int = paddingTop, right: Int = paddingRight, bottom: Int = paddingBottom) {
    setPadding(left, top, right, bottom)
}

fun View.setHeight(value: Int) {
    layoutParams?.let {
        layoutParams = it.apply { height = value }
    }
}

fun View.setWidth(value: Int) {
    layoutParams?.let {
        layoutParams = it.apply { width = value }
    }
}

fun View.updateMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.bottomMargin)

    layoutParams = lp
}
//endregion
