package com.github.willjgriff.ethereumwallet.ui.error

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.utils.isConnected
import java.io.IOException

/**
 * Created by williamgriffiths on 04/05/2017.
 *
 * TODO: This should be injected into the base mvp classes.
 */
object ErrorDisplayer {

    fun displayError(view: View, throwable: Throwable) {
        val errorText = getErrorMessage(view.context, throwable)
        Snackbar.make(view, errorText, Snackbar.LENGTH_LONG).show()
    }

    private fun getErrorMessage(context: Context, throwable: Throwable): String {
        if (internetDisconnected(context)) {
            return context.getString(R.string.error_internet_disconnected)
        } else if (isNetworkError(throwable)) {
            return context.getString(R.string.error_network)
        } else {
            return context.getString(R.string.error_unknown)
        }
    }

    private fun internetDisconnected(context: Context): Boolean {
        return !context.isConnected()
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        return throwable is IOException
    }
}