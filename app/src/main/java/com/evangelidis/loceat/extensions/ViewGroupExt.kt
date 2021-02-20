package com.evangelidis.loceat.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DimenRes

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

fun Context.dimen(@DimenRes dimenRes: Int): Int = resources.getDimensionPixelSize(dimenRes)

