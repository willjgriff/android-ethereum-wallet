package com.github.willjgriff.ethereumwallet.ui.settings.di;

import com.github.wiljgriff.ethereumwallet.di.AppComponent;
import com.github.wiljgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ui.settings.ChangeAddressController;
import com.github.willjgriff.ethereumwallet.ui.settings.DeleteAddressAlertDialog;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsController;

import dagger.Component;

/**
 * Created by Will on 14/02/2017.
 */

@ControllerScope
@Component(dependencies = AppComponent.class)
public interface SettingsComponent {

	void inject(SettingsController settingsController);

	void inject(DeleteAddressAlertDialog alertDialog);

	void inject(ChangeAddressController changeAddressController);
}
