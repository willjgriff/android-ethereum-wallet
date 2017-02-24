package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentInvalidator;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsController;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsDeleteAlertDialog;

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

	public void injectNewSettingsDeletePresenter(SettingsDeleteAlertDialog alertDialog) {
		getComponent().inject(alertDialog);
	}

	private SettingsComponent getComponent() {
		if (mSettingsComponent == null) {
			mSettingsComponent = DaggerSettingsComponent
				.builder()
				.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
				.build();
		}
		return mSettingsComponent;
	}

	@Override
	public void invalidateComponent() {
		mSettingsComponent = null;
	}
}
