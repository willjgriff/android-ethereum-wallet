package com.github.willjgriff.ethereumwallet.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by williamgriffiths on 06/05/2017.
 */
fun Context.isConnected(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isConnected
}