package com.github.willjgriff.ethereumwallet.ui.createaccount.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;

import dagger.Component;

/**
 * Created by Will on 03/02/2017.
 */

@Component(dependencies = AppComponent.class)
@FunctionScope
public interface CreateAccountComponent {

	// TODO: Using assisted injection prevents us from caching the Presenter as some dependencies
	// can only be injected at runtime. So the Presenters factory has to create a new Presenter
	// each time it is injected. Alternatively we can use setters to accept runtime dependencies
	// which will allow us to cache the Presenter.

	void inject(CreateAccountController createAccountController);
}
