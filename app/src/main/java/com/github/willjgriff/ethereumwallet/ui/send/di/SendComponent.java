package com.github.willjgriff.ethereumwallet.ui.send.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.send.SendController;

import dagger.Component;

/**
 * Created by Will on 04/02/2017.
 */

@Component(dependencies = AppComponent.class)
@ControllerScope
public interface SendComponent {

	void inject(SendController sendController);
}
