package com.github.willjgriff.ethereumwallet.ui.send.di;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentInvalidator;

/**
 * Created by Will on 21/02/2017.
 */

public enum SendInjector implements ComponentInvalidator {

	INSTANCE;

	private SendComponent mSendComponent;

	public SendComponent getComponent() {
		if (mSendComponent == null) {
			mSendComponent = DaggerSendComponent.builder()
				.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
				.build();
		}
		return mSendComponent;
	}

	@Override
	public void invalidateComponent() {
		mSendComponent = null;
	}
}
