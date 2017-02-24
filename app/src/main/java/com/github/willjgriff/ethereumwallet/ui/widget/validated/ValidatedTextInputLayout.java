package com.github.willjgriff.ethereumwallet.ui.widget.validated;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.widget.validated.RxTextInputValidation.ValidTextInputListener;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Will on 17/02/2017.
 */

public class ValidatedTextInputLayout extends TextInputLayout implements ValidTextInputListener {

	private List<Validator> mValidators = new ArrayList<>();
	private CharSequence mErrorMessage;
	private RxTextInputValidation mRxTextInputValidation;

	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_validated_text_input_layout, this);
	}

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
		mRxTextInputValidation = new RxTextInputValidation(this, mValidators);
		mRxTextInputValidation.setTextChangedObservable(RxTextView.textChanges(getEditText())
			.map(charSequence -> String.valueOf(charSequence)));
	}

	public void setCheckValidationTrigger(Observable<Object> validationTrigger) {
		mRxTextInputValidation.setCheckValidationTrigger(validationTrigger);
	}

	public Observable<String> getTextChangedObservable() {
		return mRxTextInputValidation.getTextChangedObservable();
	}

	public Observable<Boolean> getTextValidObservable() {
		return mRxTextInputValidation.getTextValidObservable();
	}

	public String getText() {
		return getEditText().getText().toString();
	}

	@Override
	public void showError() {
		setError(mErrorMessage);
	}

	@Override
	public void hideError() {
		setErrorEnabled(false);
	}
}
