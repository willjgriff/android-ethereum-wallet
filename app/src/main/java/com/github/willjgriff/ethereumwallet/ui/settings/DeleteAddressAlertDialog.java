package com.github.willjgriff.ethereumwallet.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.DeleteAddressPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.DeleteAddressView;
import com.github.willjgriff.ethereumwallet.ui.widget.validated.ValidatedTextInputLayout;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 */

public class DeleteAddressAlertDialog extends AlertDialog implements DeleteAddressView {

	@Inject
	DeleteAddressPresenter mPresenter;

	private ValidatedTextInputLayout mValidatedTextInputLayout;
	private SettingsDeleteAlertDialogListener mDialogListener;

	protected DeleteAddressAlertDialog(@NonNull Context context, SettingsDeleteAlertDialogListener listener) {
		// TODO: Remove if left unused.
//		super(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
		super(context);
		SettingsInjector.INSTANCE.injectNewSettingsDeletePresenter(this);
		setupAppearance();
		mDialogListener = listener;
	}

	private void setupAppearance() {
		setButton(BUTTON_POSITIVE, getContext().getString(R.string.controller_settings_delete_address),
			(dialogInterface, i) -> {
			});
		setButton(BUTTON_NEGATIVE, getContext().getString(R.string.controller_settings_delete_address_cancel),
			(dialogInterface, i) -> {
			});
		setTitle(R.string.controller_settings_delete_address_dialog_title);
		setMessage(getContext().getString(R.string.controller_settings_delete_address_dialog_message));

		mValidatedTextInputLayout = (ValidatedTextInputLayout) LayoutInflater.from(getContext())
			.inflate(R.layout.view_controller_settings_delete_validated_input, new FrameLayout(getContext()), false);
		setView(mValidatedTextInputLayout);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		mPresenter.bindView(this);
		mPresenter.viewReady();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mPresenter.unbindView();
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

	@Override
	public void incorrectPasswordEntered() {
		mDialogListener.showIncorrectPasswordDialog();
	}

	@Override
	public void addressDeleted() {
		mDialogListener.addressSuccessfullyDeleted();
	}

	public interface SettingsDeleteAlertDialogListener {

		void showIncorrectPasswordDialog();

		void addressSuccessfullyDeleted();
	}
}
