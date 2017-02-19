package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsDeleteAlertDialog;

import dagger.Component;

/**
 * Created by Will on 19/02/2017.
 */

@ControllerScope
@Component(dependencies = AppComponent.class)
public interface SettingsDeleteComponent {

	void inject(SettingsDeleteAlertDialog alertDialog);
}
