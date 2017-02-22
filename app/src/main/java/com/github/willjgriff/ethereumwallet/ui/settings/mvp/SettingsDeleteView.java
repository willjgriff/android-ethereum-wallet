package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

/**
 * Created by Will on 19/02/2017.
 */

public interface SettingsDeleteView {

	void closeDialog();

	void incorrectPasswordEntered();

	void addressDeleted();
}
