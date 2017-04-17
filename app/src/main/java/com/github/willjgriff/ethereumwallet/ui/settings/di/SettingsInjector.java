package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.wiljgriff.ethereumwallet.di.AppInjector;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentInvalidator;
import com.github.willjgriff.ethereumwallet.ui.settings.ChangeAddressController;
import com.github.willjgriff.ethereumwallet.ui.settings.DeleteAddressAlertDialog;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsController;

/**
 * Created by Will on 14/02/2017.
 *
 * TODO: Is there a simpler / clearer way to inject Presenters,
 * potentially retained, without risking memory leaks?
 */

public enum SettingsInjector implements ComponentInvalidator {

	INSTANCE;

	private SettingsComponent mSettingsComponent;

	public void injectRetainedSettingsPresenter(SettingsController settingsController) {
		getComponent().inject(settingsController);
	}

	public void injectNewSettingsDeletePresenter(DeleteAddressAlertDialog alertDialog) {
		getComponent().inject(alertDialog);
	}

	public void injectNewSettingsChangeAddressPresenter(ChangeAddressController changeAddressController) {
		getComponent().inject(changeAddressController);
	}

	private SettingsComponent getComponent() {
		if (mSettingsComponent == null) {
			mSettingsComponent = DaggerSettingsComponent
				.builder()
				.appComponent(AppInjector.appComponent)
				.build();
		}
		return mSettingsComponent;
	}

	@Override
	public void invalidateComponent() {
		mSettingsComponent = null;
	}
}
