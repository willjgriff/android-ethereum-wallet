package com.github.willjgriff.ethereumwallet.ui.settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.SettingsCreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.SettingsCreateAccountController.SettingsToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.settings.DeleteAddressAlertDialog.SettingsDeleteAlertDialogListener;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsView;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

public class SettingsController extends BaseMvpController<SettingsView, SettingsPresenter>
	implements SettingsView, SettingsDeleteAlertDialogListener, SettingsToolbarListener {

	@BindView(R.id.controller_settings_current_address)
	TextView mActiveAddress;
	@BindView(R.id.controller_settings_new_address)
	Button mNewAddress;
	@BindView(R.id.controller_settings_change_address)
	Button mChangeAddress;
	@BindView(R.id.controller_settings_delete_address)
	Button mDeleteAddress;

	@Inject
	SettingsPresenter mSettingsPresenter;

	public SettingsController() {
		SettingsInjector.INSTANCE.injectRetainedSettingsPresenter(this);
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
		setObservablesOnPresenter();
		setToolbarTitle(getApplicationContext().getString(R.string.controller_settings_title));
		return view;
	}

	private void setObservablesOnPresenter() {
		Observable<Object> newAddressButton = RxView.clicks(mNewAddress);
		Observable<Object> changeAddressButton = RxView.clicks(mChangeAddress);
		Observable<Object> deleteAddressButton = RxView.clicks(mDeleteAddress);
		mSettingsPresenter.setObservables(newAddressButton, changeAddressButton, deleteAddressButton);
	}

	@Override
	public void setToolbarTitle(CharSequence title) {
		if (getTargetController() instanceof NavigationToolbarListener) {
			((NavigationToolbarListener) getTargetController()).setToolbarTitle(title);
		}
	}

	@Override
	public void openCreateAccountScreen() {
		SettingsCreateAccountController settingsCreateAccountController = new SettingsCreateAccountController();
		settingsCreateAccountController.setTargetController(this);
		getRouter().pushController(RouterTransaction.with(settingsCreateAccountController)
			.pushChangeHandler(new FadeChangeHandler())
			.popChangeHandler(new FadeChangeHandler()));
	}

	@Override
	public void showDeleteAddressDialog() {
		new DeleteAddressAlertDialog(getActivity(), this).show();
	}

	@Override
	public void setActiveAddress(String address) {
		mActiveAddress.setText(address);
	}

	@Override
	public void setAddressDeleted() {
		mActiveAddress.setText(R.string.controller_settings_address_deleted);
	}

	@Override
	public void openChangeAddressScreen() {
		getRouter().pushController(RouterTransaction.with(new ChangeAddressController())
			.pushChangeHandler(new FadeChangeHandler())
			.popChangeHandler(new FadeChangeHandler()));
	}

	@Override
	public void showIncorrectPasswordDialog() {
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.controller_settings_incorrect_password)
			.setMessage(R.string.controller_settings_incorrect_password_message)
			.setPositiveButton(R.string.common_ok, (dialog, which) -> dialog.dismiss())
			.show();
	}

	@Override
	public void addressSuccessfullyDeleted() {
		mActiveAddress.setText(R.string.controller_settings_address_deleted);
	}
}
