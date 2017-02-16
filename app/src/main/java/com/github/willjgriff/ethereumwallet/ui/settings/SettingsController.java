package com.github.willjgriff.ethereumwallet.ui.settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.createaccount.SettingsCreateAccountController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
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
		// TODO: Add strings to res.
		EditText editText = new EditText(getApplicationContext());
		int padding = (int) getResources().getDimension(R.dimen.xsmall);
		editText.setPadding(padding, 0, padding, 0);
		new AlertDialog
			.Builder(getActivity())
			.setTitle("Delete active address")
			.setMessage("This action cannot be undone. Any Ether in this wallet will be lost. To delete this address, enter its password:")
			.setPositiveButton("Delete", (dialogInterface, i) -> {
				getPresenter().deleteActiveAccount(editText.getText().toString());
				dialogInterface.dismiss();
			})
			.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
			.setView(editText)
			.show();
	}
}
