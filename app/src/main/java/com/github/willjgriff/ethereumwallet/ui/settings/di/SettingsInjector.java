package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentInvalidator;
import com.github.willjgriff.ethereumwallet.ui.createaccount.di.DaggerCreateAccountComponent;
import com.github.willjgriff.ethereumwallet.ui.send.di.DaggerSendComponent;

/**
 * Created by Will on 14/02/2017.
 */

public enum SettingsInjector implements ComponentInvalidator {

	INSTANCE;

	private SettingsComponent mSettingsComponent;

	public SettingsComponent getComponent() {
//		if (mSettingsComponent == null) {
//			mSettingsComponent = DaggerSettingsComponent
		return DaggerSettingsComponent
				.builder()
				.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
				.build();
//		}
//		return mSettingsComponent;
	}

	@Override
	public void invalidateComponent() {
		mSettingsComponent = null;
	}
}
