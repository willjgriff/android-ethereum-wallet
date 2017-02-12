package com.github.willjgriff.ethereumwallet.ui.createaccount.di;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.di.ComponentInvalidator;

/**
 * Created by Will on 12/02/2017.
 */

public enum CreateAccountInjector implements ComponentInvalidator {

	INSTANCE;

	private CreateAccountComponent mCreateAccountComponent;

	public CreateAccountComponent getComponent() {
		if (mCreateAccountComponent == null) {
			mCreateAccountComponent = DaggerCreateAccountComponent
				.builder()
				.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
				.build();
		}
		return mCreateAccountComponent;
	}

	@Override
	public void invalidateComponent() {
		mCreateAccountComponent = null;
	}
}
