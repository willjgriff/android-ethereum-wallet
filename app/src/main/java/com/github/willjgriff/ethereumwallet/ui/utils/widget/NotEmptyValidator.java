package com.github.willjgriff.ethereumwallet.ui.utils.widget;

public class NotEmptyValidator implements Validator {

	@Override
	public boolean isValid(String text) {
		return text != null && !text.isEmpty();
	}
}