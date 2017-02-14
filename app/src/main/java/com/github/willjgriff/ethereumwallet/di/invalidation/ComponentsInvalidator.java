package com.github.willjgriff.ethereumwallet.di.invalidation;

import com.github.willjgriff.ethereumwallet.ui.createaccount.di.CreateAccountInjector;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Will on 12/02/2017.
 */

public class ComponentsInvalidator {

	private final List<ComponentInvalidator> mComponentInvalidators = new ArrayList<>();

	public ComponentsInvalidator() {
		mComponentInvalidators.add(CreateAccountInjector.INSTANCE);
	}

	public void invalidateComponents() {
		Observable.fromIterable(mComponentInvalidators)
			.subscribe(ComponentInvalidator::invalidateComponent);
	}
}
