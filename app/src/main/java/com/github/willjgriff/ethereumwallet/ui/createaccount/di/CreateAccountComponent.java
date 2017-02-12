package com.github.willjgriff.ethereumwallet.ui.createaccount.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;

import dagger.Component;

/**
 * Created by Will on 03/02/2017.
 */

@Component(dependencies = AppComponent.class)
// TODO: This scope isn't used, consider removing.
@ControllerScope
public interface CreateAccountComponent {

	void inject(CreateAccountController createAccountController);
}
