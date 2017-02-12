package com.github.willjgriff.ethereumwallet.ui.navigation.di;

import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationController;

import dagger.Component;

/**
 * Created by Will on 12/02/2017.
 */

@Component(modules = ComponentsInvalidatorModule.class)
@ControllerScope
public interface NavigationComponent {

	void inject(NavigationController navigationController);

}
