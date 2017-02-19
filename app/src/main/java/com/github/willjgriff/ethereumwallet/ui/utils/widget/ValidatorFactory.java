package com.github.willjgriff.ethereumwallet.ui.utils.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Returns a Collection of {@link Validator}
 * <p/>
 * If you want to understand how this works, read:
 * https://medium.com/@JakobUlbrich/flag-attributes-in-android-how-to-use-them-ac4ec8aee7d1#.ivwy7y15b
 */
public class ValidatorFactory {

	// Other validators constants should equal 2, 4, 8 etc.
	private static final int NOT_EMPTY = 1;

	public List<Validator> getValidators(int flagSet) {
		List<Validator> validators = new ArrayList<>(8);

		if (containsFlag(flagSet, NOT_EMPTY)) {
			validators.add(new NotEmptyValidator());
		}

		return validators;
	}

	private boolean containsFlag(int flagSet, int flag) {
		return (flagSet | flag) == flagSet;
	}

}
