package com.github.willjgriff.ethereumwallet.ui.createaccount.di;

import com.github.willjgriff.ethereumwallet.di.AppInjector;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;

/**
 * Created by Will on 12/02/2017.
 */

public enum CreateAccountInjector {

	INSTANCE;

	public void injectPresenterFactory(CreateAccountController createAccountController) {
		DaggerCreateAccountComponent
			.builder()
			.appComponent(AppInjector.appComponent)
			.build()
			.inject(createAccountController);
	}

}
