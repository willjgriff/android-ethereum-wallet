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
import com.github.willjgriff.ethereumwallet.ui.createaccount.CreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.SettingsCreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.settings.di.SettingsInjector;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsPresenter;
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.SettingsPresenterFactory;
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
	implements SettingsView {

	@BindView(R.id.controller_settings_new_address)
	Button mNewAddress;
	@BindView(R.id.controller_settings_change_address)
	Button mChangeAddress;
	@BindView(R.id.controller_settings_delete_address)
	Button mDeleteAddress;

	@Inject
	SettingsPresenterFactory mSettingsPresenterFactory;

	@Override
	protected SettingsView getMvpView() {
		return this;
	}

	@Override
	protected SettingsPresenter createPresenter() {
		Observable<Object> newAccountButton = RxView.clicks(mNewAddress);
		return mSettingsPresenterFactory.create(newAccountButton);
	}

	public SettingsController() {
		SettingsInjector.INSTANCE.getComponent().inject(this);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_settings, container, false);
		ButterKnife.bind(this, view);

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
}
