package com.github.willjgriff.ethereumwallet.ui.receive.di;

import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.ui.receive.ReceiveController;

/**
 * Created by Will on 01/03/2017.
 */

public enum  ReceiveInjector {

	INSTANCE;

	public void injectNewReceivePresenter(ReceiveController receiveController) {
		DaggerReceiveComponent.builder()
			.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
			.build()
			.inject(receiveController);
	}
}