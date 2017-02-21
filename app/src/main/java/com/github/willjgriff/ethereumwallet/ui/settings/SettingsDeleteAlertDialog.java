package com.github.willjgriff.ethereumwallet.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.di.ApplicationInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.di.DaggerSettingsDeleteComponent;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsDeletePresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsDeleteView;
import com.github.willjgriff.ethereumwallet.ui.utils.widget.ValidatedTextInputLayout;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 */

public class SettingsDeleteAlertDialog extends AlertDialog implements SettingsDeleteView {

	private ValidatedTextInputLayout mValidatedTextInputLayout;

	@Inject
	SettingsDeletePresenter mPresenter;

	protected SettingsDeleteAlertDialog(@NonNull Context context) {
		super(context);
		setupDialog();
	}

	protected SettingsDeleteAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
		super(context, themeResId);
		setupDialog();
	}

	protected SettingsDeleteAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		setupDialog();
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		mPresenter.bindView(this);
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mPresenter.unbindView();
	}

	private void setupDialog() {
		DaggerSettingsDeleteComponent.builder()
			.appComponent(ApplicationInjector.INSTANCE.getAppComponent())
			.build()
			.inject(this);
		setupAppearance();
	}

	private void setupAppearance() {
		setButton(BUTTON_POSITIVE, getContext().getString(R.string.controller_settings_delete_address),
			(dialogInterface, i) -> {});
		setButton(BUTTON_NEGATIVE, getContext().getString(R.string.controller_settings_delete_address_cancel),
			(dialogInterface, i) -> {});
		setTitle(R.string.controller_settings_delete_address_dialog_title);
		setMessage(getContext().getString(R.string.controller_settings_delete_address_dialog_message));

		mValidatedTextInputLayout = (ValidatedTextInputLayout) LayoutInflater.from(getContext())
			.inflate(R.layout.view_controller_settings_delete_validated_input, new FrameLayout(getContext()), false);
		setView(mValidatedTextInputLayout);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Observable<Object> deleteButton = RxView.clicks(getButton(BUTTON_POSITIVE)).share();
		Observable<Object> cancelButton = RxView.clicks(getButton(BUTTON_NEGATIVE)).share();
		mValidatedTextInputLayout.setCheckValidationTrigger(deleteButton);
		Observable<Boolean> isTextValid = mValidatedTextInputLayout.getTextValidObservable();
		Observable<String> textChanged = mValidatedTextInputLayout.getTextChangedObservable();

		mPresenter.setObservables(deleteButton, cancelButton, isTextValid, textChanged);
	}

	@Override
	public void closeDialog() {
		dismiss();
	}
}
