package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

/**
 * Created by Will on 14/02/2017.
 */

public interface SettingsView {

	void openCreateAccountScreen();

	void showDeleteAddressDialog();

	void setActiveAddress(String address);

	void setAddressDeleted();

	void openChangeAddressScreen();
}
