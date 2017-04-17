package com.github.willjgriff.ethereumwallet.ui.send.di;

import com.github.wiljgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.ui.send.SendController;

import dagger.Component;

/**
 * Created by Will on 04/02/2017.
 */

@Component(dependencies = AppComponent.class)
@FunctionScope
public interface SendComponent {

	void inject(SendController sendController);
}
