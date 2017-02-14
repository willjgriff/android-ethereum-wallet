package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsController;

import dagger.Component;

/**
 * Created by Will on 14/02/2017.
 */

@ControllerScope
@Component(dependencies = AppComponent.class)
public interface SettingsComponent {

	void inject(SettingsController settingsController);
}
