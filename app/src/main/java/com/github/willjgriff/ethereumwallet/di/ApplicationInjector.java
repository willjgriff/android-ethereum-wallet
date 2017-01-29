package com.github.willjgriff.ethereumwallet.di;

import android.content.Context;

/**
 * Created by Will on 29/01/2017.
 */

public enum ApplicationInjector {

	INSTANCE;

	private AppComponent mAppComponent;

	public void initAppComponent(Context context) {
		if (mAppComponent == null) {
			mAppComponent = DaggerAppComponent.builder()
			.appModule(new AppModule(context))
			.build();
		}
	}

	public AppComponent getAppComponent() {
		return mAppComponent;
	}
}
