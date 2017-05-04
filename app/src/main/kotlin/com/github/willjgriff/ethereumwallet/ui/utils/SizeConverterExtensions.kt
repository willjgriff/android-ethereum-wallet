package com.github.willjgriff.ethereumwallet.ui.utils

import android.content.Context

/**
 * Created by williamgriffiths on 04/05/2017.
 */
fun Float.convertDpToPixel(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this * (metrics.densityDpi / 160f)
}

fun Float.convertPixelsToDp(context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this / (metrics.densityDpi / 160f)
}

fun Int.convertDpToPixel(context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return this * (metrics.densityDpi / 160)
}