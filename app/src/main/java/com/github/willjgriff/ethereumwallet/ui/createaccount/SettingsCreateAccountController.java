package com.github.willjgriff.ethereumwallet.ui.createaccount;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 15/02/2017.
 */

public class SettingsCreateAccountController extends Controller
	implements CreateAccountCompletedListener {

	@BindView(R.id.controller_create_account_settings_container)
	ViewGroup mCreateAccountContainer;

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_create_account_settings, container, false);
		ButterKnife.bind(this, view);

		CreateAccountController createAccountController = new CreateAccountController();
		createAccountController.setTargetController(this);
		getChildRouter(mCreateAccountContainer).pushController(RouterTransaction.with(createAccountController));

		if (getTargetController() instanceof SettingsToolbarListener) {
			String settingsTitle = getApplicationContext().getString(R.string.controller_create_account_title);
			((SettingsToolbarListener) getTargetController()).setToolbarTitle(settingsTitle);
		}

		return view;
	}

	@Override
	public void onAccountCreated() {
		getRouter().popCurrentController();
	}

	public interface SettingsToolbarListener {
		void setToolbarTitle(CharSequence title);
	}

}
