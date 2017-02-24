package com.github.willjgriff.ethereumwallet.ui.widget.validated;

public class NotEmptyValidator implements Validator {

	@Override
	public boolean isValid(String text) {
		return text != null && !text.isEmpty();
	}
}