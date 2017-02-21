package com.github.willjgriff.ethereumwallet.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.github.willjgriff.ethereumwallet.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Will on 17/02/2017.
 */

public class ValidatedTextInputLayout extends TextInputLayout {

	private Observable<String> mTextChanged;
	private Observable<Boolean> mTextValid;
	private List<Validator> mValidators = new ArrayList<>();
	private CharSequence mErrorMessage;

	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_validated_text_input_layout, this);
	}

	// TODO: Currently erroneous behaviour if created with this constructor.
	// Create a layout file and use LayoutInflater to invoke any of the other constructors.
	public ValidatedTextInputLayout(Context context) {
		super(context);
		setupObservables();
	}

	public ValidatedTextInputLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupAttributes(attrs);
		setupObservables();
	}

	public ValidatedTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setupAttributes(attrs);
		setupObservables();
	}

	private void setupAttributes(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ValidatedTextInputLayout);
		try {
			int inputType = typedArray.getInteger(R.styleable.ValidatedTextInputLayout_android_inputType, InputType.TYPE_CLASS_TEXT);
			getEditText().setInputType(inputType);

			int validatorsFlag = typedArray.getInteger(R.styleable.ValidatedTextInputLayout_validator, 0);
			mValidators = new ValidatorFactory().getValidators(validatorsFlag);

			mErrorMessage = typedArray.getText(R.styleable.ValidatedTextInputLayout_error_text);
		} finally {
			typedArray.recycle();
		}
	}

	private void setupObservables() {
		mTextChanged = RxTextView.textChanges(getEditText())
			.map(charSequence -> String.valueOf(charSequence))
			.distinctUntilChanged();

		mTextValid = mTextChanged
			.map((inputText) -> isTextValid(inputText))
			.distinctUntilChanged();

		// Hide the error when valid text is emitted
		mTextValid
			.filter(isValid -> isValid)
			.subscribe(isValid -> setErrorEnabled(false));

		// Skip until the field has a valid result
		Observable<Boolean> hotValidText = mTextValid
			.publish()
			.autoConnect();
		Observable<Boolean> skipUntilValid = hotValidText
			.skipUntil(hotValidText.filter(isValid -> isValid));

		// Show the error when invalid text is emitted
		skipUntilValid
			.filter(isValid -> !isValid)
			.subscribe(isNotValid -> setError(mErrorMessage));
	}

	@NonNull
	private Boolean isTextValid(String inputText) {
		boolean isValid = true;
		for (Validator validator : mValidators) {
			if (!validator.isValid(inputText)) {
				isValid = false;
			}
		}
		return isValid;

		// Attempt at achieving the above with Rx. I'm sure there's a better way of doing this
		// but I can't think of it yet.
//		Single<Boolean> isValidObservable = Observable.fromIterable(mValidators)
//			.withLatestFrom(mTextChanged, Validator::isValid)
//			.all(isValid -> isValid);
//
//		return mTextChanged.filter(textInput -> isValidObservable.blockingGet());
	}

	public void showAdditionalError(CharSequence errorMessage) {
		setError(errorMessage);
	}

	public void hideError() {
		setErrorEnabled(false);
	}

	public void setCheckValidationTrigger(Observable<Object> validationTrigger) {
		validationTrigger
			.flatMap(anyEmission -> mTextValid)
			.filter(isValid -> !isValid)
			.subscribe(isNotValid -> setError(mErrorMessage));
	}

	public Observable<String> getTextChangedObservable() {
		return mTextChanged;
	}

	public Observable<Boolean> getTextValidObservable() {
		return mTextValid;
	}

	public String getText() {
		return getEditText().getText().toString();
	}
}
