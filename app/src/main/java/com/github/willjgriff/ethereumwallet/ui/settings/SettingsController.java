package com.github.willjgriff.ethereumwallet.ui.settings;

import android.support.annotation.NonNull;
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
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.settings.SettingsDeleteAlertDialog.SettingsDeleteAlertDialogListener;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsView;
import com.github.willjgriff.ethereumwallet.ui.widget.TestView;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

public class SettingsController extends BaseMvpController<SettingsView, SettingsPresenter>
	implements SettingsView, SettingsDeleteAlertDialogListener {

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
	private Unbinder mUnbinder;

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
		mUnbinder = ButterKnife.bind(this, view);

		Observable<Object> newAddressButton = RxView.clicks(mNewAddress);
		Observable<Object> deleteAddressButton = RxView.clicks(mDeleteAddress);
		mSettingsPresenter.setObservables(newAddressButton, deleteAddressButton);
		setToolbarTitle();

		return view;
	}

	@Override
	protected void onDestroyView(@NonNull View view) {
		super.onDestroyView(view);
		mUnbinder.unbind();
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
	public void showDeleteAddressDialog() {
		new SettingsDeleteAlertDialog(getActivity(), this).show();
	}

	@Override
	public void setActiveAddress(String address) {
		mActiveAddress.setText(address);
	}

	@Override
	public void showIncorrectPasswordDialog() {

	}

	@Override
	public void addressSuccessfullyDeleted() {
		mActiveAddress.setText(R.string.controller_settings_address_deleted);
	}
}
