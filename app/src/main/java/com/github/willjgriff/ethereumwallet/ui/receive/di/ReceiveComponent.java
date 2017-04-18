package com.github.willjgriff.ethereumwallet.ui.receive.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.receive.ReceiveController;

import dagger.Component;

/**
 * Created by Will on 01/03/2017.
 */

@Component(dependencies = AppComponent.class)
@ControllerScope
public interface ReceiveComponent {

	void inject(ReceiveController receiveController);
}
