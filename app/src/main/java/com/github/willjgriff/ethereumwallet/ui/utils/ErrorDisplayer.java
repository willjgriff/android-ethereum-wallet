package com.github.willjgriff.ethereumwallet.ui.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.data.utils.ConnectivityUtils;

import java.io.IOException;

import io.realm.exceptions.RealmError;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by williamgriffiths on 22/09/2016.
 * <p>
 * TODO: This should be injected somehow.
 */
public class ErrorDisplayer {

	public static void displayError(View view, Throwable throwable) {
		String errorText = getErrorMessage(view.getContext(), throwable);
		Snackbar.make(view, errorText, Snackbar.LENGTH_LONG).show();
	}

	private static String getErrorMessage(Context context, Throwable throwable) {
		if (isStorageError(throwable)) {
			return context.getString(R.string.error_storage);
		} else if (internetDisconnected(context)) {
			return context.getString(R.string.error_internet_disconnected);
		} else if (isNetworkError(throwable)) {
			return context.getString(R.string.error_network);
		} else {
			return context.getString(R.string.error_unknown);
		}
	}

	private static boolean isStorageError(Throwable throwable) {
		return throwable instanceof RealmError ||
			throwable instanceof RealmException ||
			throwable instanceof RealmFileException ||
			throwable instanceof RealmMigrationNeededException ||
			throwable instanceof RealmPrimaryKeyConstraintException;
	}

	private static boolean internetDisconnected(Context context) {
		return !ConnectivityUtils.isConnected(context);
	}

	private static boolean isNetworkError(Throwable throwable) {
		return throwable instanceof IOException;
	}
}
