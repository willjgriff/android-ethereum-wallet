package com.github.willjgriff.ethereumwallet.ui.navigation.di;

import com.github.willjgriff.ethereumwallet.di.ComponentsInvalidator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 12/02/2017.
 */

@Module
public class ComponentsInvalidatorModule {

	@Provides
	public ComponentsInvalidator mComponentsInvalidator() {
		return new ComponentsInvalidator();
	}
}
