package com.github.willjgriff.ethereumwallet.ui.widget.validated;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by williamgriffiths on 24/02/2017.
 */

public class RxTextInputValidation {

	private ValidTextInputListener mValidTextInputListener;
	private Observable<String> mTextChanged;
	private Observable<Boolean> mTextValid;
	private List<Validator> mValidators;

	public RxTextInputValidation(@NonNull ValidTextInputListener validTextInputListener,
	                             @NonNull List<Validator> validators,
	                             @NonNull Observable<CharSequence> textChangedObservable) {

		mValidTextInputListener = validTextInputListener;
		mValidators = validators;
		mTextChanged = textChangedObservable
			.map(String::valueOf)
			.distinctUntilChanged();

		setupTextChangedObservable();
	}

	public void setupTextChangedObservable() {
		// TODO: Debug these Observables and check they behave as expected (they may be firing to often)
		mTextValid = mTextChanged
			.flatMap(this::isTextValid)
			.map(validText -> validText)
			.distinctUntilChanged();

		// Hide the error when valid text is emitted
		mTextValid
			.filter(isValid -> isValid)
			.subscribe(isValid -> mValidTextInputListener.hideError());

		// Skip until the field has a valid result
		Observable<Boolean> hotValidText = mTextValid
			.publish()
			.autoConnect();
		Observable<Boolean> skipUntilValid = hotValidText
			.skipUntil(hotValidText.filter(isValid -> isValid));

		// Show the error when invalid text is emitted
		skipUntilValid
			.filter(isValid -> !isValid)
			.subscribe(isNotValid -> mValidTextInputListener.showError());
	}

	private Observable<Boolean> isTextValid(String inputText) {
		return Observable.fromIterable(mValidators)
			.all(validator -> validator.isValid(inputText))
			.toObservable();
	}

	public void setValidateTrigger(Observable<Object> validateTrigger) {
		validateTrigger
			.flatMap(anyEmission -> mTextValid)
			.filter(isValid -> !isValid)
			.subscribe(isNotValid -> mValidTextInputListener.showError());
	}

	public Observable<String> getTextChangedObservable() {
		return mTextChanged;
	}

	public Observable<Boolean> getTextValidObservable() {
		return mTextValid;
	}

	public interface ValidTextInputListener {
		void showError();
		void hideError();
	}
}
