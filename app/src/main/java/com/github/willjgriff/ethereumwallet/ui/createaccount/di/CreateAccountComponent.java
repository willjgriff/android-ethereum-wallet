package com.github.willjgriff.ethereumwallet.ui.createaccount.di;

import com.github.wiljgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;

import dagger.Component;

/**
 * Created by Will on 03/02/2017.
 */

@Component(dependencies = AppComponent.class)
@FunctionScope
public interface CreateAccountComponent {

	void inject(CreateAccountController createAccountController);
}
