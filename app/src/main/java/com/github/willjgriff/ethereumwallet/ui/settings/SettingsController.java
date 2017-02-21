package com.github.willjgriff.ethereumwallet.ui.settings;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.SettingsCreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsView;
import com.github.willjgriff.ethereumwallet.ui.widget.ValidatedTextInputLayout;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

public class SettingsController extends BaseMvpController<SettingsView, SettingsPresenter>
	implements SettingsView {

	@BindView(R.id.controller_settings_new_address)
	Button mNewAddress;
	@BindView(R.id.controller_settings_change_address)
	Button mChangeAddress;
	@BindView(R.id.controller_settings_delete_address)
	Button mDeleteAddress;

	@Inject
	SettingsPresenter mSettingsPresenter;

	public SettingsController() {
		SettingsInjector.INSTANCE.getComponent().inject(this);
	}

	@Override
	protected SettingsView getMvpView() {
		return this;
	}

	@Override
	protected SettingsPresenter createPresenter() {
		return mSettingsPresenter;
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_settings, container, false);
		ButterKnife.bind(this, view);

		Observable<Object> newAddressButton = RxView.clicks(mNewAddress);
		Observable<Object> deleteAddressButton = RxView.clicks(mDeleteAddress);
		mSettingsPresenter.setObservables(newAddressButton, deleteAddressButton);

		setToolbarTitle();

		return view;
	}

	private void setToolbarTitle() {
		if (getTargetController() instanceof NavigationToolbarListener) {
			String settingsTitle = getApplicationContext().getString(R.string.controller_settings_title);
			((NavigationToolbarListener) getTargetController()).setToolbarTitle(settingsTitle);
		}
	}

	@Override
	public void openCreateAccountScreen() {
		getRouter().pushController(RouterTransaction.with(new SettingsCreateAccountController())
			.pushChangeHandler(new FadeChangeHandler())
			.popChangeHandler(new FadeChangeHandler()));
	}

	@Override
	public void showPasswordConfirmationDialog() {
//		// This should be in an independent component with its own MVP structure.
//		ValidatedTextInputLayout validatedTextInputLayout = (ValidatedTextInputLayout) LayoutInflater.from(getActivity())
//			.inflate(R.layout.view_controller_settings_delete_validated_input, new FrameLayout(getApplicationContext()), false);
//
//		AlertDialog deleteAccountDialog = new AlertDialog
//			.Builder(getActivity())
//			.setTitle(R.string.controller_settings_delete_address_dialog_title)
//			.setMessage(R.string.controller_settings_delete_address_dialog_message)
//			.setPositiveButton(R.string.controller_settings_delete_address, null)
//			.setNegativeButton(R.string.controller_settings_delete_address_cancel, (dialogInterface, i) -> dialogInterface.cancel())
//			.setView(validatedTextInputLayout)
//			.show();
//
//		Button dialogPositiveButton = deleteAccountDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//		Observable<Object> dialogPositiveButtonObservable = RxView.clicks(dialogPositiveButton).share();
//
//		validatedTextInputLayout.setCheckValidationTrigger(dialogPositiveButtonObservable);
//
//		Observable<Boolean> isTextValidObservable = validatedTextInputLayout.getTextValidObservable();
//
//		dialogPositiveButtonObservable
//			.withLatestFrom(isTextValidObservable, (buttonClick, areAllValid) -> areAllValid)
//			.filter(areAllValid -> areAllValid)
//			.subscribe(aBoolean -> getPresenter().deleteActiveAccount(validatedTextInputLayout.getText()));

		new SettingsDeleteAlertDialog(getActivity()).show();
	}
}
