package com.github.willjgriff.ethereumwallet.ui.utils.error

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.data.utils.ConnectivityUtils
import io.realm.exceptions.*
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
        if (isStorageError(throwable)) {
            return context.getString(R.string.error_storage)
        } else if (internetDisconnected(context)) {
            return context.getString(R.string.error_internet_disconnected)
        } else if (isNetworkError(throwable)) {
            return context.getString(R.string.error_network)
        } else {
            return context.getString(R.string.error_unknown)
        }
    }

    private fun isStorageError(throwable: Throwable): Boolean {
        return throwable is RealmError ||
                throwable is RealmException ||
                throwable is RealmFileException ||
                throwable is RealmMigrationNeededException ||
                throwable is RealmPrimaryKeyConstraintException
    }

    private fun internetDisconnected(context: Context): Boolean {
        return !ConnectivityUtils.isConnected(context)
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        return throwable is IOException
    }
}