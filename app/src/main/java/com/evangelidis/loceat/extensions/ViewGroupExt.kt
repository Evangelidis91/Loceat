package com.evangelidis.loceat.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)
