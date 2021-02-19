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

// https://developer.squareup.com/blog/showing-the-android-keyboard-reliably/
fun View.showKeyboard() {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

//    requestFocus()
//    if (hasWindowFocus()) {
//        // No need to wait for the window to get focus.
//        showTheKeyboardNow()
//    } else {
//        // We need to wait until the window gets focus.
//        viewTreeObserver.addOnWindowFocusChangeListener(
//            object : ViewTreeObserver.OnWindowFocusChangeListener {
//                override fun onWindowFocusChanged(hasFocus: Boolean) {
//                    // This notification will arrive just before the InputMethodManager gets set up.
//                    if (hasFocus) {
//                        this@showKeyboard.showTheKeyboardNow()
//                        // It’s very important to remove this listener once we are done.
//                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
//                    }
//                }
//            })
//    }
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
/**
 * Updates the padding of the view. All values should be in pixels. Any parameters not given will not be changed from their current value.
 */
fun View.updatePadding(left: Int = paddingLeft, top: Int = paddingTop, right: Int = paddingRight, bottom: Int = paddingBottom) {
    setPadding(left, top, right, bottom)
}

/**
 * Sets either exact height of view (in pixels), or can be [ViewGroup.LayoutParams.WRAP_CONTENT]/[ViewGroup.LayoutParams.MATCH_PARENT]
 */
fun View.setHeight(value: Int) {
    layoutParams?.let {
        layoutParams = it.apply { height = value }
    }
}

/**
 * Sets either exact width of view (in pixels), or can be [ViewGroup.LayoutParams.WRAP_CONTENT]/[ViewGroup.LayoutParams.MATCH_PARENT]
 */
fun View.setWidth(value: Int) {
    layoutParams?.let {
        layoutParams = it.apply { width = value }
    }
}

/**
 * Updates margins of view. Margins should be provided in pixels. Any margins not provided will not be changed.
 */
fun View.updateMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return // Early return for layout params that don't support margins

    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.bottomMargin)

    layoutParams = lp
}
//endregion
